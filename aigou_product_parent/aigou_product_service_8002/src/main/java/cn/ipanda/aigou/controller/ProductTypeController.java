package cn.ipanda.aigou.controller;

import cn.ipanda.aigou.service.IProductTypeService;
import cn.ipanda.aigou.domain.ProductType;
import cn.ipanda.aigou.query.ProductTypeQuery;
import cn.ipanda.aigou.util.AjaxResult;
import cn.ipanda.aigou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {
    @Autowired
    public IProductTypeService productTypeService;

    /**
     * 保存和修改公用的
     * productType 传递的实体
     * Ajaxresult转换结果
     *
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  18:26  * @return : null 2019/5/13
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductType productType) {
        try {
            if (productType.getId() != null) {
                productTypeService.updateById(productType);
            } else {
                productTypeService.insert(productType);
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
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  18:25  * @return : null 2019/5/13
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            productTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！" + e.getMessage());
        }
    }

    /**
     * 获取用户
     *
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  18:25  * @return : null 2019/5/13
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductType get(@PathVariable("id") Long id) {
        return productTypeService.selectById(id);
    }

    /**
     * 查看所有的员工信息
     *
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  18:25  * @return : null 2019/5/13
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductType> list() {
        return productTypeService.selectList(null);
    }

    /**
     * 分页查询数据
     * query 查询对象
     *
     * @Author: Panda
     * @Description: PageList 分页对象
     * @Date: Administrator  * @param null :  18:24  * @return : null 2019/5/13
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<ProductType> json(@RequestBody ProductTypeQuery query) {
        Page<ProductType> page = new Page<ProductType>(query.getPage(), query.getRows());
        page = productTypeService.selectPage(page);
        return new PageList<ProductType>(page.getTotal(), page.getRecords());
    }

    /**
     * this.$http.get("/product/productType/treeData")
     *
     * @Author: Panda
     * @Description: 分页对象
     * @Date: Administrator  * @param null :  18:24  * @return : null 2019/5/13
     */
    @RequestMapping(value = "/treeData", method = RequestMethod.GET)
    public List<ProductType> treeData() {
        return productTypeService.treeData();
    }
}
