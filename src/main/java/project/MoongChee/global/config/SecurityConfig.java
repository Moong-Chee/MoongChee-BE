package project.MoongChee.global.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.MoongChee.global.auth.authentication.CustomAuthenticationEntryPoint;
import project.MoongChee.global.auth.jwt.JwtFilter;
import project.MoongChee.global.auth.jwt.JwtProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                )
                // jwt 토큰을 사용하기 때문에 세션을 STATELESS로 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html",
                                                "/swagger-ui/**", "/swagger/**").permitAll()
                                        .requestMatchers("/api/v1/users/login").permitAll()
                                        .requestMatchers("/api/v1/users/oauth/google/callback").permitAll()
                                        .requestMatchers("http://43.203.202.100.nip.io").permitAll()
                                        .requestMatchers("/api/v1/admin/**").permitAll()
                                        .requestMatchers("/ws", "/ws/**").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .anyRequest().authenticated()

                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptioHandling ->
                        exceptioHandling
                                .authenticationEntryPoint(customAuthenticationEntryPoint))

                .build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider);  // JwtFilter를 Bean으로 등록하고 JwtProvider를 주입
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
    CORS 관련 설정
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("http://localhost:5174");
        configuration.addAllowedOriginPattern("https://moong.vercel.app/");
        configuration.addAllowedOrigin("http://127.0.0.1:5500");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Authorization_refresh");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
