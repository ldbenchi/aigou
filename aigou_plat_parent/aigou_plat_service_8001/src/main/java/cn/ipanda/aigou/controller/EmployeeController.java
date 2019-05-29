package cn.ipanda.aigou.controller;
import cn.ipanda.aigou.domain.Employee;
import cn.ipanda.aigou.util.AjaxResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/*public class EmployeeController {
    public static void main(String[] args) {
        *//*AjaxResult ajaxResult=new AjaxResult("失败了");
        String msg = ajaxResult.getMsg();
        ajaxResult.setMsg("sf");
        StringBuffer stringBuffer=new StringBuffer();
        StringBuffer s = stringBuffer.append("s").append("5");*//*
        AjaxResult.me().setMsg("ss").setSuccess(true);
    }
}*/
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    //restful:地址尽量不用动词：获取一个员工：localhost:8001/employee/getById?id
    //@RequestMapping("/login")
    //@RequestBody把请求body中的数据，springmvc的原理封装进去
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        //模拟登陆因为需要数据库比对，本来要获取到用户信息所以模拟！
        if("admin".equals(employee.getName())&&"admin".equals(employee.getPassword())){
            return AjaxResult.me().setSuccess(true).setMsg("登陆成功");
        }else {
            return AjaxResult.me().setSuccess(false).setMsg("登陆失败");
        }
    }
}
