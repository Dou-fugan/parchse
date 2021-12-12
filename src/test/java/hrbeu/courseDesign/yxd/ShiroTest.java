package hrbeu.courseDesign.yxd;

import hrbeu.courseDesign.yxd.domain.shiro.dao.UserRepository;
import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.infrastructure.utils.getID;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by EalenXie on 2019/3/25 18:56.
 */
public class ShiroTest {

    @Resource
    private UserRepository userRepository;

    private Object encrypt(String username, String enablePassword) {

        String hashAlgorithmName = "md5";//加密算法

        String passwordSalt = "5371f568a45e5ab1f442c38e0932aef24447139b";//密钥

        String salt = passwordSalt + username + passwordSalt; //盐值

        int hashIterations = 1024; //散列次数

        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);//盐

        return new SimpleHash(hashAlgorithmName, enablePassword, credentialsSalt, hashIterations);

    }



    /**
     * 加密测试
     */
    @Test
    public void encryption() {
        Object securePassword = encrypt("chenjinyi", "123456");
        System.out.println("chenjinyi加密后的算法 : " + securePassword);
        Assert.assertEquals("dde5deadfcaa4267804832b063f4f8f9", securePassword.toString());

        Object zhangsanPassword = encrypt("zhangsan", "12345");
        System.out.println("zhangsan的加密后密码 : " + zhangsanPassword);
        Assert.assertEquals("3b574a9959cd4f8a9a3752d34e0f5f33", zhangsanPassword.toString());

        Object wuyifanPassword = encrypt("wuyifan", "12345");
        System.out.println("wuyifan的加密后密码 : " + wuyifanPassword);
        Assert.assertEquals("797342f5d276c299bd7537cfc4bc6f9b", wuyifanPassword.toString());

        Object lisiPassword = encrypt("3d1a32c2a11641b5551f1cbfc0a007e3", "12345");
        System.out.println("lisi的加密后密码 : " + lisiPassword.toString());
    }

    @Test
    public void jpaTest() {
        System.out.println(userRepository);
        User user = userRepository.findByUsername("ealenxie");
        System.out.println(user);
    }
    @Test
    public void getId() {
        getID a=new getID();
        System.out.println(a.returnID());
    }

}
