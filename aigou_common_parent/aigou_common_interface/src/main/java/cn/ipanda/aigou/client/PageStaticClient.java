package cn.ipanda.aigou.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;
/**
 * 页面静态客户端
 *
 * @Author: Panda
 * @Description:
 * @Date: Administrator  * @param null :  16:40  * @return : null 2019/5/14
 */
@FeignClient(value = "COMMON-PRIVODER", fallbackFactory = PageStaticClientFactory.class)
@RequestMapping("/common")
public interface PageStaticClient {
    /**
     *  生成动态模板
     *  根据给定数据，和制定的模板！最终生成一个HTML的页面！
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  16:42  * @return : null 2019/5/14
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    void getPageStatic(@RequestBody Map<String,Object> map);
}