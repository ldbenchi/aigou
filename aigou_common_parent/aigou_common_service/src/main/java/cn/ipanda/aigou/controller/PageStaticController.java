package cn.ipanda.aigou.controller;
import cn.ipanda.aigou.client.PageStaticClient;
import cn.ipanda.aigou.constants.GlobelConstants;
import cn.ipanda.aigou.util.VelocityUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/**
 * VelocityUtils的Object model, String templateFilePathAndName, String targetFilePathAndName
 * model：数据对象
 * templateFilePathAndName：模板文件的物理路径
 * targetFilePathAndName：目标输出文件的物理路径
 *
 * @Author: Panda
 * @Description: 实现PageStaticClient起到抽取的作用和约束的作用！
 * @Date: Administrator  * @param null :  16:54  * @return : null 2019/5/14
 */
@RestController
@RequestMapping("/common")
public class PageStaticController implements PageStaticClient {
    @Override
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public void getPageStatic(@RequestBody Map<String, Object> map) {
        Object model = map.get(GlobelConstants.PAGE_MODE);                                          //模板的数据：model的key!
        String templateFilePathAndName = (String) map.get(GlobelConstants.PAGE_TEMPLATE);           //哪一个模板!
        String targetFilePathAndName = (String) map.get(GlobelConstants.PAGE_TEMPLATE_HTML);        //最终输出到哪一个html!
        System.out.println("model="+model);                                                         //打印model
        System.out.println("templateFilePathAndName="+templateFilePathAndName);                     //打印templateFilePathAndName
        System.out.println("targetFilePathAndName="+targetFilePathAndName);                         //打印targetFilePathAndName
        VelocityUtils.staticByTemplate(model, templateFilePathAndName, targetFilePathAndName);      //最终逻辑实现！
    }
}
