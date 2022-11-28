
package my.vue.model;

import java.util.Collection;
import java.util.Locale;


public class ClientDesc {
    
    private String login;
    private String ip;
    private String dns;
    private int port;
    private Collection role;
    private Locale locale;
    //private String charenc;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Collection getRole() {
        return role;
    }

    public void setRole(Collection role) {
        this.role = role;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
   
}
