package filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders.*;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
		}else {
			String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
			
		}
		
	}

}
