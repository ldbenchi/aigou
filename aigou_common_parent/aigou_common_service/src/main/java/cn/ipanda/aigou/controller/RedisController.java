package cn.ipanda.aigou.controller;
import cn.ipanda.aigou.client.RedisClient;
import cn.ipanda.aigou.util.RedisUtil;
import org.springframework.web.bind.annotation.*;
/**
 * @Author: Panda
 * @Description: 实现RedisClient起到抽取的作用和约束的作用！
 * @Date: Administrator  * @param null :  23:00  * @return : null 2019/5/12
 */
@RestController
@RequestMapping("/common")
public class RedisController implements RedisClient {
    @Override
    @RequestMapping(value = "/redis", method = RequestMethod.POST)
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {
        RedisUtil.set(key, value);
    }

    @Override
    @RequestMapping(value = "/redis/{key}", method = RequestMethod.GET)
    public String get(@PathVariable("key") String key) {
        return RedisUtil.get(key);
    }
}
