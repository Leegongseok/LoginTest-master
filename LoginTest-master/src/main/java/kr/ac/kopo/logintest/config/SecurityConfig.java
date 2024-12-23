package kr.ac.kopo.logintest.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();//패스워드 암호화하는 클래스
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        String userName = "user2";
//        String password = "1234";
//        UserDetails user = User.builder()
//                .username(userName)
//                .password(passwordEncoder().encode(password))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests((auth) -> {
//            /sample/all 은 아무나 다 접근할 수 있도록
            auth.requestMatchers("/sample/all").permitAll();
            auth.requestMatchers("/sample/member").hasRole("USER");
        });

        httpSecurity.formLogin();
        httpSecurity.csrf().disable();
        httpSecurity.logout();

        return httpSecurity.build();
    }
}
