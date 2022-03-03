package schoolrestservicedemo.security;

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

import schoolrestservicedemo.dao.UserRepository;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
		)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*GlobalMethodSecurity is enabled to provide access by roles and used in methods in RestController.
	 * I specified that I will use UserDetailsService as I will be doing DaoAuthentication.
	 * In the HttpSecurity configuration method, I specify that csrf is disabled 
	 * I'm setting it to Stateless as I won't be using the HttpSession.
	 * Because I am using jwt tokens for security.
	 * I specify the endpoints that should not be plugged into the filters.
	 * and below I added two filters that I used.
	 * I used BcryptPasswordEncoder to encrypt user password.
	 */
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/token/refresh").permitAll()
			.antMatchers("/account/password").permitAll()
			.antMatchers("/account/logout").permitAll()
			.anyRequest().authenticated()
			.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		http.addFilter(new AuthenticationFilter(jwtUtils, authenticationManagerBean(), userRepo));
		http.addFilterBefore(new AuthenticationOncePerRequestFilter(userDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {	
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
