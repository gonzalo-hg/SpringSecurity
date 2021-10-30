package filters;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
		}else {
			String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {														  
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier =  JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities= new ArrayList<>();
					/*stream(roles).forEach(rol ->{
						authorities.add(new SimpleGrantedAuthority(rol));
					});*/
					
					UsernamePasswordAuthenticationToken authentication = 
							new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					log.error("Error  loggin in: {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					Map<String, String> error  = new HashMap<>();
					error.put("refreshToken", e.getMessage());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			}else {
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
