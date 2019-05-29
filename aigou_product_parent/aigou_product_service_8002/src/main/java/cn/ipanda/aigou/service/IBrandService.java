package cn.ipanda.aigou.service;
import cn.ipanda.aigou.domain.Brand;
import cn.ipanda.aigou.query.BrandQuery;
import cn.ipanda.aigou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 品牌信息 服务类
 * </p>
 * @author xwmtest
 * @since 2019-05-08
 */
public interface IBrandService extends IService<Brand> {
    /**
     * <p>
     * Brand的分页条件查询
     * </p>
     * @author xwmtest
     * @since 2019-05-08
     */
    PageList<Brand> queryPage(BrandQuery query);
}
