package my.vue.cfg;

import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@DependsOn("webMvcCfg")
public class SecureCfg extends WebSecurityConfigurerAdapter {

    @Inject
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password("password").roles("guest");
        //auth.inMemoryAuthentication().withUser("admin").password("admin").roles("admin");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,confirm from users where username=?")
                .authoritiesByUsernameQuery("select username,level from users where username=?");
        //.withDefaultSchema();
        //.passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // from jeps:
        http    
                //.httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/static/js/**").authenticated()//.hasIpAddress("127.0.0.1")
                //.antMatchers("/rest/**").authenticated()//.hasIpAddress("127.0.0.1")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/static/auth.html").permitAll()
                //.loginPage("/vhod").permitAll()
                .defaultSuccessUrl("/static/index.html")
                //.loginPage("/auth").permitAll()
                //.failureForwardUrl("/static/error.html")
                // !!! .successForwardUrl = HTTP Status 405 - Request method 'POST' not supported !!!!
                // !!! iz-za 'Forward' !!!
                //.successForwardUrl("/static/index.html")
                //.and().logout().logoutUrl("/static/logout.html")
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .and().logout().invalidateHttpSession(true).logoutUrl("/logout")
                .and()
                .httpBasic();        
        //
        /*http    
                //.httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                    //.antMatchers("/admin/**").authenticated()
                .antMatchers("/**").hasAnyRole("guest","admin")
                    //.antMatchers("/**").authenticated()                  
                    //.antMatchers("/static/js/**").authenticated()//.hasIpAddress("127.0.0.1")
                    //.antMatchers("/rest/**").authenticated()//.hasIpAddress("127.0.0.1")
                //.anyRequest().authenticated() 
                .and()
                .formLogin()
                .loginPage("/static/auth.html").permitAll()
                //.loginPage("/vhod").permitAll()
                .defaultSuccessUrl("/static/index.html")
                //.loginPage("/auth").permitAll()
                //.failureForwardUrl("/static/error.html")
                // !!! .successForwardUrl = HTTP Status 405 - Request method 'POST' not supported !!!!
                // !!! iz-za 'Forward' !!!
                //.successForwardUrl("/static/index.html")
                //.and().logout().logoutUrl("/static/logout.html")
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .and().logout().invalidateHttpSession(true).logoutUrl("/logout")
                .and()
                .httpBasic();*/
        //.and().antMatcher("/static/register**").anonymous();

        /*http
                .antMatcher("/api/**")
                // This is just an example – not required in our case
                .headers().cacheControl().disable()
                .httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).hasRole("USER")
                .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .anyRequest().authenticated();*/

        /*http.authorizeRequests()
                //.antMatchers("/rest/**").hasRole("rest")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/**").authenticated()
                .and().httpBasic();*/
        //formLogin().defaultSuccessUrl("/", false);

        /*http.csrf().disable().authorizeRequests()
                .antMatchers("/rest/**").hasRole("rest")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/**").authenticated()
                //.access("hasRole('guest')")
                .and().formLogin().defaultSuccessUrl("/", false).and().logout();*/
        //.and()
        //.antMatcher("/rest/**").httpBasic();
        //.and().httpBasic();
        //http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        //http.antMatcher("/rest/**").httpBasic().authenticationEntryPoint(authenticationEntryPoint);
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        // Spring Security web filters
        web.ignoring().antMatchers("/resources/**");
    }*/
    ///////////////////////
    /*@Configuration
    @Order(1)
    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            /*http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/rest/**").hasRole("rest")
                    .antMatchers("/rest/**").authenticated()
                    .and()
                    .httpBasic();
            http.antMatcher("/rest/**").authorizeRequests().anyRequest().authenticated().and().httpBasic();
            /*http.authorizeRequests()
                    .antMatchers("/rest/**").hasRole("rest")
                    .anyRequest().authenticated()
                    .and().httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable() //HTTP with Disable CSRF
                    .authorizeRequests() //Authorize Request Configuration
                    .antMatchers("/admin/**").hasRole("admin")
                    .antMatchers("/admin/**").authenticated()
                    .antMatchers("/static/**").hasAnyRole("user","admin")
                    .antMatchers("/static/**").authenticated()                    
                    .and() //Login Form configuration for all others
                    .formLogin().defaultSuccessUrl("/", false);
            //.loginPage("/login").permitAll()
            //.and() //Logout Form configuration
            //.logout().permitAll();
            //http.authorizeRequests().anyRequest().authenticated().and().formLogin();
        }
    }*/
}
