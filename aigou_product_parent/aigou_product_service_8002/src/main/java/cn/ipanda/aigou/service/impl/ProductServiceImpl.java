package cn.ipanda.aigou.service.impl;

import cn.ipanda.aigou.domain.*;
import cn.ipanda.aigou.mapper.ProductExtMapper;
import cn.ipanda.aigou.mapper.ProductMapper;
import cn.ipanda.aigou.mapper.SkuMapper;
import cn.ipanda.aigou.query.ProductQuery;
import cn.ipanda.aigou.service.IProductService;
import cn.ipanda.aigou.util.PageList;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author xwmtest
 * @since 2019-05-08
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    /**
     * @Author: Panda
     * @Description: 分布式的事务比较复杂！注入mapper依赖！
     * @Date: Administrator  * @param null :  12:07  * @return : null
     */
    @Autowired
    private ProductExtMapper productExtMapper;
    /**
     * @Author: Panda
     * @Description: 分布式的事务比较复杂！注入mapper依赖！
     * @Date: Administrator  * @param null 2019-05-29 1:50  * @return : null
     */
    @Autowired
    private SkuMapper skuMapper;

    /**
     * @Author: Panda
     * @Description: 保存product表，同时保存ProductExt表！
     * @Date: Administrator  * @param null :  12:07  * @return : null 2019/5/23
     */
    @Override
    public boolean insert(Product entity) {
        boolean b = super.insert(entity);           //保存Product表！同时保存productExt自动返回注解！insert中的三属性！
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());    //mp返回保存数据主键
        productExtMapper.insert(productExt);
        return b;
    }

    @Override
    public PageList<Product> selectQuer(ProductQuery query) {
        PageList<Product> pageList = new PageList<>();
        long totalCount = productMapper.queryPageCount(query);         //设置总页数
        if (totalCount > 0) {
            pageList.setTotal(totalCount);                          //mp的对象总的条数page.getToal()
            List<Product> products = productMapper.queryPage(query);
            pageList.setRows(products);                               //设置当前分页的数据
        }
        return pageList;
    }

    @Override
    public void addSku(Object productId, List<Map<String, Object>> skuProperties, List<Map<String, Object>> skuDatas) {
        //  更新ProductExt表的skuProperties
        //  通过productId更新ProductExt表中的skuProperties字段！
        ProductExt entity = new ProductExt();
        entity.setSkuProperties(JSONArray.toJSONString(skuProperties));
        Wrapper<ProductExt> wrapper = new EntityWrapper<>();
        wrapper.eq("productId", productId);
        productExtMapper.update(entity, wrapper);
        //  保存sku表的：一个是sku表的自身字段：价格，库存等等等！还有就是
        //skuDatas:
        //  0: {颜色: "yellow", 尺寸: "99", price: "999", availableStock: "1000"}
        //  1: {颜色: "yellow", 尺寸: "100", price: "1000", availableStock: "1200"}
        //  2: {颜色: "red", 尺寸: "99", price: "998", availableStock: "1222"}
        //  3: {颜色: "red", 尺寸: "100", price: "1001", availableStock: "1688"}

        for (Map<String, Object> skuData : skuDatas) {
            //sku对象
            Sku sku = new Sku();
            //2.1设置productId！
            sku.setProductId(Long.valueOf(productId.toString()));
            Set<Map.Entry<String, Object>> skuDataEntry = skuData.entrySet();
            //skuData=0: {颜色: "yellow", 尺寸: "99", price: "999", availableStock: "1000"}
            List<Map<String, Object>> otherList = new ArrayList<>();//sku值中的对应的属性表中的相关信息！
            for (Map.Entry<String, Object> entry : skuDataEntry) {
                Map<String, Object> otherMap = new HashMap<>();
                String key = entry.getKey();
                Object value = entry.getValue();
                //2.2自身的属性
                if ("price".equals(key)) {
                    sku.setPrice(Integer.valueOf(value.toString()));
                } else if ("availableStock".equals(key)) {
                    sku.setAvailableStock(Integer.valueOf(value.toString()));
                } else {
                    //其他的属性！
                    otherMap.put(key, value);
                    otherList.add(otherMap);
                }
            }
            //============skuProperties设置开始============
            //otherList:[{"颜色":"yellow"},{"尺寸":"99"}]
            //最终的skuProperties [{"id": 33, "key": "颜色","value":"yellow"}]
            //sku的保存在前面都是构造这个sku的各个字段值！
            List<Map<String, Object>> skuList = new ArrayList<>();
            for (Map<String, Object> om : otherList) {
                //{"颜色":"yellow"}
                Map<String, Object> mm = new HashMap<>();
                Set<Map.Entry<String, Object>> entries = om.entrySet();
                String properKey = "";
                for (Map.Entry<String, Object> entry : entries) {
                    properKey = entry.getKey();
                    mm.put("key", properKey);
                    mm.put("value", entry.getValue());
                }
                Long propertyId = getPropId(properKey, skuProperties);
                mm.put("id", propertyId);
                skuList.add(mm);
            }
            //设置SkuProperties:
            sku.setSkuProperties(JSONArray.toJSONString(skuList));
            //============skuProperties设置结束============
            //============skuIndex设置开始============
            StringBuffer sb = new StringBuffer();
            for (Map<String, Object> om : skuList) {
                // om : {"id":33,"key":"颜色","value":"yellow"}
                //获取属性id
                Object proId = om.get("id");
                Object value = om.get("value");
                Integer index = getIndex(proId, value, skuProperties);
                System.out.println("idnex==" + index);
                sb.append(index).append("_");
            }


            // sb 1_2_4_
            //去掉最后一个_
            String sbStr = sb.toString();
            sbStr = sbStr.substring(0, sb.lastIndexOf("_"));
            sku.setSkuIndex(sbStr);
            //============skuIndex设置结束============
            //sku的保存:在前面都是在构造这个sku的各个字段值
            skuMapper.insert(sku);
        }
    }

    /**
     * @param proId         属性的id
     * @param value         属性的value
     * @param skuProperties list
     * @return
     */
    private Integer getIndex(Object proId, Object value, List<Map<String, Object>> skuProperties) {
        for (Map<String, Object> skuProperty : skuProperties) {
            //{id=33, specName=颜色, type=2, productTypeId=9, value=null,skuValues=[yellow, green]}
            Long id = Long.valueOf(skuProperty.get("id").toString());
            Long pro = Long.valueOf(proId.toString());
            // java.lang.Integer cannot be cast to java.lang.Long
            if (id.longValue() == pro.longValue()) {
                List<String> skuValues = (List<String>) skuProperty.get("skuValues");
                int index = 0;
                for (String skuValue : skuValues) {
                    if (skuValue.equals(value.toString())) {
                        return index;
                    }
                    index++;
                }
            }
        }
        return null;
    }
    /**
     * 根据skuProperties属性的key获取这个属性的id
     *
     * @param properKey
     * @param skuProperties
     * @return
     */
    private Long getPropId(String properKey, List<Map<String, Object>> skuProperties) {
        for (Map<String, Object> skuProperty : skuProperties) {
            String specName = (String) skuProperty.get("specName");
            if (specName.equals(properKey)) {
                return Long.valueOf(skuProperty.get("id").toString());
            }
        }
        return null;
    }
}
