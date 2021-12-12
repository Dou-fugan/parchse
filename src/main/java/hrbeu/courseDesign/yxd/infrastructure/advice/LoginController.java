package hrbeu.courseDesign.yxd.infrastructure.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by EalenXie on 2019/3/26 16:03.
 */
@Controller
public class LoginController {


    /**
     * 登陆页
     */
    @RequestMapping("/enter")
    public String login() {
        return "/AuthNo/login2.html";
    }

    /**
     * 退出 后跳转登陆页
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout() {
//        return "登出成功";

        return null;
    }

    /**
     * 首页
     */
    @RequestMapping({"/", "/index"})
    public String index() {
        return "/AuthNo/login2.html";
    }

    /**
     * 未授权页
     */
    @RequestMapping({"/unauthorized"})
    public String unauthorized() {
        return "/AuthNo/login2.html";
    }

//    @RequestMapping({"/login1"})
//    public String login1() {
//        return "login1";
//    }
}
