package ru.spring.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> {
            requests.antMatchers("/").permitAll();
            requests.antMatchers("/authorize").permitAll();
            requests.antMatchers("/auth/..").permitAll();
            requests.antMatchers("/product/all").permitAll();
            requests.antMatchers("/product").hasRole("ADMIN");
//            requests.antMatchers(HttpMethod.GET, "/product").hasRole("ADMIN");
//            requests.antMatchers(HttpMethod.POST, "/product").hasRole("ADMIN");
            requests.mvcMatchers(HttpMethod.GET, "/product/{productId}").permitAll();
        });

        http.exceptionHandling().accessDeniedPage("/access-denied");
        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll();
        http.logout()
                .logoutSuccessUrl("/product/all")
                .permitAll();
        http.httpBasic();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("$2a$10$Pez1A5gKdvkeno7yvqGMbOUwTxp26JfzCVzyG8lvjZWP4ELaYHUEW")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("$2a$10$GlqEJH742XDm.VfyivZg2.AFmqkoGG0Zv4lMDKWoUKDl3N8B1XMO2")
//                .roles("ADMIN");
//    }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}

