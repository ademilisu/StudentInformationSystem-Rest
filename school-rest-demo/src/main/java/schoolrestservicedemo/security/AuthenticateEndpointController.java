package schoolrestservicedemo.security;


import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.interfaces.DecodedJWT;
import schoolrestservicedemo.entity.AppUser;
import schoolrestservicedemo.entity.Role;
import schoolrestservicedemo.entity.UserData;
import schoolrestservicedemo.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/")
public class AuthenticateEndpointController {
	
	/*This class is the controller with endpoints
	 * that are not plugged into the AuthenticationOncePerRequestFilter.
	 */
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@RolesAllowed("ROLE_ADMIN")
	@PostMapping("/signup/students")
	public UserData signupStudents(@RequestBody AppUser user) {
		if(userDetailsService.findByUsername(user.getUsername()) == null){
			Role role = new Role();
			role.setName("ROLE_USER");
			UserData userData = userDetailsService.saveUser(user, role);
			return userData;
		}
		else return null;
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@PostMapping("/signup/instructors")
	public UserData signupInstructors(@RequestBody AppUser user) {
		if(userDetailsService.findByUsername(user.getUsername()) == null){
			Role role = new Role();
			role.setName("ROLE_INSTRUCTOR");
			UserData userData = userDetailsService.saveUser(user, role);
			return userData;
		}
		else return null;
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@PostMapping("/signup/admins")
	public UserData signupAdmins(@RequestBody AppUser user) {
		if(userDetailsService.findByUsername(user.getUsername()) == null){
			Role role = new Role();
			role.setName("ROLE_ADMIN");
			UserData userData = userDetailsService.saveUser(user, role);
			return userData;
		}
		else return null;
	}

	
	@PostMapping("/account/password")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response
			, @RequestBody AppUser user) {
		String refreshCookie = request.getHeader(HttpHeaders.SET_COOKIE);
		String password = user.getPassword();
		if(refreshCookie != null) {
			try {
				int indis = refreshCookie.indexOf(";");
			 	String refreshToken = refreshCookie.substring(0,indis);
				DecodedJWT decodedJwt = jwtUtils.decode(refreshToken);
				AppUser oldUser = userDetailsService.findByUsername(decodedJwt.getSubject());
				oldUser.setPassword(password);
				userDetailsService.updateUser(oldUser);
				String newAccessToken = jwtUtils.generateAccessToken(oldUser.getUsername());
				String newRefreshToken = jwtUtils.generateRefreshToken(oldUser.getUsername());
				Cookie cookie = new Cookie(Integer.toString(oldUser.getId()), newRefreshToken);
				cookie.setMaxAge(jwtUtils.getExpirationTimeRefreshToken());
				cookie.setHttpOnly(true);
				cookie.setSecure(true);
				response.setContentType("application/json");
				response.setHeader("accesToken", newAccessToken);
				response.addCookie(cookie);
			}
			catch(Exception exc) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
			}
		}
		else response.setStatus(HttpStatus.FORBIDDEN.value());
	}
	
	/*When the access token expires, the user must send a refresh token to get a new access token.
	 * This endpoint performs these operations and sends a new access token to the user.
	 */
	
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		String refreshCookie = request.getHeader(HttpHeaders.SET_COOKIE);
		if(refreshCookie != null) {
				 try {	
					 	int indis = refreshCookie.indexOf(";");
					 	String refreshToken = refreshCookie.substring(0,indis);
						DecodedJWT decodedJwt = jwtUtils.decode(refreshToken);
						String newAccessToken = jwtUtils.generateAccessToken(decodedJwt.getSubject());
						response.setContentType("application/json");
						response.addHeader("accessToken", newAccessToken);						
					}
					catch(Exception exc) {
						response.setStatus(HttpStatus.FORBIDDEN.value());
					}
			 
		}
		else {
			response.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@DeleteMapping("/remove/students/{studentId}")
	public void studentAccountRemove(@PathVariable int studentId) {
		AppUser appUser = userDetailsService.findByStudentId(studentId);
		userDetailsService.deleteUser(appUser.getId());
	}
	
	@RolesAllowed("ROLE_ADMIN")
	@DeleteMapping("/remove/instructors/{instructorId}")
	public void instructorAccountRemove(@PathVariable int instructorId) {
		AppUser appUser = userDetailsService.findByInstructorId(instructorId);
		userDetailsService.deleteUser(appUser.getId());
	}
	
	/*logout method has no action because removing tokens on client side is enough.*/
	@GetMapping("/account/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
	}
}
