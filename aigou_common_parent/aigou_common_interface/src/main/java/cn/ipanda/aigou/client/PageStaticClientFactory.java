package cn.ipanda.aigou.client;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.Map;
/**
 * @Author: Panda
 * @Description: 
 * @Date: Administrator  * @param null :  16:52  * @return : null 2019/5/14
 */
@Component
public class PageStaticClientFactory implements FallbackFactory<PageStaticClient> {
    @Override
    public PageStaticClient create(Throwable cause) {
        return new PageStaticClient() {
            @Override
            public void getPageStatic(Map<String, Object> map) {

            }
        };
    }
}

