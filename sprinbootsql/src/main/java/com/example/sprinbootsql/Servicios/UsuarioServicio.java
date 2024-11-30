package com.example.sprinbootsql.Servicios;

import com.example.sprinbootsql.Entidades.Usuario;
import com.example.sprinbootsql.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Usuario modificarUsuario(Integer documento, Usuario usuario) {
        if (usuarioRepositorio.existsById(documento)) {
            usuario.setDocumento(documento);
            return usuarioRepositorio.save(usuario);
        }
        return null;
    }

    public List<Usuario> obtenerUsuarios(String query) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        if (query == null || query.isEmpty()) {
            return usuarios;
        }
        // Filtramos los usuarios en memoria usando el parámetro `query`
        return usuarios.stream()
                .filter(u -> String.valueOf(u.getDocumento()).contains(query) ||
                        (u.getNombre() != null && u.getNombre().contains(query)) ||
                        (u.getApellido() != null && u.getApellido().contains(query)))
                .collect(Collectors.toList());
    }

    public boolean eliminarUsuario(Integer documento) {
        if (usuarioRepositorio.existsById(documento)) {
            usuarioRepositorio.deleteById(documento);
            return true;
        }
        return false;
    }
}
