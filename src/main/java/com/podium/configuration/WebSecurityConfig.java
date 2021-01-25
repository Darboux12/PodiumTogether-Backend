package com.podium.configuration;

import com.podium.constant.PodiumEndpoint;
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
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService jwtUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

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

                // ********* HERE **************

                // AUTHENTICATE
                .and().authorizeRequests().antMatchers(PodiumEndpoint.authenticate)
                .permitAll()

                // SIGN UP
                .and().authorizeRequests().antMatchers(PodiumEndpoint.addUser)
                .permitAll()

                // CITY
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findAllCity)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findCityByName)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.existCityByName)
                .permitAll()

                // CONTACT
                .and().authorizeRequests().antMatchers(PodiumEndpoint.addContact)
                .permitAll()

                // COUNTRY
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findAllCountry)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findCountryByName)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.existCountryByName)
                .permitAll()

                // DISCIPLINE
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findAllDiscipline)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findByDisciplineName)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.existDisciplineByName)
                .permitAll()

                // GENDER
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findAllGender)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findGenderByName)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.existGenderByName)
                .permitAll()

                // INFORMATION
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findServerAddress)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findServerEndpoints)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findServerEndpointsCompatibility)
                .permitAll()

                // NEWS
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findAllNews)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findNewsById)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findNewsByTitle)
                .permitAll()

                // SUBJECT
                .and().authorizeRequests().antMatchers(PodiumEndpoint.findAllSubject)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.findSubjectByName)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.existSubjectByName)
                .permitAll()

                // USER
                .and().authorizeRequests().antMatchers(PodiumEndpoint.existUserByUsername)
                .permitAll()

                .and().authorizeRequests().antMatchers(PodiumEndpoint.existUserByEmail)
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
