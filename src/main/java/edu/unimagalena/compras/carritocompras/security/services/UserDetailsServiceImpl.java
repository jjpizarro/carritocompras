package edu.unimagalena.compras.carritocompras.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unimagalena.compras.carritocompras.entities.Usuario;
import edu.unimagalena.compras.carritocompras.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(usuario);
    }
}
