package cn.ipanda.aigou.service;

import cn.ipanda.aigou.domain.Product;
import cn.ipanda.aigou.domain.Specification;
import cn.ipanda.aigou.query.ProductQuery;
import cn.ipanda.aigou.util.PageList;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author xwmtest
 * @since 2019-05-08
 */
public interface IProductService extends IService<Product> {

    PageList<Product> selectQuer(ProductQuery query);
    /***
     * @param productId 商品的id！
     * @param  skuProperties sku的属性表数据！
     * @param skuDatas  sku的值！
     */
    void addSku(Object productId, List<Map<String,Object>> skuProperties, List<Map<String, Object>> skuDatas);

}
