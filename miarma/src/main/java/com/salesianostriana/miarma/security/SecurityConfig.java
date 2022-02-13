package com.salesianostriana.miarma.security;

import com.salesianostriana.miarma.security.jwt.JwtAccesDeniedHandler;
import com.salesianostriana.miarma.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationFilter filter;
    private final JwtAccesDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()

                /*
                .antMatchers(HttpMethod.POST, "/auth/register/propietario").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register/gestor").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/propietarios").authenticated()
                .antMatchers(HttpMethod.GET, "/propietario/**").hasAnyRole("PROPIETARIO", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/propietario/**").hasAnyRole("PROPIETARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/user/me").authenticated()
                .antMatchers(HttpMethod.GET, "/vivienda/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/vivienda/**").authenticated()
                .antMatchers(HttpMethod.GET, "/inmobiliaria/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/inmobiliaria/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/vivienda/**").hasAnyRole("PROPIETARIO", "ADMIN")
                .antMatchers(HttpMethod.POST, "/vivienda/**").hasRole("PROPIETARIO")
                .antMatchers(HttpMethod.POST, "/inmobiliaria/**").hasRole("ADMIN")
                 */
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();


    }


}