package hrbeu.courseDesign.yxd.domain.mapper.login;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hrbeu.courseDesign.yxd.domain.pojo.SystemShiroUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RegisterUserMapper extends BaseMapper<SystemShiroUser> {

    @Insert("INSERT INTO system_shiro_user(username,password,password_salt,usccode)\n" +
            "VALUE(#{username},#{password},#{password_salt},#{usccode})")
    void insertSystemShiroUser(String username,String password,String password_salt,String usccode);

    @Insert("INSERT INTO system_shiro_user_roles(user_id,roles_id)\n" +
            "VALUE(#{user_id},#{roles_id})" +
            "ON DUPLICATE KEY UPDATE user_id=#{user_id};")
    void insertSystemShiroUserRole(int user_id, int roles_id);

    @Select("SELECT COUNT(*) FROM manufacturer WHERE usccode = #{usccode};")
    int selectManufacturerNumber(String usccode);

    @Insert("INSERT INTO manufacturer(usccode,manufacturer_name,address,agent,telephone_number)\n" +
            "VALUE(#{usccode},null,null,null,null)" +
            "ON DUPLICATE KEY UPDATE usccode=#{usccode};")
    void insertManufacturer(String usccode);

    @Select("SELECT LAST_INSERT_ID()")
    int lastInsert();

    @Select("SELECT COUNT(*) FROM system_shiro_user WHERE username=#{username}")
    int selectSystemShiroUser(String username);
    @Select("SELECT usccode FROM system_shiro_user WHERE id= #{user_id};")
    String getUsccode(String user_id);
}
