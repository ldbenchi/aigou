package cn.ipanda.aigou.mapper;

import cn.ipanda.aigou.domain.Product;
import cn.ipanda.aigou.query.ProductQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author xwmtest
 * @since 2019-05-08
 */
public interface ProductMapper extends BaseMapper<Product> {
    long queryPageCount(ProductQuery query);
    List<Product> queryPage(ProductQuery query);
}
