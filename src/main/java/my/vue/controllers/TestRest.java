package my.vue.controllers;

import java.security.Principal;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import my.vue.model.AddUserModel;
import my.vue.model.ClientDesc;
import my.vue.service.DaoJDBC;
import my.vue.service.RandomWord;
import my.vue.model.RestLoginPassw;
import my.vue.service.SecureService;
//import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestRest {

    @Inject
    private RandomWord randomWord;
    
    @Inject
    private DaoJDBC daoJDBC;
    
    @Inject 
    private SecureService secureService;

    @RequestMapping("/static")
    //@ResponseBody
    public String getIndex() {
        return "index.html";
    }

    @RequestMapping("/admin")
    //@ResponseBody
    public String getAdmin() {
        return "redirect:/static/index.html";
    }
    
    @RequestMapping("/logout")
    public String ishod(HttpServletRequest request) {
        //secureService.killHttpSession(request);
        return "logout.html";
    }    

    // see WebMvcCfg = set welcome page
    @RequestMapping("/")
    //@ResponseBody
    public String index() {
        //return "index.html";
        return "redirect:/static/index.html";
    }

    @RequestMapping(
            value = "/rest/user/json",
            method = RequestMethod.GET,
            //headers="Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@PreAuthorize("hasRole('rest')") // разница только в исп EL
    //@Secured({ "rest" })
    //@RolesAllowed({"rest"}) // jsr-250
    public String getUserJSON(
            @RequestParam(value = "alfavit", required = true) String alfavit,
            @RequestParam(value = "specuse", required = true) Boolean specuse,
            @RequestParam(value = "numchar", required = true) int charnum
    ) {
        return randomWord.generate(specuse, alfavit, charnum);
    }

    @RequestMapping(
            value = "/rest/loginpassw",
            method = RequestMethod.GET,
            //headers="Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RestLoginPassw getRestLoginPassw() {
        RestLoginPassw rs=new RestLoginPassw();
        String buf=randomWord.generate(false, "aA-zZ_0-9", 8);
        rs.setLogin(buf);
        rs.setPassw(buf);
        daoJDBC.usersInsertRow(buf,buf,"rest",true);
        return rs;
    } 
    
    @RequestMapping(
            value = "/rest/clientdesc",
            method = RequestMethod.GET,
            //headers="Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientDesc getClientDesc(
            Authentication authentication,
            HttpServletRequest request
    ) {
        ClientDesc cd=new ClientDesc();
        cd.setDns(secureService.getRemoteName(request));
        cd.setIp(secureService.getRemoteIP(request));
        cd.setPort(secureService.getRemotePort(request));
        cd.setLogin(secureService.getCurrentUserNameFromReq(request));
        cd.setRole(secureService.getRolesOfCurrentUserFromAuth(authentication));
        cd.setLocale(secureService.getCurrentLocale(request));
        //cd.setCharenc(secureService.getCharEncode(request));
        return cd;
    } 
    
    @RequestMapping(
            value = "/rest/currentrole",
            method = RequestMethod.GET,
            //headers="Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection getCurrentRole(Authentication authentication) {
        return secureService.getRolesOfCurrentUserFromAuth(authentication);
    } 
    
    @RequestMapping(
            value = "/rest/currentuser",
            method = RequestMethod.GET,
            //headers="Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getCurrentUser(HttpServletRequest request) {
        return secureService.getCurrentUserNameFromReq(request);
    }    

    // for simple post-form without ajax !
    @RequestMapping(
            value = "/rest/useradd"
            ,method = RequestMethod.POST
    )
    public String userAdd(
            @RequestParam(value = "login",  required = true) String login,
            @RequestParam(value = "passw1", required = true) String passw1,
            @RequestParam(value = "passw2", required = true) String passw2
            //,@RequestParam(value = "adm",    required = false) String adm
    ) {
        String rol="guest";
       // if (adm!=null && adm.equals("on")) rol="admin";
        daoJDBC.usersInsertRow(login, passw1, rol, true);
        return "redirect:/static/index.html";
    }    
    
    // for ajax axios-post
    //@PreAuthorize("hasRole('rest')") // разница только в исп EL
    //@Secured({ "admin" })
    //@RolesAllowed({"guest"}) // jsr-250
    @RequestMapping(
            value = "/admin/adduser"
            ,method = RequestMethod.POST
    )
    public String addUser(@RequestBody AddUserModel addUserModel) {
        String rol="ROLE_guest";
       if (addUserModel.getAdm()==true) rol="ROLE_admin";
       String login=addUserModel.getLogin();
       String passw1=addUserModel.getPassw1();
        daoJDBC.usersInsertRow(login, passw1, rol, true);
        return "redirect:/static/index.html";
    }       
    
}
