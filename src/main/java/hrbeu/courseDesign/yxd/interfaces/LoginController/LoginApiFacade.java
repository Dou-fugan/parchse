package hrbeu.courseDesign.yxd.interfaces.LoginController;

import hrbeu.courseDesign.yxd.domain.mapper.login.RegisterUserMapper;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.RegisterUser;
import hrbeu.courseDesign.yxd.domain.shiro.dao.UserRepository;
import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.domain.shiro.service.UserLoginApiService;
import hrbeu.courseDesign.yxd.domain.shiro.vo.Resp;
import hrbeu.courseDesign.yxd.infrastructure.utils.Encrypt;
import hrbeu.courseDesign.yxd.infrastructure.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

@RestController
public class LoginApiFacade {

    @Autowired
    private RegisterUserMapper registerUserMapper;
    @Resource
    private UserLoginApiService userLoginApiService;
    @Resource
    private UserRepository userRepository;
    /**
     * 登陆 API
     *
     * @param params 包含必传信息 用户名 密码
     */
    @PostMapping(value = "/open/api/login1")
    public ResponseEntity<Resp> login1(HttpServletRequest request, @RequestBody Map<String, String> params) {
        HttpSession session=request.getSession();

        String captcha=params.get("captcha");

        if(session.getAttribute("imageCode").toString().equalsIgnoreCase(captcha)){

            if(params.containsKey("username") && params.containsKey("password")){
                String username = params.get("username");
                String password = params.get("password");
                return userLoginApiService.login(username, password);
            } else {
                return ResponseEntity.badRequest().body(new Resp("缺少重要参数或参数无效"));
            }

        }else {
            return ResponseEntity.badRequest().body(new Resp("验证码错误"));
        }
    }

    @PostMapping(value = "/open/api/login2")
    public ResponseEntity<Resp> login2(HttpServletRequest request,@RequestBody Map<String, String> params) {

        HttpSession session=request.getSession();

        String captcha=params.get("captcha");

        if(session.getAttribute("imageCode").toString().equalsIgnoreCase(captcha)){

            if(params.containsKey("username") && params.containsKey("password")){
                String username = params.get("username");
                String password = params.get("password");
                return userLoginApiService.login(username, password);
            } else {
                return ResponseEntity.badRequest().body(new Resp("缺少重要参数或参数无效"));
            }

        }else {
            return ResponseEntity.badRequest().body(new Resp("验证码错误"));
        }
    }

    @PostMapping(value = "/open/api/jump")
    @ResponseBody
    public String jump(@RequestBody Map<String, String> params) {
        //查询当前用户id对应的角色，返回角色id
        if (params.containsKey("username")) {
            String username = params.get("username");
            User user = userRepository.findByUsername(username);
            return user.getRoles().get(0).getName();//对于医生预期是4
        } else {
            return "缺少用户名选项";
        }
    }
    /**
     * 登出 API
     */
    @PostMapping(value = "/open/api/logout")
    public ResponseEntity<Resp> logout() {
        return userLoginApiService.logout();
    }

    /**
     * 404跳转之身份验证
     * @return
     */
    @GetMapping(value = "/open/api/recgAgent")
    public String recgAgent() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user!=null){
            return user.getRoles().get(0).getName();
        }else {
            return "visitor";
        }
    }

    @GetMapping (value = "/open/api/getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyCodeUtils.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    //注册操作
    @Transactional
    @PostMapping("/open/api/register")
    public ResponseEntity<Resp> registerAdd(HttpServletRequest request,@RequestBody RegisterUser registerUser){

        HttpSession session=request.getSession();

        String captcha=registerUser.captcha;

        if(session.getAttribute("imageCode").toString().equalsIgnoreCase(captcha)){
            System.out.println("验证注册成功");
        }else {
            return ResponseEntity.badRequest().body(new Resp("验证码错误"));
        }

        System.out.println("收到注册请求:"+registerUser);
        String password= registerUser.getPassword();
        String username = registerUser.getUsername();

        Encrypt encrypt = new Encrypt();
        Object securePassword = encrypt.encrypt(username, password);
        String passwordSalt = "5371f568a45e5ab1f442c38e0932aef24447139b";//密钥
        //围绕roleName来确定流程
        try {
            if(registerUserMapper.selectSystemShiroUser(registerUser.username)>0){
                throw new Exception("用户名不可重复");
            }
            if(registerUser.roleName==null){
                throw new Exception("没有指明roleName");
            }else{
                if(registerUser.roleName.equals("reviewer")){
                    if (registerUser.adminPwd.equals("abc")){
                        registerUserMapper.insertSystemShiroUser(username,securePassword.toString(),passwordSalt,registerUser.usccode);
                        int user_id=registerUserMapper.lastInsert();
                        registerUserMapper.insertSystemShiroUserRole(user_id,1);
                    }else {
                        throw new Exception("错误的adminPwd");
                    }
                }else if(registerUser.roleName.equals("manufacturer")){
                    if(registerUser.usccode!=null){
                        if(registerUserMapper.selectManufacturerNumber(registerUser.usccode)==0){
                            registerUserMapper.insertManufacturer(registerUser.usccode);
                        }
                        registerUserMapper.insertSystemShiroUser(username,securePassword.toString(),passwordSalt,registerUser.usccode);
                        int user_id=registerUserMapper.lastInsert();
                        registerUserMapper.insertSystemShiroUserRole(user_id,0);

                        //非标准语法
                        //$sql = "insert into weixin_user(wx_id, wx_name, wx_state, wx_info, wx_lasttime) values ('$wx_id', '$wx_name', '$wx_state', '$wx_info', NOW()) ON DUPLICATE KEY UPDATE wx_name='$wx_name', wx_state = '$wx_state', wx_info = '$wx_info', wx_lasttime = NOW();";
                        //如果重复插入会报错Duplicate entry '1' for key 'manufacturer.PRIMARY'
                    }else{
                        throw new Exception("缺少usccode参数");
                    }
                }else{
                    throw new Exception("错误的roleName");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(new Resp(e.getMessage()));
        }

        return ResponseEntity.ok((new Resp("注册成功")));
    }
}
