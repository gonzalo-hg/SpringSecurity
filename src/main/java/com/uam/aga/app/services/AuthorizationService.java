package com.uam.aga.app.services;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uam.aga.app.models.Rol;
import com.uam.aga.app.models.Usuario;


@Service
public class AuthorizationService {

	public static final String TOKEN_PREFIX = "Bearer ";
	
	@Autowired
	private UsuarioService usuarioService;
	
	public void refreshauthorizationToken(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		
		
		if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			try {	
				System.out.println("Entras al AuthorizationService");
				//Eliminanos el prefijo del token 
				String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier =  JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				String username = decodedJWT.getSubject();
				Usuario usuario  = usuarioService.getUsuario(username);
				
				String accessToken = JWT.create()
						//pasamos el nombre del usuario en una cedena
						.withSubject(usuario.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() +30*60*1000))//Se define el tiempo del token que son 10 minutos de la hora actual
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toList()))
						.sign(algorithm);//Crea un nuevo JWT y firma con el algoritmo dado
				Map<String, String> tokens  = new HashMap<>();
				//Pasamos los token al body
				tokens.put("accessToken", accessToken);
				tokens.put("refreshToken", refreshToken);
				response.setStatus(200);
				response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				System.out.println("Error pero dentro del servicio");
				response.setHeader("error", e.getMessage());
				response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
				Map<String, String> error  = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		}else {
			throw new RuntimeException("El token ha expirado, se tiene que actualizar");
		}
	}
}
