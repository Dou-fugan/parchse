package hrbeu.courseDesign.yxd.domain.pojo;/*
@date 2021/7/27 - 10:54 上午
*/

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="system_shiro_user")
public class SystemShiroUser {
    private int id;
    private String password ;
    private String password_salt;
    private String username;
}
