package org.bamappli.telfonibackendspring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuriteApplication{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;
    public ConfigurationSecuriteApplication(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .cors(Customizer.withDefaults())
                        .authorizeHttpRequests(
                                authorize ->
                                        authorize
                                                .requestMatchers(POST,"/inscription").permitAll()
                                                .requestMatchers(POST,"/connexion").permitAll()
                                                .requestMatchers(POST,"/verify").permitAll()
                                                .requestMatchers(GET,"/api/images/**").permitAll()
                                                .requestMatchers(POST,"/update-token").permitAll()

                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                .requestMatchers("/boutique/**").hasRole("BOUTIQUE")
                                                .requestMatchers("/client/**").hasRole("CLIENT")

                                                .requestMatchers(GET, "/controller/**").permitAll()
                                                .requestMatchers(POST, "/controller").hasAnyRole("ADMIN", "CONTROLLER")
                                                .requestMatchers(PATCH, "/controller").hasAnyRole("ADMIN", "CONTROLLER")

                                                .requestMatchers(GET, "/reparateur/**").permitAll()
                                                .requestMatchers(POST, "/reparateur").hasAnyRole("ADMIN", "REPARATEUR")
                                                .requestMatchers(PATCH, "/reparateur").hasAnyRole("ADMIN", "REPARATEUR")

                                                .requestMatchers(GET, "/annonce/**").permitAll()
                                                .requestMatchers(POST, "/annonce").hasAnyRole("CLIENT", "BOUTIQUE")
                                                .requestMatchers(PATCH, "/annonce").hasAnyRole("CLIENT", "BOUTIQUE", "ADMIN")
                                                .requestMatchers(DELETE, "/annonce").hasAnyRole("ADMIN", "CONTROLLER", "CLIENT", "BOUTIQUE")

                                                .requestMatchers(GET, "/brand/**").permitAll()
                                                .requestMatchers(POST, "/brand").hasRole("ADMIN")
                                                .requestMatchers(PATCH, "/brand").hasRole("ADMIN")
                                                .requestMatchers(DELETE, "/brand").hasRole("ADMIN")

                                                .requestMatchers(GET, "/commande/**").hasAnyRole("CONTROLLER", "CLIENT")
                                                .requestMatchers(POST, "/commande").hasRole("CONTROLLER")
                                                .requestMatchers(PATCH, "/commande").hasAnyRole("CONTROLLER", "ADMIN")
                                                .requestMatchers(DELETE, "/commande").hasAnyRole("CONTROLLER", "ADMIN")

                                                .requestMatchers(GET, "/discussion/**").permitAll()
                                                .requestMatchers(POST, "/discussion").hasRole("CLIENT")

                                                .requestMatchers(GET, "/historiquewallet").permitAll()

                                                .requestMatchers(GET, "/litige/**").hasAnyRole("ADMIN", "CLIENT")
                                                .requestMatchers(POST, "/litige").hasRole("CLIENT")
                                                .requestMatchers(PATCH, "/litige").hasAnyRole("ADMIN")
                                                .requestMatchers(DELETE, "/litige").hasAnyRole("CLIENT", "ADMIN")

                                                .requestMatchers(GET, "/message/**").permitAll()
                                                .requestMatchers(POST, "/message").permitAll()
                                                .requestMatchers(PATCH, "/message").permitAll()
                                                .requestMatchers(DELETE, "/message").permitAll()

                                                .requestMatchers(GET, "/modele/**").permitAll()
                                                .requestMatchers(POST, "/modele").hasRole("ADMIN")
                                                .requestMatchers(PATCH, "/modele").hasRole("ADMIN")
                                                .requestMatchers(DELETE, "/modele").hasRole("ADMIN")

                                                .requestMatchers(GET, "/panier").hasRole("CLIENT")

                                                .requestMatchers(GET, "/photos/download/**").permitAll()
                                                .requestMatchers(POST, "/photos/upload").hasAnyRole("CLIENT")
                                                .requestMatchers(DELETE, "/photos/delete/**").hasAnyRole("CLIENT", "CONTROLLER", "ADMIN")

                                                .requestMatchers(GET, "/promotion/**").permitAll()
                                                .requestMatchers(POST, "/promotion").hasRole("BOUTIQUE")
                                                .requestMatchers(PATCH, "/promotion").hasRole("BOUTIQUE")
                                                .requestMatchers(DELETE, "/promotion").hasRole("BOUTIQUE")

                                                .requestMatchers(GET, "/reparateur/**").permitAll()
                                                .requestMatchers(POST, "/reparateur").hasAnyRole("REPARATEUR", "ADMIN")
                                                .requestMatchers(PATCH, "/reparateur").hasAnyRole("REPARATEUR", "ADMIN")
                                                .requestMatchers(DELETE, "/reparateur").hasAnyRole("REPARATEUR", "ADMIN")

                                                .requestMatchers(GET, "/reparation/**").permitAll()
                                                .requestMatchers(POST, "/reparation").hasAnyRole("REPARATEUR", "ADMIN", "CLIENT")
                                                .requestMatchers(PATCH, "/reparation").hasAnyRole("REPARATEUR", "ADMIN")
                                                .requestMatchers(DELETE, "/reparation").hasAnyRole("REPARATEUR", "ADMIN")

                                                .requestMatchers(GET, "/stock/**").permitAll()
                                                .requestMatchers(POST, "/stock").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(PATCH, "/stock").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(DELETE, "/stock").hasAnyRole("BOUTIQUE", "CLIENT")

                                                .requestMatchers(GET, "/tags/**").permitAll()
                                                .requestMatchers(POST, "/tags").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(PATCH, "/tags").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(DELETE, "/tags").hasAnyRole("BOUTIQUE", "CLIENT")

                                                .requestMatchers(GET, "/telephone/**").permitAll()
                                                .requestMatchers(POST, "/telephone").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(PATCH, "/telephone").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(DELETE, "/telephone").hasAnyRole("BOUTIQUE", "CLIENT")

                                                .requestMatchers(GET, "/transaction/**").permitAll()
                                                .requestMatchers(POST, "/transaction").hasAnyRole("BOUTIQUE", "CLIENT")
                                                .requestMatchers(PATCH, "/transaction/**").hasAnyRole("ADMIN", "CLIENT")

                                                .requestMatchers(GET, "/wallet/**").permitAll()
                                                .requestMatchers(PATCH, "/wallet").hasRole("ADMIN")
                                                .requestMatchers(GET, "/user/current").hasAnyRole("ADMIN", "BOUTIQUE", "CLIENT", "REPARATEUR", "CONTROLLER")

                                                .anyRequest().authenticated()
                        )
                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                                )
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

}
