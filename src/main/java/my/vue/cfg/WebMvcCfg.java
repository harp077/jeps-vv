package my.vue.cfg;

import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
//import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "my.vue")
@PropertySource(value = {"classpath:jeps.properties"})
public class WebMvcCfg extends WebMvcConfigurerAdapter {

    @Value("${db.driver}")
    private String dbDriver;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;

    @PostConstruct
    public void afterBirn() {
        System.out.println("DB URL ========== " + dbUrl);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        //BasicDataSource dsNodes = new BasicDataSource();
        HikariDataSource dsNodes = new HikariDataSource();
        dsNodes.setDriverClassName(dbDriver);
        //dsNodes.setUrl(dbUrl);
        dsNodes.setJdbcUrl(dbUrl);
        dsNodes.setPassword(dbPassword);
        dsNodes.setUsername(dbUsername);
        /*try {
            con = dsNodes.getConnection(dbUsername, dbPassword);
        } catch (SQLException ex) {
            //Logger.getLogger(AppContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        executeUpdate("CREATE TABLE hosts(id int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), title varchar(255), login varchar(255), passw varchar(255), descr varchar(255), url varchar(255))");
         */
        return dsNodes;
    }

    // set welcome page
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/vhod").setViewName("redirect:/static/auth.html");
        //registry.addViewController("/welcome").setViewName("redirect:/static/index.html");
        //registry.addViewController("/logout").setViewName("redirect:/static/logout.html");
    }

    /*@Bean
    public ViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix("/WEB-INF/jsp/");
        viewResolve.setSuffix(".jsp");
        return viewResolve;
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:/opt/files/");
        registry.addResourceHandler("*.html").addResourceLocations("/WEB-INF/static/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/static/img/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/static/fonts/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
