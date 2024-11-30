package com.example.sprinbootsql.Controladores;

import com.example.sprinbootsql.Entidades.RegistroPeso;
import com.example.sprinbootsql.Servicios.RegistroPesoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/pesos")
public class RegistroPesoControlador {

    @Autowired
    private RegistroPesoServicio registroPesoServicio;

    // Insertar un nuevo registro
    @PostMapping("/insertar")
    public ResponseEntity<RegistroPeso> crearRegistro(@RequestBody RegistroPeso registroPeso) {
        RegistroPeso nuevoRegistro = registroPesoServicio.crearRegistro(registroPeso);
        return ResponseEntity.ok(nuevoRegistro);
    }

    // Consultar todos los registros
    @GetMapping("/consultar")
    public ResponseEntity<List<RegistroPeso>> obtenerRegistros() {
        List<RegistroPeso> registros = registroPesoServicio.obtenerRegistros();
        return ResponseEntity.ok(registros);
    }

    // Consultar un registro por documento del empleado
    @GetMapping("/consultar/{documento}")
    public ResponseEntity<RegistroPeso> obtenerRegistroPorDocumento(@PathVariable Integer documento) {
        try {
            RegistroPeso registro = registroPesoServicio.obtenerRegistroPorDocumento(documento);
            return ResponseEntity.ok(registro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el registro
        }
    }
}
