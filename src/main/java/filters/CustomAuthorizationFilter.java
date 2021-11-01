package filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Esta clase personalizada esta creada para que cada vez que un usuario 
 * que este correctamente autenicado y haga una peticion de un servicio 
 * pase por aqui y verifique si tiene los permisos necesarios para poder acceder
 * a los recursos
 * @author gonzalo
 *
 */
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{


	public static final String TOKEN_PREFIX = "Bearer ";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("EntrasSSSSSSSSS????");
		//Si el usuario quiere solo iniciar sesion, no pasa por ningun filtro
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
			System.out.println("Entras????");
		}
		//Si desea hacer otro request, entonces hace los siguientes filtros de reclamación
		else {
			String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
			if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
				try {		
					System.out.println("Entras aqui????"+TOKEN_PREFIX);
					//Eliminanos el prefijo del token 
					String token = authorizationHeader.substring(TOKEN_PREFIX.length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier =  JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					System.out.println("Roles: "+ authorities.stream());
					Arrays.stream(roles).forEach(rol -> {
						authorities.add(new SimpleGrantedAuthority(rol));
						System.out.println("Roles: "+ rol);
					});
					//Aqui le decimos a spring quien es el usuario que solicita el recurso
					//su rol del usuario y decide si entra al servicio o no 
					UsernamePasswordAuthenticationToken authentication = 
							new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					log.error("Error  loggin in: {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
					Map<String, String> error  = new HashMap<>();
					//response.sendError(org.springframework.http.HttpStatus.FORBIDDEN.value());
					error.put("error_message", e.getMessage());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			}else {
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
