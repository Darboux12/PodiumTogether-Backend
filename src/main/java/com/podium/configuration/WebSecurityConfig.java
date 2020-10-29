package com.podium.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer
{
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("HEAD", "find", "PUT", "POST",
                        "DELETE", "PATCH").allowedHeaders("*");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .deny()
                .and()
                // dont authenticate this particular requests
                .authorizeRequests().antMatchers("/authenticate")
                .permitAll()

                .and().authorizeRequests().antMatchers("/news/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/{username}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/find/role")
                .permitAll()

                .and().authorizeRequests().antMatchers("/test")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/find/{username}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/contact/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/contact/delete/{id}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/subject/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/subject/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/news/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/{username}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/contact/find")
                .permitAll()

                .and().authorizeRequests().antMatchers("/subject/find/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/country/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/subject/delete/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/exist/username/{username}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/exist/email/{email}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/discipline/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/discipline/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/discipline/exist/{discipline}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/news/find/id/{id}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/event/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/image/upload/news")
                .permitAll()

                .and().authorizeRequests().antMatchers("/news/find/title/{title}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/news/delete/{id}")
                .permitAll()


                .and().authorizeRequests().antMatchers("/discipline/delete/{discipline}")
                .permitAll()


                .and().authorizeRequests().antMatchers("/country/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/country/exist/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/country/delete/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/gender/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/gender/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/gender/exist/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/gender/delete/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/gender/find/name/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/event/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/city/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/city/find/name/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/city/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/city/exist/{name}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/city/delete/{name}")
                .permitAll()


                .and().authorizeRequests().antMatchers("/event/add")
                .permitAll()

                .and().authorizeRequests().antMatchers("/user/delete/{username}")
                .permitAll()


                             .and().authorizeRequests().antMatchers("/event/delete/{title}")
                .permitAll()


                .and().authorizeRequests().antMatchers("/contact/find/all")
                .permitAll()

                .and().authorizeRequests().antMatchers("/contact/find/subject/{subject}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/contact/find/email/{email}")
                .permitAll()

                .and().authorizeRequests().antMatchers("/subject/exist/{name}")
                .permitAll()


                .and().authorizeRequests().antMatchers("/discipline/find/{discipline}")
                .permitAll()


                .and().authorizeRequests().antMatchers("/authenticate/check")
                .permitAll()



                .and().authorizeRequests().antMatchers("/user/update/profile")
                .permitAll()

                .and().authorizeRequests().antMatchers("/token/find/username")
                .permitAll()


                .and().authorizeRequests().antMatchers("/token/find/expiration")
                .permitAll()

























                // all other requests need to be authenticated
                        .anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }





}