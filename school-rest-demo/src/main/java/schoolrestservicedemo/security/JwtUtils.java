package schoolrestservicedemo.security;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {
	
	/*This class creates and validates tokens through information in the ApplicationProperties file.*/
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration.access.token}")
	private int expirationTimeAccessToken;
	
	@Value("${jwt.expiration.refresh.token}")
	private int expirationTimeRefreshToken;
	
	public String generateAccessToken(String username) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		String token = JWT.create()
				.withSubject(username)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeAccessToken))
				.sign(algorithm);
		return token;
	}
	
	public String generateRefreshToken(String username) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		String token = JWT.create()
				.withSubject(username)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeRefreshToken))
				.sign(algorithm);			
		return token;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public DecodedJWT decode(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJwt = verifier.verify(token);
		return decodedJwt;
	}
	
	public boolean verify(String token, HttpServletResponse response) throws IOException{
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		try {
			verifier.verify(token);
			return true;
		} catch (SignatureVerificationException e) {
			System.out.println("Invalid Signature");
			
		}
		catch (TokenExpiredException e) {
			System.out.println("token expired");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		catch (JWTVerificationException e) {
			System.out.println("verification failed");
		}
		return false;
	}

	public int getExpirationTimeRefreshToken() {
		return expirationTimeRefreshToken;
	}
	
}
