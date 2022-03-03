package schoolrestservicedemo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthenticationOncePerRequestFilter extends OncePerRequestFilter {
	
	/*This filter class is executed each time the user submits a request for a resource.
	 *  Just excluding /login,/account/password,/account/logout,/token/refresh endpoints.
	 *  When user wants to access a resource, 
	 *  he should add the access token with the 'Bearer' prefix to the 'Authorization' header 
	 *  in the request.
	 *  The OncePerRequestFilter class has a method called doFilterInternal.
	 *  In this method, the token with the prefix 'Bearer' in the 'Authorization'
	 *  header of the request is read. 
	 *  This token is decrypted and user specific information is extracted.
	 *  Authentication is done through the SecurityContextHolder.
	 *  If the authentication process is successful, the user is granted access to the resource.
	 */
	
	private UserDetailsService userDetailsService;
	private JwtUtils jwtUtils;
	

	public AuthenticationOncePerRequestFilter(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		if(request.getServletPath().equals("/login") || 
				request.getServletPath().equals("/account/password") || 
				request.getServletPath().equals("/token/refresh") ||
				request.getServletPath().equals("/account/logout")){

			filterChain.doFilter(request, response);
			
		}
		else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				if(jwtUtils.verify(token, response)) {
					DecodedJWT decodedJwt = jwtUtils.decode(token);
					String username = decodedJwt.getSubject();
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authenticationToken = new 
										UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);				
					filterChain.doFilter(request, response);
				}
									
			}
			else {
				response.setStatus(HttpStatus.FORBIDDEN.value());
			}
		}
	}
}
