package my.vue.controllers;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class LoginController {

    @RequestMapping(value = "/vhod", method = { RequestMethod.GET, RequestMethod.POST })//, produces = MediaType.APPLICATION_JSON_VALUE)
    public String showLogin() {
        return "redirect:/static/auth.html";
        //return "auth";
    }
    
    @RequestMapping(value = "/welcome", method = { RequestMethod.GET, RequestMethod.POST })//, produces = MediaType.APPLICATION_JSON_VALUE)
    public String welcome() {
        return "redirect:/static/index.html";
        //return "auth";
    }    
    
    /*@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String getAuth(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout
    ) {
        if (error != null) {
            return "/static/error.html";
        }
        if (logout != null) {
            return "/static/logout.html";
        }
        return "redirect:/static/index.html";
    }*/

}
