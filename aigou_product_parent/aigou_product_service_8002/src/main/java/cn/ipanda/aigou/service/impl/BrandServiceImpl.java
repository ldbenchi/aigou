package cn.ipanda.aigou.service.impl;

import cn.ipanda.aigou.domain.Brand;
import cn.ipanda.aigou.mapper.BrandMapper;
import cn.ipanda.aigou.query.BrandQuery;
import cn.ipanda.aigou.service.IBrandService;
import cn.ipanda.aigou.util.PageList;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 * 以前在分页查询的时候：需要两个请求：总的条数和当前页分页的数据
 * could not autowire. No beans of 'BrandMapper' type found. more... (Ctrl+F1解决
 * https://blog.csdn.net/kq1983/article/details/84290055
 *
 * @author xwmtest
 * @since 2019-05-08
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;                                //注入mapper
    @Override
    public PageList<Brand> queryPage(BrandQuery query) {
        PageList<Brand> pageList = new PageList<>();
        long totalCount = brandMapper.queryPageCount(query);         //设置总页数
        if (totalCount > 0) {
            pageList.setTotal(totalCount);                          //mp的对象总的条数page.getToal()
            List<Brand> brands = brandMapper.queryPage(query);
            pageList.setRows(brands);                               //设置当前分页的数据
        }                                                           //Mapper.xml中的查询分页的数据rows
        return pageList;
    }
}
