package study.securitysession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터 체인으로 등록해줍니다.
public class SecurityConfig {

    /**
     *   .cors(cors -> cors.disable())  // 다른 출처간 자원을 공유하지 않겠다는 의미
     *   .csrf(withDefaults())          // csrf 공격으로 부터 보호하는 설정을 하겠다는 의미
     *   .authorizeHttpRequests(authorize -> authorize  // 요청에 대한 권한 관련 설정을 하겠다는 의미
     *      .mvcMatchers("/admin/**").hasRole("ADMIN")  // `/admin/**` URL 요청은 ADMIN 권한을 가진 사용자만 접근 할 수있다는 의미
     *      .mvcMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")    // `/user/**` URL 요청은 USER, ADMIN 권한을 가진 사용자만 접근할 수 있다는 의미
     *      .anyRequest().permitAll()  // 위에 URL 요청을 제외한 모든 요청은 접근을 허용하겠다는 의미
     *   )
     *   .formLogin(form -> form            // form로그인 방식을 사용하겠다는 의미
     *      .loginPage("/login")            // 로그인 페이지를 시큐리티가 생성한 페이지가 이닌 직접 만든 페이지에서 처리하겠다는 의미
     *      .loginProcessingUrl("/login")   // `POST /login` 요청을 시큐리티가 낚아채서 처리하겠다는 의미
     *      .defaultSuccessUrl("/")         // 로그인을 성공하면 `/`로 리다이렉트하겠다는의미. 만약 `/user`에 접근했는데 권한이 없으면 로그인 페이지로 오게되고, 로그인을 성공하면 이전에 접근하려고 했던 `/user` 페이지로 이동하게 해준다.
     *   )
     *   .logout(logout -> logout           // 로그아웃 처리를 설정하겠다는 의미
     *      .logoutUrl("/logout")           // 로그아웃 URL을 설정함. csrf설정이 적용된 경우. `/logout`을 POST 요청을 보내야함.
     *      .logoutSuccessUrl("/login")     // 로그아웃 성공시 이동할 URL을 의미
     *      .invalidateHttpSession(true)    // 세션을 만료하겠다는 의미
     *      .deleteCookies("JSESSIONID")    // JSESSIONID 라는 이름을 가진 쿠키를 삭제하겠다는 의미
     *   )
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .mvcMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
        ;
        return http.build();
    }

}
