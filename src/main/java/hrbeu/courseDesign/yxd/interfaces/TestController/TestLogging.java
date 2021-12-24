package hrbeu.courseDesign.yxd.interfaces.TestController;

import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.infrastructure.annotation.LogRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestLogging {

    @LogRecord(type="creatA",body = "#b")
    @GetMapping("/open/api/logging")
    String logging(HttpServletRequest request, @Param("a") int a,@Param("b") int b){
        System.out.println(a+','+b);
        return "返回信息";
    }
}

class a{
    int a;
    int b;
}
