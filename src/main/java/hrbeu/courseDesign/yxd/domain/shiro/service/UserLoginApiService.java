package hrbeu.courseDesign.yxd.domain.shiro.service;

import hrbeu.courseDesign.yxd.application.RBACService;
import hrbeu.courseDesign.yxd.domain.shiro.dao.UserRepository;
import hrbeu.courseDesign.yxd.domain.shiro.vo.Resp;
import hrbeu.courseDesign.yxd.infrastructure.utils.DrugSystemToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by EalenXie on 2019/3/26 16:31.
 */
@Service
public class UserLoginApiService implements RBACService {

    @Resource
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Resp> login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        DrugSystemToken token = new DrugSystemToken(username, password.toCharArray());
        subject.login(token);// 执行认证登陆
        return ResponseEntity.ok(new Resp("登录成功"));
    }

    @Override
    public ResponseEntity<Resp> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseEntity.ok(new Resp("登出成功"));
    }

}
