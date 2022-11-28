package my.vue.cfg;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


public class AppInit //extends AbstractSecurityWebApplicationInitializer  
        implements WebApplicationInitializer {
    
    private AnnotationConfigWebApplicationContext ctx;
    private ServletRegistration.Dynamic rest;
    
    /*public SecurityWebApplicationInitializer() {
        super(SecurCfg.class);
    } */   

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
       
        ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebMvcCfg.class);
        ctx.register(SecureCfg.class);
        servletContext.addListener(new ContextLoaderListener(ctx));
        ctx.setServletContext(servletContext);
        rest = servletContext.addServlet("rest", new DispatcherServlet(ctx));
        rest.setLoadOnStartup(1);
        //rest.setAsyncSupported(true);
        rest.addMapping("/");

    }
    
  
    
}
