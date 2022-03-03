 package schoolrestservicedemo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import schoolrestservicedemo.dao.UserRepository;
import schoolrestservicedemo.entity.AppUser;
import schoolrestservicedemo.entity.UserData;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	/*When user attempt to login (/login endpoint), this filter class is enacted. 
	 * A usernamePasswordAuthenticationToken object is created and 
	 * authenticated by the AuthenticationManager via UserDetailsService. 
	 * When successful authentication, successfullAuthentication method is executed. 
	 * In this method, an access token and a refresh token are generated
	 * for the authenticated user via the JwtUtils class. 
	 * Long-term refresh token is added to a cookie with httpOnly flag. 
	 * This cookie and short-term access token are added to response header. 
	 * UserData object will be created based on role and added to response body. 
	 * When authentication is not successful,
	 * UnsuccessfulAuthentication method will be executed and response will be sent with Unauthorized status code.
	 */
	
	private UserRepository userRepo;
	
	private JwtUtils jwtUtils;

	private AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(JwtUtils jwtUtils, AuthenticationManager authenticationManager,
			 UserRepository userRepo) {
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
	}
	
	@Override
	public Authentication attemptAuthentication( 
			HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper mapper = new ObjectMapper();
		AppUser appUser = null;
		UsernamePasswordAuthenticationToken authenticationToken = null;
		
			try {
				appUser = mapper.readValue(request.getInputStream(), AppUser.class);
				authenticationToken = new 
						UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword());	
			
			} catch (StreamReadException e) {
				e.printStackTrace();
			} catch (DatabindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		
		String accessToken = jwtUtils.generateAccessToken(user.getUsername());
		String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());
			
		ObjectMapper mapper = new ObjectMapper();
		AppUser appUser = userRepo.findByUsername(user.getUsername());
		
		Cookie cookie = new Cookie(Integer.toString(appUser.getId()), refreshToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(jwtUtils.getExpirationTimeRefreshToken());
		
		if(appUser.getInstructor() != null) {
			UserData userData = new UserData();
			userData.setIsNewAccount("old");
			if(appUser.getInstructor().getFirstName() == null) {
				userData.setIsNewAccount("new");;
			}
			userData.setUserId(appUser.getId());
			userData.setType("instructor");
			userData.setFirstName(appUser.getInstructor().getFirstName());
			userData.setLastName(appUser.getInstructor().getLastName());
			userData.setTypeId(appUser.getInstructor().getId());
			response.setContentType("application/json");
			response.addCookie(cookie);
			response.addHeader("accessToken", accessToken);
			mapper.writeValue(response.getOutputStream(), userData);
		}
		if(appUser.getStudent() != null) {
			UserData userData = new UserData();
			userData.setIsNewAccount("old");
			if(appUser.getStudent().getFirstName() == null) {
				userData.setIsNewAccount("new");
			}
			userData.setUserId(appUser.getId());
			userData.setType("student");
			userData.setFirstName(appUser.getStudent().getFirstName());
			userData.setLastName(appUser.getStudent().getLastName());
			userData.setTypeId(appUser.getStudent().getId());
			response.setContentType("application/json");
			response.addCookie(cookie);
			response.addHeader("accessToken", accessToken);
			mapper.writeValue(response.getOutputStream(), userData);
		}
		if(appUser.getInstructor() == null && appUser.getStudent() == null) {
			UserData userData = new UserData();
			userData.setUserId(appUser.getId());
			userData.setFirstName("Admin");
			userData.setType("admin");
			response.setContentType("application/json");
			response.addCookie(cookie);
			response.addHeader("accessToken", accessToken);
			mapper.writeValue(response.getOutputStream(), userData);
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
	
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

}
