package learningSpring.config;

import learningSpring.service.DBUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Log4j2
@EnableMethodSecurity(prePostEnabled = true) //
@RequiredArgsConstructor
public class SecurityConfig {

    private final DBUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf token
        http.csrf(AbstractHttpConfigurer::disable);
//        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        http.authorizeHttpRequests((authz) -> authz
                    .anyRequest().authenticated()
            )
                .formLogin(withDefaults()) // agora para acessar a api pelo navegador é necessário um dos users
            .httpBasic(withDefaults());
        return http.build();

    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        log.info("Password encoded {}", encoder.encode("test"));
//        UserDetails userAdmin = User.withUsername("sofia")
//                .password(encoder.encode("test"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails userUser = User.withUsername("ned")
//                .password(encoder.encode("test"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userAdmin, userUser);
//    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }




// OS METODOS ABAIXO ESTÃO DEPRECIADOS


//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//        .httpBasic();
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        log.info("Password encoded {}", passwordEncoder.encode("test"));
//
//        auth.inMemoryAuthentication()
//            .withUser("sofia")
//                .password(passwordEncoder.encode("test"))
//                .roles("ADMIN", "USER")
//                .and()
//                .withUser("usuario")
//                .password(passwordEncoder.encode("test"))
//        .roles("USER");
//    }

}