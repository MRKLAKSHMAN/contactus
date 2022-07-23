package com.app.contactus.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfigurationLDAP extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll().loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/contactus")
                .failureForwardUrl("/loginprocessing")
                .and().logout().permitAll().logoutSuccessUrl("/login?logout=logoutSuccess");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=lakshmancontactus,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");
    }
}

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfigurationLDAP {
//
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .usernameParameter("username").passwordParameter("password")
//                .defaultSuccessUrl("/contactus")
//                .failureForwardUrl("/loginprocessing")
//                .and().logout().permitAll().logoutSuccessUrl("/login?logout=logoutSuccess");
//
//        return http.build();
//    }
//
//    @Bean
//    public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
//        EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
//                EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
//        contextSourceFactoryBean.setLdif("classpath*:resources/users.ldif");
//        contextSourceFactoryBean.setRoot("dc=lakshmancontactus,dc=com");
//        contextSourceFactoryBean.setPort(8389);
//        return contextSourceFactoryBean;
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
//        LdapPasswordComparisonAuthenticationManagerFactory factory = new LdapPasswordComparisonAuthenticationManagerFactory(
//                contextSource, new BCryptPasswordEncoder());
//
//        factory.setUserDnPatterns("uid={0},ou=people");
//        factory.setPasswordEncoder(new BCryptPasswordEncoder());
//        factory.setPasswordAttribute("userPassword");
//        return factory.createAuthenticationManager();
//    }
//}
