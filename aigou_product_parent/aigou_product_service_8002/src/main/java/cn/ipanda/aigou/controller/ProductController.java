package cn.ipanda.aigou.controller;

import cn.ipanda.aigou.domain.ProductExt;
import cn.ipanda.aigou.domain.Specification;
import cn.ipanda.aigou.service.IProductExtService;
import cn.ipanda.aigou.service.IProductService;
import cn.ipanda.aigou.domain.Product;
import cn.ipanda.aigou.query.ProductQuery;
import cn.ipanda.aigou.service.ISpecificationService;
import cn.ipanda.aigou.util.AjaxResult;
import cn.ipanda.aigou.util.PageList;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;
    @Autowired
    public ISpecificationService specificationService;
    @Autowired
    public IProductExtService productExtService;


    /**
     * 保存和修改公用的
     *
     * @param product 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product) {
        try {
            if (product.getId() != null) {
                productService.updateById(product);
            } else {
                productService.insert(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            productService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id) {
        return productService.selectById(id);
    }


    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Product> list() {

        return productService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query) {
        return productService.selectQuer(query);
    }

    /**
     * @Author: Panda
     * @Description: let skuPropertiesUrl = "/aigou/product/product/skuProperties/" + productTypeId+"/"+productId;
     * @Date: Administrator  * @param null :  21:00  * @return : null 2019/5/27
     */

    @RequestMapping(value = "/skuProperties/{productTypeId}", method = RequestMethod.GET)
    public List<Specification> skuProperties(@PathVariable("productTypeId") Long productTypeId) {
        //要判断是新增还是修改：判断是对当前产品显示属性是添加还是新增
        //productExt表中有viewProperties：修改：需要前台传productId给我！
        //一个是对象为空（IsNull），一个是值为空（IsEmpty）
        //么有就是添加
        List<Specification> specifications = getSpecifications(productTypeId, 2L);
        return specifications;
    }

    // 前台的响应！
    //Request URL: http://localhost:8848/aigou/product/product/skuProperties
    //Request Method: POST
    //Status Code: 200 OK
    //Remote Address: [::1]:8848
    //Referrer Policy: no-referrer-when-downgrade
    //access-control-allow-credentials: true
    //access-control-allow-origin: http://localhost:8848
    //connection: close
    //content-type: application/json;charset=UTF-8
    //date: Tue, 28 May 2019 14:49:46 GMT
    //transfer-encoding: chunked
    //vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers
    //X-Powered-By: Express
    //{productId: 66,…}
    //productId: 66
    //skuDatas:
    // [
    // {颜色: "yellow", 尺寸: "99", price: "999", availableStock: "1000"},…]
    //  0: {颜色: "yellow", 尺寸: "99", price: "999", availableStock: "1000"}
    //  1: {颜色: "yellow", 尺寸: "100", price: "1000", availableStock: "1200"}
    //  2: {颜色: "red", 尺寸: "99", price: "998", availableStock: "1222"}
    //  3: {颜色: "red", 尺寸: "100", price: "1001", availableStock: "1688"}
    //skuProperties:
    // [
    //  {id: 33, specName: "颜色", type: 2, productTypeId: 9, value: null, skuValues: ["yellow", "red"]},…]
    //  0: {id: 33, specName: "颜色", type: 2, productTypeId: 9, value: null, skuValues: ["yellow", "red"]}
    //  1: {id: 34, specName: "尺寸", type: 2, productTypeId: 9, value: null, skuValues: ["99", "100"]}
    //  json	list	treeData	__webpack_hmr	json	9	skuProperties

    // 控制台的输出！
    //  productId,skuProperties,skuDatas
    //  productId:66
    //skuProperties:
    //  [
    //  {id=33, specName=颜色, type=2, productTypeId=9, value=null, skuValues=[yellow, red]},
    //  {id=34, specName=尺寸, type=2, productTypeId=9, value=null, skuValues=[99, 100]}]
    //skuDatas:
    //  [
    //  {颜色=yellow, 尺寸=99, price=999, availableStock=1000},
    //  {颜色=yellow, 尺寸=100, price=1000, availableStock=1200},
    //  {颜色=red, 尺寸=99, price=998, availableStock=1222},
    //  {颜色=red, 尺寸=100, price=1001, availableStock=1688}]
    @RequestMapping(value = "/skuProperties", method = RequestMethod.POST)
    public AjaxResult saveSkuProperties(@RequestBody Map<String, Object> map) {
        try {
            //目的更新t_product_ext表的一个viewProperties
            //①先根据productId查询到ProductExt
            //①接收前台的数据
            Object productId = map.get("productId");
            //前台的传过来的添加显示属性的list
            System.out.println("productId:" + productId);
            List<Map<String,Object>> skuProperties = (List<Map<String,Object>>) map.get("skuProperties");//List<>
            System.out.println("skuProperties:" + skuProperties);
            //skuDatas
            List<Map<String, Object>> skuDatas = (List<Map<String, Object>>) map.get("skuDatas");
            System.out.println("skuDatas:" + skuDatas);
            //调用方法！
            productService.addSku(productId,skuProperties,skuDatas);

            //②viewProperties转换成json字符
            return AjaxResult.me().setMsg("显示属性保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("显示属性保存失败" + e.getMessage());
        }
    }

    /**
     * @Author: Panda
     * @Description: productTypeId表示商品类型
     * type表示显示属性， type=1 显示属性;type=2 sku属性！
     * @Date: Administrator  * @param null :  21:30  * @return : null 2019/5/27
     */

    private List<Specification> getSpecifications(Long productTypeId, Long type) {
        Wrapper<Specification> wrapper = new EntityWrapper<>();
        wrapper.eq("product_type_id", productTypeId);
        wrapper.eq("type", type);
        List<Specification> specifications = specificationService.selectList(wrapper);
        for (Specification specification : specifications) {
            System.out.println("specification:" + specification);
        }
        return specifications;
    }
    /**
     *
     * @Author: Panda
     * @return
     * @data:
     * @return:
     */

    /**
     * 根据产品id分类获取这个类的显示属性：服务是给前台调用：不搞托底策略feign！
     *
     * @Author: Panda
     * @Description: 这是测试！http://localhost:9527/swagger-ui.html?urls.primaryName=%E5%95%86%E5%93%81%E7%B3%BB%E7%BB%9F%E6%9C%8D%E5%8A%A1#/product-controller/viewPropertiesUsingGET
     * @Date: Administrator  * @param null :  23:25  * @return : null 2019/5/25
     * Request URL
     * http://localhost:9527/aigou/product/product/viewProperties/3
     */
    @RequestMapping(value = "/viewProperties/{productTypeId}/{productId}", method = RequestMethod.GET)
    public List<Specification> viewProperties(@PathVariable("productTypeId") Long productTypeId,
            @PathVariable("productId") Long productId) {
        //要判断是新增还是修改：判断是对当前产品显示属性是添加还是新增
        //productExt表中有viewProperties：修改：需要前台传productId给我！
        //一个是对象为空（IsNull），一个是值为空（IsEmpty）
        ProductExt productExt = productExtService.selectOne(new EntityWrapper<ProductExt>().eq("productId", productId));
        //if (productExt != null && !productExt.getViewProperties().isEmpty()) {
        if (productExt != null && !StringUtils.isEmpty(productExt.getViewProperties())) {
            //有显示属性是修改
            String strArrJson = productExt.getViewProperties();
            return JSONArray.parseArray(strArrJson, Specification.class);
        } else {
            //么有就是添加
            List<Specification> specifications = getSpecifications(productTypeId, 1L);
            return specifications;
        }
    }
    //let skuPropertiesUrl = "/aigou/product/product/skuProperties/" + productTypeId;


    /**
     * @Author: Panda
     * @Description: 把这个存到t_product_ext表的一个viewProperties，里面根据productId进行过滤！
     * @Date: Administrator  * @param null :  0:34  * @return : null 2019/5/27
     */
    @RequestMapping(value = "/viewProperties", method = RequestMethod.POST)
    public AjaxResult saveViewProperties(@RequestBody Map<String, Object> map) {
        try {
            //目的更新t_product_ext表的一个viewProperties
            //①先根据productId查询到ProductExt
            //①接收前台的数据
            Object productId = map.get("productId");
            //前台的传过来的添加显示属性的list
            List<Specification> viewProperties = (List<Specification>) map.get("viewProperties");//List<>
            //②viewProperties转换成json字符
            String toJsonString = JSONArray.toJSONString(viewProperties);
            //③更新到ProductExt的viewProperties种，需要json的字符串；


            //productExt.setViewProperties("{\"http://localhost:9527/aigou/product/product/viewProperties\":\"panda123productId65\"}");
            ProductExt productExt = new ProductExt();
            productExt.setViewProperties(toJsonString);
            productExtService.update(productExt, new EntityWrapper<ProductExt>().eq("productId", productId));
            return AjaxResult.me().setMsg("显示属性保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("显示属性保存失败" + e.getMessage());
        }
    }
}
