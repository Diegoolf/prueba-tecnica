package com.SistemaAlmacen.prueba.tecnica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import javax.sql.DataSource;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private HandlerMappingIntrospector introspector;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(mvcMatcherBuilder.pattern("/css/**"), mvcMatcherBuilder.pattern("/js/**"), mvcMatcherBuilder.pattern("/registro/**"), mvcMatcherBuilder.pattern("/login/**")).permitAll()
                        // Módulo de Inventario (Visible para ambos roles)
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/home"), mvcMatcherBuilder.pattern("/almacen/home")).hasAnyAuthority("ROLE_1", "ROLE_2")
                         // Solo Administrador puede agregar, aumentar, dar de baja productos
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/productos/**")).hasAuthority("ROLE_1")
                        // Módulo de Salida de productos (Solo Almacenista)
                        .requestMatchers(mvcMatcherBuilder.pattern("/almacen/salidas/**")).hasAuthority("ROLE_2")
                        // Módulo histórico (Solo Administrador)
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/historico/**")).hasAuthority("ROLE_1")
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/redireccionar-por-rol",true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                ).
                logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()

                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery(
            "SELECT correo as username, contrasena as password, estatus as enabled " +
            "FROM usuarios WHERE correo = ?");

        manager.setAuthoritiesByUsernameQuery(
            "SELECT u.correo as username, CONCAT('ROLE_', r.idRol) as authority " +
            "FROM usuarios u INNER JOIN roles r ON u.idRol = r.idRol WHERE u.correo = ?");

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
