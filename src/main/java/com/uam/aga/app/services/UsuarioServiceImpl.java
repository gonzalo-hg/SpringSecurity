package com.uam.aga.app.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uam.aga.app.models.Role;
import com.uam.aga.app.models.Usuario;
import com.uam.aga.app.repository.RoleRepository;
import com.uam.aga.app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	private  PasswordEncoder passwordEnconder; //La usamos para codificar la contraseña 
	
	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		log.info("Se guardo un usuario {} en la BD",usuario.getNombre());
		usuario.setPassword(passwordEnconder.encode(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	@Override
	public Role guardarRole(Role role) {
		log.info("Se guardo un role {} en la BD", role.getNombre());
		return roleRepository.save(role);
	}

	@Override
	public void agregaRolUsuario(String username, String rolName) {
		log.info("Agregando un role {} al usuario {} en la BD", rolName, username);
		Usuario usuario = usuarioRepository.findByUserName(username);
		Role role = roleRepository.findByNombre(rolName);
		usuario.getRoles().add(role);
	}

	@Override
	public Usuario getUsuario(String username) {
		return usuarioRepository.findByUserName(username);
	}

	@Override
	public List<Usuario> getUsuarios() {	
		return usuarioRepository.findAll();
	}
	/***
	 * Este metodo se implementa para cargar 
	 * los usuarios desde la base de datos
	 * y se devuelve el usuario encontrado con seguridad
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Query query  = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("userName").is(username)));
		
		log.error("QUe entro aqui:{} {}", username,query);
		Usuario usuario = usuarioRepository.findByUserName(username);
		if(usuario == null) {
			log.error("USuario no encontado en la bd");
			throw new UsernameNotFoundException("USuario no encontado en la bd");
		}
		else {
			log.info("USuario  encontado en la bd: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		usuario.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
			});
		
		return new User(usuario.getUserName(), usuario.getPassword(),authorities);
	}

}

/***
 * Autenticacion es verificar quien eres
 * 
 * Autorizacion, son los limites en los que peudes navegar en la aplicacion
 * 
 * 
 */
