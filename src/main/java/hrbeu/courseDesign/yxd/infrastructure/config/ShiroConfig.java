package hrbeu.courseDesign.yxd.infrastructure.config;

import hrbeu.courseDesign.yxd.domain.shiro.dao.PermissionRepository;
import hrbeu.courseDesign.yxd.domain.shiro.entity.Permission;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EalenXie on 2019/3/25 15:12.
 */
@Configuration
public class ShiroConfig {

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private UserAuthRealm userAuthRealm;

    /**
     * 配置 资源访问策略 . web应用程序 shiro核心过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean factoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/enter");//登录页
//        factoryBean.setSuccessUrl("/index");//首页
        factoryBean.setUnauthorizedUrl("/unauthorized");//未授权界面
        factoryBean.setFilterChainDefinitionMap(setFilterChainDefinitionMap()); //配置 拦截过滤器链
        /*重要，设置自定义拦截器，当访问某些自定义url时，使用这个filter进行验证*/
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        //如果map里面key值为authc,表示所有名为authc的过滤条件使用这个自定义的filter
        //map里面key值为myFilter,表示所有名为myFilter的过滤条件使用这个自定义的filter，具体见下方
//        filters.put("login1", new MyFilter1());
//        filters.put("login2", new MyFilter2());
        factoryBean.setFilters(filters);
        return factoryBean;
    }

    /**
     * 配置 SecurityManager,可配置一个或多个realm
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userAuthRealm);
        return securityManager;
    }

    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置 拦截过滤器链.  map的键 : 资源地址 ;  map的值 : 所有默认Shiro过滤器实例名
     * 默认Shiro过滤器实例 参考 : {@link org.apache.shiro.web.filter.mgt.DefaultFilter}
     */
    private Map<String, String> setFilterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();
        //注册 数据库中所有的权限 及其对应url
        List<Permission> allPermission = permissionRepository.findAll();//数据库中查询所有权限
        for (Permission p : allPermission) {
            System.out.println(p.getUrl());
            filterMap.put(p.getUrl(), "perms[" + p.getName() + "]");    //拦截器中注册所有的权限
        }
//        filterMap.put("/open/api/login1", "login1");
//        filterMap.put("/open/api/login2", "login2");
        filterMap.put("/enter", "anon");
        filterMap.put("/AuthNo/**", "anon");
        filterMap.put("/AuthYes/**","authc");   //有些页面需要验证
        filterMap.put("/static/**", "anon");    //公开访问的资源
        filterMap.put("/open/api/**", "anon");  //公开接口地址
//        filterMap.put("/logout", "logout");     //配置登出页,shiro已经帮我们实现了跳转
        filterMap.put("/**", "authc");          //所有资源都需要经过验证
        return filterMap;
    }

    /**
     * 凭证匹配 : 指定 加密算法,散列次数
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");   //散列算法:这里使用MD5算法
        hashedCredentialsMatcher.setHashIterations(1024); //散列的次数，比如散列两次，相当于 md5(md5(""))
        return hashedCredentialsMatcher;
    }
}
