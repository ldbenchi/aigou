package cn.ipanda.aigou.util;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ①连接池的封装！
 * ②api的而封装！
 *
 * @Author: Panda
 * @Description:
 * @Date: Administrator  * @param null :  22:06  * @return : null 2019/5/12
 */
public class RedisUtil {
    static JedisPool jedisPool=null;
    static {
        GenericObjectPoolConfig poolConfig = new JedisPoolConfig(); //①连接池！以下参数设置！
        poolConfig.setMaxTotal(30);                                 //②最大连接数
        poolConfig.setMaxIdle(10);                                  //③最大空闲数
        poolConfig.setMaxWaitMillis(3 * 1000);                      //④超时时间
        poolConfig.setTestOnBorrow(true);                           //⑤接的时候进行测试！

        String host = "127.0.0.1";                                  //本地ip地址！
        int port = 6379;                                            //端口号！
        int timeout = 5 * 1000;                                     //过期时间！
        String password = "root";                                   //密码！
        jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
    }
    /**设置
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  22:37  * @return : null 2019/5/12
     */
    public static void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();              //①获取jedis实例
        try {
            jedis.set(key, value);                          //②api操作
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();                                  //③资源回收
        }
    }
    /**获取
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  22:37  * @return : null 2019/5/12
     */
    public static String get(String key) {
        Jedis jedis = jedisPool.getResource();              //①获取jedis实例
        try {
            return jedis.get(key);                          //②api操作
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();                                  //③资源回收
        }
    }
}
