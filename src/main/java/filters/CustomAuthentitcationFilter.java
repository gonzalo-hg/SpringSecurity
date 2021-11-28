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
	
	/**
	 * Contructor para crear la autenticacion
	 * @param authenticationManager
	 */
	public CustomAuthentitcationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	/**
	 * Se anula la autenticacion por default de Spring 
	 * Se hace la autenticacion cada vez que el usuario quiere iniciar sesion 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
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

		//Se devuelve el username pero sin espacios en blanco, al inicio o final
		username = username.trim();
		
		//Se agrega el request, el usuario que pide inicio de sesio
		//Y el administrador de hacer autetnica con los datos del request
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		//Se puede establecer enviar el token al body
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
	
		//Guardamos el usuario autenticado
		User usuario = (User) authentication.getPrincipal();
		//Se define un objeto Algorithm para la verificacion del token 
		//y se firma el token con la palabra secret
		//Esa palabra se tiene que cambiar y guardar en otro lugar
		//De preferencia que vaya codificada
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());//Esto debe ponerse en otro lado
								//Mezcla el secreo con datos del mensaje y hace esto por setunda vez
		//public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes()); ejemplo de una palabra secreta

		String accessToken = JWT.create()
				//pasamos el nombre del usuario en una cedena
				.withSubject(usuario.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))//Se define el tiempo del token que son 10 minutos de la hora actual
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);//Crea un nuevo JWT y firma con el algoritmo dado
		String refreshToken = JWT.create()
				.withSubject(usuario.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() +30*60*1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		Map<String, String> tokens  = new HashMap<>();
		response.setHeader("access_token", accessToken);//Se pasa como un header
		logger.info(accessToken);
		//response.setHeader("refresh_token", refreshToken);//se pasa como un header
		response.setHeader("nombreUsuario", usuario.getUsername());
		//Pasamos los token al body
		tokens.put("accessToken", accessToken);
		//tokens.put("refreshToken", refreshToken);
		//tokens.put("username", usuario.getUsername());
		response.setStatus(200);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}
	
	/**
	 * Este metodo se lanza cuando el usuario o la contraseña no son validos o si son nulos
	 * Se anula el metodo unsuccessfulAuthentication de Spring para personalizarlo de la siguiente manera
	 * La respuesta es un 401
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación: username o password incorrecto");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
}
