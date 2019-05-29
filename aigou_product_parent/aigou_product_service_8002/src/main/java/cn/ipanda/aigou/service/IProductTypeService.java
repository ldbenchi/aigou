package cn.ipanda.aigou.service;
import cn.ipanda.aigou.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
/**
 * <p>
 * 商品目录 服务类
 * </p>
 * @author xwmtest
 * @since 2019-05-08
 */
public interface IProductTypeService extends IService<ProductType> {

    /**
     * @Author: Panda
     * @Description: treeDate 的数据！
     * @Date: Administrator  * @param null :  12:51  * @return : null 2019/5/10
     */
    List<ProductType> treeData();

}
