package com.example.sprinbootsql.Controladores;

import com.example.sprinbootsql.Entidades.Credenciales;
import com.example.sprinbootsql.Servicios.CredencialesServicio;
import com.example.sprinbootsql.Entidades.Usuario;
import com.example.sprinbootsql.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/credenciales")
public class CredencialesControlador {

    @Autowired
    private CredencialesServicio credencialesServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Crear una nueva credencial
    @PostMapping("/insertarcredencial")
    public ResponseEntity<Credenciales> crearCredencial(@RequestBody Map<String, Object> requestBody) {
        Credenciales credencial = new Credenciales();
        credencial.setEmpleadoDocumento(Integer.parseInt(requestBody.get("empleado_documento").toString()));
        credencial.setPassword((String) requestBody.get("password"));

        Credenciales nuevaCredencial = credencialesServicio.crearCredencial(credencial);
        if (nuevaCredencial != null) {
            return new ResponseEntity<>(nuevaCredencial, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Consultar todas las credenciales
    @GetMapping("/consultarcredenciales")
    public ResponseEntity<List<Map<String, Object>>> obtenerCredenciales() {
        List<Map<String, Object>> credenciales = new ArrayList<>();
        for (Credenciales credencial : credencialesServicio.obtenerCredenciales()) {
            Map<String, Object> credencialMap = new HashMap<>();
            credencialMap.put("empleado_documento", credencial.getEmpleadoDocumento());
            credencialMap.put("password", credencial.getPassword());
            credencialMap.put("usuario", credencial.getUsuario());
            credenciales.add(credencialMap);
        }
        return new ResponseEntity<>(credenciales, HttpStatus.OK);
    }


    // Modificar una credencial
    @PutMapping("/modificarcredencial/{empleadoDocumento}")
    public ResponseEntity<Credenciales> modificarCredencial(@PathVariable Integer empleadoDocumento, @RequestBody Credenciales credencial) {
        Credenciales credencialModificada = credencialesServicio.modificarCredencial(empleadoDocumento, credencial);
        if (credencialModificada != null) {
            return new ResponseEntity<>(credencialModificada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar una credencial
    @DeleteMapping("/eliminarcredencial/{empleadoDocumento}")
    public ResponseEntity<Void> eliminarCredencial(@PathVariable Integer empleadoDocumento) {
        boolean eliminado = credencialesServicio.eliminarCredencial(empleadoDocumento);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        Integer nombreUsuario = Integer.parseInt(loginData.get("nombre_usuario"));
        String password = loginData.get("password");

        Credenciales credencial = credencialesServicio.obtenerCredencialPorEmpleado(nombreUsuario);
        if (credencial != null && credencial.getPassword().equals(password)) {
            Usuario usuario = credencial.getUsuario();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("rol", usuario.getRol().name());
            return ResponseEntity.ok(response);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario o contraseña incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
