package com.example.sprinbootsql.Servicios;

import com.example.sprinbootsql.Entidades.Credenciales;
import com.example.sprinbootsql.Entidades.Usuario;
import com.example.sprinbootsql.Repositorios.CredencialesRepositorio;
import com.example.sprinbootsql.Repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredencialesServicio {

    @Autowired
    private CredencialesRepositorio credencialesRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Crear una nueva credencial
    public Credenciales crearCredencial(Credenciales credencial) {
        Usuario usuario = usuarioRepositorio.findById(credencial.getEmpleadoDocumento()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        credencial.setUsuario(usuario);
        return credencialesRepositorio.save(credencial);
    }


    // Consultar todas las credenciales
    public Iterable<Credenciales> obtenerCredenciales() {
        return credencialesRepositorio.findAll();
    }

    // Modificar una credencial existente
    public Credenciales modificarCredencial(Integer empleadoDocumento, Credenciales credencial) {
        Credenciales credencialExistente = credencialesRepositorio.findById(empleadoDocumento).orElse(null);
        if (credencialExistente != null) {
            if (credencial.getPassword() != null) {
                credencialExistente.setPassword(credencial.getPassword());
            }
            return credencialesRepositorio.save(credencialExistente);
        }
        return null;
    }

    // Eliminar una credencial

    public Boolean eliminarCredencial(Integer empleadoDocumento) {
        if (usuarioRepositorio.existsById(empleadoDocumento)) {
            usuarioRepositorio.deleteById(empleadoDocumento);
            return true;
        }
        return false;
    }

    // Verificar credenciales para el login
    public Credenciales obtenerCredencialPorEmpleado(Integer empleadoDocumento) {
        return credencialesRepositorio.findById(empleadoDocumento).orElse(null);
    }
}

