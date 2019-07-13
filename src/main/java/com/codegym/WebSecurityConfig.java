package com.codegym;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //cau hinh xac thuc bo nho voi thong tin dang nhap va vai tro cua nguoi dung
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("12345").roles("USER")
                .and()
                .withUser("admin").password("12345").roles("ADMIN");
    }

    //cau hinh bao mat dua tren web cho tat ca cac yeu cau HTTP
    //Theo mac dinh, no se ap dung cho tat ca cac yeu cau, nhung co the bi han che bang cach su dung requestMatcher() hoac cac phuong thuc tuong tu khac
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //url '/' khon duoc bao mat va co the duoc truy cap boi bat ky ai
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                //bat ky url nao bat dau bang '/user' deu duoc bao mat va chi nhung ng dung nao co vai tro USER moi co the truy cap
                .authorizeRequests().antMatchers("/user**").hasRole("USER")
                .and()
                //bat ky url nao bat dau bang '/admin' deu duoc bao mat va chi co nhung nguoi dung nao co vai tro ADMIN moi co the truy cap
                .authorizeRequests().antMatchers("/admin**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
