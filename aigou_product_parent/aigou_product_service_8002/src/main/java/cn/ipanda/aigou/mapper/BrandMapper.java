package cn.ipanda.aigou.mapper;
import cn.ipanda.aigou.domain.Brand;
import cn.ipanda.aigou.query.BrandQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 * @author xwmtest
 * @since 2019-05-08
 */
public interface BrandMapper extends BaseMapper<Brand> {
   List<Brand> queryPage(BrandQuery query);
   long queryPageCount(BrandQuery query);
}
