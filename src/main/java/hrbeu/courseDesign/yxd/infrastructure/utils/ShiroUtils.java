package hrbeu.courseDesign.yxd.infrastructure.utils;

import hrbeu.courseDesign.yxd.domain.shiro.entity.Permission;
import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.util.List;
import java.util.Map;

public class ShiroUtils {
    public static User getUserInfoBySessionId(String sessionId){
        User result = null;
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(sessionId));
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            result = (User) coll.getPrimaryPrincipal();
        }catch (UnknownSessionException e){
            System.out.println("There is no session with id ["+sessionId+']');
        }
        return result;
    }
}
