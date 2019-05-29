package cn.ipanda.aigou.config;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @Author: Panda
 * @Description: Spring boot方法！
 * @Date: Administrator  * @param null :  18:22  * @return : null 2019/5/13
 */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.ipanda.aigou.mapper")//cn\ipanda\mybatisplus\mapper\UserMapper.xml的路径
public class MybatisPlusConfig {
    /**
     * @Author: Panda
     * @Description: 分页插件
     * @Date: Administrator  * @param null :  18:22  * @return : null 2019/5/13
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
