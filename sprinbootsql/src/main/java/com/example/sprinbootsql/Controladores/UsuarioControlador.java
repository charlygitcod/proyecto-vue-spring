package com.example.sprinbootsql.Controladores;

import com.example.sprinbootsql.Entidades.Usuario;
import com.example.sprinbootsql.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicios;

    // Crear un nuevo usuario
    @PostMapping("/insertarusuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario saveUsuario = usuarioServicios.crearUsuario(usuario);
        return new ResponseEntity<>(saveUsuario, HttpStatus.CREATED);
    }

    // Consultar usuarios con filtro
    @GetMapping("/consultarusuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios(@RequestParam(value = "query", defaultValue = "") String query) {
        List<Usuario> usuarios = usuarioServicios.obtenerUsuarios(query);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Modificar un usuario existente
    @PutMapping("/modificarusuario/{documento}")
    public ResponseEntity<Usuario> modificarUsuario(@PathVariable Integer documento, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioServicios.modificarUsuario(documento, usuario);
        if (updatedUsuario != null) {
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si el usuario no existe
    }

    // Eliminar un usuario
    @DeleteMapping("/eliminarusuario/{documento}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer documento) {
        boolean eliminado = usuarioServicios.eliminarUsuario(documento);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
