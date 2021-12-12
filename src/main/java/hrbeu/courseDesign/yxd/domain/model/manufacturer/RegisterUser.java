package hrbeu.courseDesign.yxd.domain.model.manufacturer;

import lombok.Data;

@Data
public class RegisterUser {
    public String username;
    public String password;
    public String usccode;
    public String roleName;
    public String adminPwd;
    public String captcha;
}
