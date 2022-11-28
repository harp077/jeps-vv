
package my.vue.service;

import java.security.Principal;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecureService {
    
    public String getCurrentUserNameFromPrincipal(Principal principal) {
        return principal.getName();
    }

    public String getCurrentUserNameFromAuth(Authentication authentication) {
        return authentication.getName();
    }
    
    public Object getAuthDetail(Authentication authentication) {
        return authentication.getDetails();
    }    
    
    public String getCurrentUserNameFromReq(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    } 
    
    public Collection getRolesOfCurrentUserFromAuth(Authentication authentication) {
        return authentication.getAuthorities();
    } 
    
    //////////// info from request
    
    public String getRemoteIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }  
    
    public String getRemoteName(HttpServletRequest request) {
        return request.getRemoteHost();
    }  
    
    public int getRemotePort(HttpServletRequest request) {
        return request.getRemotePort();
    }

    public Cookie[] getCookie(HttpServletRequest request) {
        return request.getCookies();
    }  
    
    public HttpSession getHttpSession(HttpServletRequest request) {
        return request.getSession();
    }

    public void killHttpSession(HttpServletRequest request) {
        request.getSession().invalidate();
        //request.logout();
    } 

    public Locale getCurrentLocale(HttpServletRequest request) {
        return request.getLocale();
    } 

    public String getCharEncode(HttpServletRequest request) {
        return request.getCharacterEncoding();
    }     
    
}
