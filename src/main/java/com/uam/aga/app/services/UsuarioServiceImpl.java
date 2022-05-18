package com.uam.aga.app.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uam.aga.app.repository.RoleRepository;
import com.uam.aga.app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.uam.springboot.app.negocio.modelo.Rol;
import mx.uam.springboot.app.negocio.modelo.Usuario;

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
	
	
	private Usuario usuario;
	
	
	/**
	 * Metodo que siver para almacenar usuarios con la inyeccion
	 * del UsuarioRepository
	 * @param usuario
	 * @return el usuario guardado
	 */
	@Override
	public Usuario saveUsuario(Usuario usuario){
		log.info("Se guardo un usuario {} en la BD",usuario.getNombre());
		usuario.setPassword(passwordEnconder.encode(usuario.getPassword()));
		usuario.setApellidoP(usuario.getApellidoP());
		usuario.setApellidoM(usuario.getApellidoM());
		System.out.println("USuario guardado Service: "+usuario);
		
		return usuarioRepository.save(usuario);
	}
	
	/**
	 * Metodo que siver para almacenar usuarios con la inyeccion
	 * del RoleRepository
	 * @param Rol
	 * @return el rol guardado
	 */
	@Override
	public Rol saveRole(Rol role) {
		log.info("Se guardo un role {} en la BD", role.getNombre());
		return roleRepository.save(role);
	}

	/**
	 * Metodo que siver para agregar un rol al usuario 
	 * @param 
	 * 
	 */
	@Override
	public void addRolUsuario(String username, String rolName) {
		log.info("Agregando un role {} al usuario {} en la BD", rolName, username);
		 usuario = usuarioRepository.findByUsername(username);
		Rol role = roleRepository.findByNombre(rolName);
		usuario.getRoles().add(role);
		usuarioRepository.save(usuario);
 	}
 
	/**
	 * Metodo que siver para buscar un usuario
	 * por medio de su username 
	 * @param 
	 * @return El usuario encontrado.
	 */
	@Override
	public Usuario getUsuario(String username) {
		return usuarioRepository.findByUsername(username);
	}

	/**
	 * Metodo que siver para obtener todos los usuarios
	 * registrados en la BD

	 * @return La lista de usuarios en la BD
	 */
	@Override
	public List<Usuario> getUsuarios() {	
		return usuarioRepository.findAll();
	}
	/***
	 * Este metodo se implementa para cargar 
	 * los usuarios desde la base de datos
	 * y se devuelve el usuario encontrado con seguridad
	 * @param Es el nombre de usuario a encontrar
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if(usuario == null) {
			log.error("USuario no encontrado en la bd");
			throw new UsernameNotFoundException("Usuario no encontrado en la bd");
		}
		else {
			log.info("USuario  encontado en la bd: {}", usuario);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		usuario.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
			//System.out.println(role);
			});
		
		return new User(usuario.getUsername(), usuario.getPassword(),authorities);
	}

}

/**
 * Autenticacion es verificar quien eres
 * 
 * Autorizacion, son los limites en los que peudes navegar en la aplicacion
 * 
 * 
 */
