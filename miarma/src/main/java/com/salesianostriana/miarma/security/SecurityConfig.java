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

    @Override //define cuál va a ser el esquema de autenticación
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override //expone el esquema de autenticación como un bean para después tomarlo en el filtro
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override //sirve para definir la autorización y terminar de configurar la seguridad
    protected void configure(HttpSecurity http) throws Exception {
        http
                //deshabilita la seguridad csrf
                .csrf().disable()
                .exceptionHandling()
                //Tenemos que definir un bean para setear como
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                //Indicamos que la política de sesiones es no crear sesiones
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //Establecemos una serie de rutas y métodos http por los cuales identificamos los roles de acceso
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
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

        //El filtro se encargará de la autenticación basada en el token
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        // Para dar acceso a h2 (deshabilita los framesets)
        http.headers().frameOptions().disable();


    }


}