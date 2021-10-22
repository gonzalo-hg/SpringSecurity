package filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomAuthentitcationFilter extends UsernamePasswordAuthenticationFilter{

	@Autowired
	private  AuthenticationManager authenticationManager;
	
	public CustomAuthentitcationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	
	//----------->NO pasa la autenticacion, devuelve valores nulos :S
	/**
	 * Se intenta jacer la auntenticacion con los datos
	 * recabados del usuarios
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("userName");//Se toma la infor que viene en el request
		String password = request.getParameter("password");
		
		log.info("Username: {}", username); log.info("Password:{} ",password);//Luego se pasa al token de autenticacion 
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}
	
	/***
	 * 
	 *Este metodo es llamdao cuando la autenticacion fue exitosa
	 *y se genera el token para enviar la informacion del usuario
	 *@param request
	 */

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User usuario = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());//Esto debe ponerse en otro lado
		String accessToken = JWT.create()
				.withSubject(usuario.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refreshToken = JWT.create()
				.withSubject(usuario.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() +30*60*1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		//response.setHeader("accessToken", accessToken);
		//response.setHeader("refreshToken", refreshToken); 
		Map<String, String> tokens  = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}

	
	
}
