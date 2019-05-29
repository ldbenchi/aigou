package cn.ipanda.aigou.controller;

import cn.ipanda.aigou.service.IBrandService;
import cn.ipanda.aigou.domain.Brand;
import cn.ipanda.aigou.query.BrandQuery;
import cn.ipanda.aigou.util.AjaxResult;
import cn.ipanda.aigou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    public IBrandService brandService;
    /**
     * 保存和修改公用的
     * @param brand 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Brand brand) {
        try {
            if (brand.getId() != null) {
                brandService.updateById(brand);
            } else {
                brandService.insert(brand);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("保存对象失败！" + e.getMessage());
        }
    }
    /**
     * 删除对象信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            brandService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！" + e.getMessage());
        }
    }
    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Brand get(@PathVariable("id") Long id) {
        return brandService.selectById(id);
    }
    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Brand> list() {
        return brandService.selectList(null);
    }
    /**
     * 分页查询数据
     * page是mp的:mp对多表的关联查询不尽人意,我们用以前的方式自己写:
     * 定义一个service:query-->pageList ===>Mapper接口:定义这样一个方法===>Mapper.xml:
     * xml: sql的关联查询: 需要封装一个关联对象,也要做条件:
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Brand> json(@RequestBody BrandQuery query) {
        PageList<Brand> pageList = brandService.queryPage(query);
        return pageList;
    }
}
