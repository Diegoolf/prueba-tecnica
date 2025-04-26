package com.SistemaAlmacen.prueba.tecnica.service;
import java.util.List;
import com.SistemaAlmacen.prueba.tecnica.model.Usuario;
import com.SistemaAlmacen.prueba.tecnica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




@Service
public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol())
        );

        return new org.springframework.security.core.userdetails.User(
            usuario.getCorreo(),
            usuario.getContrasena(), // importante: debería estar encriptada
            usuario.getEstatus() == 1,
            true, true, true,
            authorities
        );
    }
}
