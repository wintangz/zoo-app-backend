package net.wintang.zooapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static net.wintang.zooapp.util.ApplicationConstants.SecurityConstants.Roles;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint((jwtAuthEntryPoint)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("api/auth/**").permitAll()
                        .requestMatchers("api/users/password-reset/**").permitAll()
                        .requestMatchers("api/animals/**").hasAnyAuthority(Roles.ADMIN,
                                Roles.STAFF, Roles.ZOO_TRAINER)
                        .requestMatchers("api/users").hasAuthority(Roles.ADMIN)
                        .requestMatchers(HttpMethod.PUT, "api/users/**").permitAll()
                        .requestMatchers("api/users/staff/**").hasAuthority(Roles.ADMIN)
                        .requestMatchers(HttpMethod.POST, "api/users/zoo-trainers").hasAnyAuthority(Roles.STAFF, Roles.ADMIN)
                        .requestMatchers(HttpMethod.GET, "api/users/zoo-trainers").hasAuthority(Roles.STAFF)
                        .requestMatchers("api/users/zoo-trainers/**").hasAuthority(Roles.STAFF)
                        .requestMatchers(HttpMethod.POST, "api/users/customers").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/users/customers").hasAuthority(Roles.STAFF)
                        .requestMatchers("api/users/customers/**").hasAuthority(Roles.STAFF)
                        .requestMatchers(HttpMethod.GET, "api/species").permitAll()
                        .requestMatchers("api/species/**").hasAuthority(Roles.ZOO_TRAINER)
                        .requestMatchers(HttpMethod.GET, "api/foods").hasAnyAuthority(Roles.ZOO_TRAINER, Roles.STAFF)
                        .requestMatchers("api/foods/**").hasAuthority(Roles.ZOO_TRAINER)
                        .requestMatchers(HttpMethod.GET, "api/health_records").hasAnyAuthority(Roles.ZOO_TRAINER, Roles.STAFF)
                        .requestMatchers("api/health_records/**").hasAuthority(Roles.ZOO_TRAINER)
                        .requestMatchers(HttpMethod.GET, "api/feeding_schedules").hasAnyAuthority(Roles.ZOO_TRAINER, Roles.STAFF)
                        .requestMatchers("api/feeding_schedules/**").hasAuthority(Roles.ZOO_TRAINER)
                        .requestMatchers(HttpMethod.GET, "api/orders").hasAnyAuthority(Roles.STAFF, Roles.ADMIN)
                        .requestMatchers(HttpMethod.GET, "api/orders/tickets").hasAnyAuthority(Roles.STAFF, Roles.ADMIN)
                        .requestMatchers(HttpMethod.GET, "api/enclosures").hasAnyAuthority(Roles.STAFF, Roles.ZOO_TRAINER)
                        .requestMatchers("api/enclosures/**").hasAuthority(Roles.STAFF)
                        .requestMatchers(HttpMethod.GET, "api/habitats").permitAll()
                        .requestMatchers("api/habitats/**").hasAuthority(Roles.STAFF)
                        .anyRequest().permitAll())
                .httpBasic(withDefaults());
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Replace with your React app's origin
        configuration.addAllowedMethod("*"); // You can customize allowed methods
        configuration.addAllowedHeader("*"); // You can customize allowed headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }
}
