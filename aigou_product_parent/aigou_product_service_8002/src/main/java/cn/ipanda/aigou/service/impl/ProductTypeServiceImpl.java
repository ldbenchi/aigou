package cn.ipanda.aigou.service.impl;

import cn.ipanda.aigou.client.PageStaticClient;
import cn.ipanda.aigou.client.RedisClient;
import cn.ipanda.aigou.constants.GlobelConstants;
import cn.ipanda.aigou.domain.ProductType;
import cn.ipanda.aigou.mapper.ProductTypeMapper;
import cn.ipanda.aigou.service.IProductTypeService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author xwmtest
 * @since 2019-05-08
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Autowired
    private RedisClient redisClient;    //注入
    @Autowired
    private PageStaticClient pageStaticClient;
    /**
     * ①先得到一级目录！
     * ②得到0的儿子！
     * ③遍历这个0的儿子！
     * ④分别得到他的儿子！
     * ⑤遍历这个儿子目录的儿子！
     * ⑥递归的遍历下去，直到找到么有儿子的时候就返回！
     *
     * @Author: Panda
     * @Description: treeDate的数据！
     * @Date: Administrator  * @param null :  12:53  * @return : null 2019/5/10
     */
   /* @Override
    public List<ProductType> treeData() {
        return treeDataLoop();
    }*/

    /**
     * 先根据key,从redis获取:我是producttype的服务提供者,我要调用公共服务的redis,则是redis的消费者:
     * java内部的服务的调用,就应该使用feign或者ribbon:选中feign:
     * feign的使用:是在消费者,注入接口,就象调用本地接口一样
     * 判断是否有结果:有就直接返回,没有就从数据库获取,存入redis,并返回
     * 源码！public static boolean isEmpty(String str) {
     * return str == null || str.length() == 0;
     * }
     *
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  16:05  * @return : null 2019/5/13
     */
    @Override
    public List<ProductType> treeData() {
        String jsonArrString = redisClient.get(GlobelConstants.REDIS_PRODUCTTYPE_KEY);
        if (StringUtils.isEmpty(jsonArrString)) {                                   //isEmpty=true=null或0就直接数据库获取，存入redis缓存并返回！
            List<ProductType> productTypes = treeDataLoop();
            jsonArrString = JSONArray.toJSONString(productTypes);
            redisClient.set(GlobelConstants.REDIS_PRODUCTTYPE_KEY, jsonArrString);  //存入redis缓存！
            System.out.println("进入数据库db");
            return productTypes;                                                    //返回！
        } else {
            System.out.println("进入redis缓存");
            return JSONArray.parseArray(jsonArrString, ProductType.class);          //json数组字符串转json数组!有直接返回!
        }
    }

    private List<ProductType> treeDataLoop() {
        List<ProductType> allProductType = productTypeMapper.selectList(null);  //1:获取所有的数据:
        Map<Long, ProductType> map = new HashMap<>();                       //用于存在每一个对象和他的一个标识的 Long:id
        for (ProductType productType : allProductType) {
            map.put(productType.getId(), productType);
        }
        List<ProductType> result = new ArrayList<>();                           //最终想要的结果:
        for (ProductType productType : allProductType) {                        //3:遍历
            Long pid = productType.getPid();                                    //组装结构: productType:每一个对象:
            if (pid == 0) {
                result.add(productType);
            } else {
                ProductType parent = map.get(pid);    // where id =pid      // 找自己的老子,把自己添加到老子的儿子中
                //  List<ProductType> children = parent.getChildren();      //我老子的儿子
                //  hildren.add(productType);                               //把我自己放入老子的儿子中
                parent.getChildren().add(productType);
            }
        }
        return result;
    }

    /**
     * 使用循环方式:
     * 我们期望发送一条sql,把所有的子子孙孙的结构搞出来,但是搞不出来的;
     * 但是我们可以发送一条sql:把所有的数据拿回来,存在内存中,我可以写代码组装他的结构(在内存中完成的).
     *
     * @return
     */
    private List<ProductType> treeDataRecursion(Long pid) {
        List<ProductType> children = getAllChildren(pid);                       //[1,100]获取一级目录；！
        if (children == null || children.size() == 0) {
            return children;                                                    //children=空或者=0！就返回自己；！
        }
        for (ProductType child : children) {                                    // child:1；！
            List<ProductType> allChildren = treeDataRecursion(child.getId());   //查询1的儿子；！
            child.setChildren(allChildren);                                     //把1的儿子给1；！
        }
        return children;
    }

    /**
     * @Author: Panda
     * @Description: treeDate的数据！pid的儿子！
     * @Date: Administrator  * @param null :  12:53  * @return : null 2019/5/10
     */
    private List<ProductType> getAllChildren(long pid) {
        Wrapper<ProductType> wrapper = new EntityWrapper<>();       //SELECT * FROM t_product_type WHERE pid=????;
        wrapper.eq("pid", pid);                             //where pid =#{pid}
        return productTypeMapper.selectList(wrapper);
    }

    /**
     * 修改:本身数据的修改不会变;修改完后,重新生成模板:
     * 1:数据修改:
     * 2:模板的生成:此时此时,这个是模板的消费者:消费模板的提供者:
     * 这个是java后台内部的服务的消费:feign/ribbon(采纳feign)
     * feign:注入模板接口,调用
     * 逻辑实现:
     *
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  18:57  * @return : null 2019/5/15
     */
    @Override
    public boolean updateById(ProductType entity) {
        boolean b = super.updateById(entity);                           //修改:本身数据的修改不会变;修改完后,重新生成模板:1:数据修改！
        Map<String, Object> mapProductType = new HashMap<>();           //2.1:先生成改变数据的html页面:productType！
        List<ProductType> productTypes = treeDataLoop();
        mapProductType.put(GlobelConstants.PAGE_MODE, productTypes);    //这里页面需要的是所有的产品类型数据！
        mapProductType.put(GlobelConstants.PAGE_TEMPLATE, "C:\\resource-maven\\NEW_SVN\\aigou_parent\\aigou_common_parent\\aigou_common_interface\\src\\main\\resources\\template\\product.type.vm");          //哪一个模板！
        mapProductType.put(GlobelConstants.PAGE_TEMPLATE_HTML, "C:\\resource-maven\\NEW_SVN\\aigou_parent\\aigou_common_parent\\aigou_common_interface\\src\\main\\resources\\template\\product.type.vm.html");     //根据模板生成的页面的地址！
        pageStaticClient.getPageStatic(mapProductType);                 //这里已经能够拿到值了！

        Map<String, Object> mapHome = new HashMap<>();                  //2.2:再生成home的html页面！
        Map<String, String> staticRootMap = new HashMap<>();            //数据:$model.staticRoot！

        staticRootMap.put("staticRoot", "C:\\resource-maven\\NEW_SVN\\aigou_parent\\aigou_common_parent\\aigou_common_interface\\src\\main\\resources\\");
        mapHome.put(GlobelConstants.PAGE_MODE, staticRootMap);          //这里页面需要的是目录的根路径！

        mapHome.put(GlobelConstants.PAGE_TEMPLATE, "C:\\resource-maven\\NEW_SVN\\aigou_parent\\aigou_common_parent\\aigou_common_interface\\src\\main\\resources\\template\\home.vm");                 //哪一个模板!
        mapHome.put(GlobelConstants.PAGE_TEMPLATE_HTML, "C:\\resource-maven\\NEW_SVN\\aigou_parent\\aigou_common_parent\\aigou_common_interface\\src\\main\\resources\\template\\home.html");            //根据模板生成的页面的地址！
        pageStaticClient.getPageStatic(mapHome);
        return b;
    }
}
