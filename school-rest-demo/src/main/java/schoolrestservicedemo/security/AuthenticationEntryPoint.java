package schoolrestservicedemo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
	
	/*This is an authentication entry point customization class. A controller advice.
	 * When the user is not authenticated, 
	 * the server sends a response stating that user needs to authenticate
	 * By default it sends 403 status code, but here I specify that when token expired, 
	 * it sends 401 status code
	 * When Access is denied due to lack of authorization, it sends 403 status code.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = {AccessDeniedException.class})
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accsException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
	}

}
