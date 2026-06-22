package br.cesarschool.poo.projeto.config;

import br.cesarschool.poo.projeto.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/recuperar-senha", "/recuperar-senha/**", "/css/**", "/images/**", "/h2-console/**").permitAll()
                // Somente ADMIN pode gerenciar clientes, técnicos e chamados
                .requestMatchers("/clientes/**", "/tecnicos/**").hasRole("ADMIN")
                .requestMatchers("/chamados/novo", "/chamados/editar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/chamados/salvar", "/chamados/excluir/**").hasRole("ADMIN")
                // Somente TECNICO pode finalizar e adicionar anexos (validação de posse feita no controller)
                .requestMatchers(HttpMethod.POST, "/chamados/finalizar/**", "/chamados/iniciar/**", "/chamados/*/anexo").hasRole("TECNICO")
                // Agenda: criação/edição/exclusão/cancelamento somente ADMIN
                .requestMatchers("/agenda/novo", "/agenda/editar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/agenda/salvar", "/agenda/excluir/**", "/agenda/cancelar/**").hasRole("ADMIN")
                // Dashboard, listagem e detalhe de chamados: qualquer usuário autenticado
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
