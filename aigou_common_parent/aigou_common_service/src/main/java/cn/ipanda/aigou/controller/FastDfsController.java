package cn.ipanda.aigou.controller;

import cn.ipanda.aigou.util.AjaxResult;
import cn.ipanda.aigou.util.FastDfsApiOpr;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
/**
 * @Author: Panda
 * @Description:
 * @Date: Administrator  * @param null :  16:14  * @return : null 2019/5/21
 */
@RestController
@RequestMapping("/common")
public class FastDfsController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public AjaxResult upload(@RequestBody MultipartFile file) {
        String originalFilename = file.getOriginalFilename();                //原始文件名！
        System.out.println("原名originalFilename:" + originalFilename);      //输出原始文件名字！微信图片_20190509135825.jpg
        String extensionName = FilenameUtils.getExtension(originalFilename); //从原始文件名获取后缀名！
        System.out.println("后缀extensionName:" + extensionName);            //输出原始文件名的后缀名jpg！
        try {                                                                //fastDfs的工具类封装！
            String filePath = FastDfsApiOpr.upload(file.getBytes(), extensionName);
            return AjaxResult.me().setSuccess(true).setMsg("恭喜恭喜！上传成功了哦！:").setObject(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("亲对不起！上传失败了哦！:" + e.getMessage());
        }
    }
    /**
     * ①删除的思路？？？
     * ②要知道delete她要删除哪一个东西！
     *ip/groupname/filename
     * 服务接口定义地址: /common/delete/{filePath}
     * 在访问的时候:输入的请求地址: /common/delete/group1/M00/00/01/wKgAeVzik0-AY1s7AACoNgxz0tM781.jpg
     * 由于我们的参数中本身就有/,对请求路径进行了干扰,所以我们换一个思路:
     * 我们不在请求地址中直接传我的参数,使用key--value的形式传
     * http://localhost:9527/aigou/common/common/delete?filePath=group1%2FM00%2F00%2F01%2FwKgAeVzik0-AY1s7AACoNgxz0tM781.jpg
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  20:11  * @return : null 2019/5/21
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public AjaxResult delete(@RequestParam("filePath") String filePath) {
        try {
            System.out.println("前台的filePath:"+filePath);
            String substring = filePath.substring(1);
            System.out.println("substring:"+substring);
            String groupName = substring.substring(0, substring.indexOf("/"));
            System.out.println("groupName:"+groupName);
            String flieName = substring.substring(substring.indexOf("/") + 1);
            System.out.println("flieName:"+flieName);
            int deleteResult = FastDfsApiOpr.delete(groupName, flieName);
            System.out.println("deleteResult:"+deleteResult);
            if (deleteResult == 0) {
                return AjaxResult.me().setSuccess(true).setMsg("恭喜恭喜！删除成功！");  //删除成功
            } else {
                return AjaxResult.me().setSuccess(false).setMsg("亲对不起！删除失败！"); //删除失败
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("亲对不起！删除失败！" + e.getMessage()); //删除失败
        }
    }
    /**
     * 测试！源码无关的！
     * @Author: Panda
     * @Description:
     * @Date: Administrator  * @param null :  21:16  * @return : null 2019/5/21
     */
    public static void main(String[] args) {
        String filePath = "group1/M00/00/01/wKgAeVzik0-AY1s7AACoNgxz0tM781.jpg";
        String subString = filePath.substring(0, filePath.indexOf("/"));
        String subString1 = filePath.substring(filePath.indexOf("/") + 1);//subString1:M00/00/01/wKgAeVzik0-AY1s7AACoNgxz0tM781.jpg!
        System.out.println("subString:" + subString);//subString:group1
        System.out.println("subString1:" + subString1);//subString1:/M00/00/01/wKgAeVzik0-AY1s7AACoNgxz0tM781.jpg多了一个/!
    }
}
