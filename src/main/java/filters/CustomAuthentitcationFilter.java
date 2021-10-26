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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uam.aga.app.models.Usuario;

public class CustomAuthentitcationFilter extends UsernamePasswordAuthenticationFilter{

	@Autowired
	private  AuthenticationManager authenticationManager;
	
	public CustomAuthentitcationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	
	//----------->NO pasa la autenticacion, devuelve valores nulos :S
	/**
	 * 
https://www.scaledagileframework.com/
https://www.finvivir.com.mx/quienes-somos
	 * Se intenta jacer la auntenticacion con los datos
	 * recabados del usuarios
	 
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		//System.out.println(requ));
		System.out.println(getUsernameParameter());

		System.out.println(response.getStatus());
		String username = request.getParameter("username");//Se toma la infor que viene en el request
		String password = request.getParameter("password");
		
		log.info("Username: {}", username); 
		log.info("Password:{} ",password);//Luego se pasa al token de autenticacion 
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		System.out.println(authenticationToken.getName());
		System.out.println("Que dices: "+ authenticationManager.authenticate(authenticationToken).toString());
		System.out.println("Cualquiera");
		return authenticationManager.authenticate(authenticationToken);
	}*/
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("Entras?????");
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if(username != null && password !=null) {
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);
			
			
		} else {
			Usuario user = null;
			try {
				System.out.println("Usuario: " +username);
			System.out.println("Password: "+ password);
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username desde request InputStream (raw): " + username);
				logger.info("Password desde request InputStream (raw): " + password);
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace(); 
			}
		}

		username = username.trim();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authToken);
	}
	
	/***
	 * https://drive.google.com/file/d/1NpJpb9Kcrj9ani1qI0Fm5jNecErHQKrQ/view?usp=sharing
	 *Este metodo es llamdao cuando la autenticacion fue exitosa
	 *y se genera el token para enviar la informacion del usuario
	 *@param request
	 */

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("Entre aqui :3");
		User usuario = (User) authentication.getPrincipal();
		//Se define un objeto Algorithm para la verificacion del token 
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());//Esto debe ponerse en otro lado
								//Mezcla el secreo con datos del mensaje y hace esto por setunda vez
		String accessToken = JWT.create()
				.withSubject(usuario.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))//Se define el tiempo del token 
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);//Crea un nuevo JWT y firma con el algoritmo dado
		String refreshToken = JWT.create()
				.withSubject(usuario.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() +30*60*1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		Map<String, String> tokens  = new HashMap<>();
		//response.setHeader("access_token", accessToken);
		//response.setHeader("refresh_token", refreshToken);
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación: username o password incorrecto!");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
}
