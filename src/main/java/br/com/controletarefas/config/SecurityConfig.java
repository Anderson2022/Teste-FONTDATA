package br.com.controletarefas.config;

import br.com.controletarefas.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository repository) {
        return username -> repository.findByUsername(username)
                .map(usuario -> User.withUsername(usuario.getUsername())
                        .password(usuario.getSenha())
                        .roles(usuario.getPerfil().name())
                        .disabled(!usuario.isAtivo())
                        .build())
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("Usuário não encontrado"));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/error").permitAll()
                        .requestMatchers("/dashboard", "/usuarios/**", "/tarefas/**", "/atribuicoes/**").hasRole("SUPERVISOR")
                        .requestMatchers("/minhas-tarefas").hasAnyRole("SUPERVISOR", "OPERADOR")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").successHandler((request, response, authentication) -> {
                    boolean supervisor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"));
                    response.sendRedirect(supervisor ? "/dashboard" : "/minhas-tarefas");
                }).failureUrl("/login?erro").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/acesso-negado"))
                .build();
    }
}
