//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package hrbeu.courseDesign.yxd.infrastructure.utils;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class DrugSystemToken implements HostAuthenticationToken, RememberMeAuthenticationToken , AuthenticationToken {
    private String username;
    private char[] password;
    private String drugProdId;

    public DrugSystemToken(String username, char[] password) {
        this.username=username;
        this.password= password;
    }
    public DrugSystemToken(String username, char[] password,String drugProdId) {
        this.username=username;
        this.password= password;
        this.drugProdId=drugProdId;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setDrugProdId(String drugProdId){
        this.drugProdId=drugProdId;
    }

    public Object getPrincipal() {
        return this.getUsername();
    }

    public Object getCredentials() {
        return this.getPassword();
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }
}
