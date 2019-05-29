package cn.ipanda.aigou.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "COMMON-PRIVODER", fallbackFactory = RedisClientFactory.class)
@RequestMapping("/common")
public interface RedisClient {  //表示对哪一个服务进行处理
    @RequestMapping(value = "/redis", method = RequestMethod.POST)
    void set(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping(value = "/redis/{key}", method = RequestMethod.GET)
    String get(@PathVariable("key") String key);
}