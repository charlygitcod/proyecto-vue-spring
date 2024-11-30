package com.example.sprinbootsql.Controladores;

import com.example.sprinbootsql.Entidades.EntregaEPP;
import com.example.sprinbootsql.Servicios.EntregaEPPServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/entregasepp")
public class EntregaEPPControlador {

    @Autowired
    private EntregaEPPServicio entregaEPPServicio;

    // Insertar una nueva entrega
    @PostMapping("/insertar")
    public ResponseEntity<EntregaEPP> crearEntrega(@RequestBody EntregaEPP entregaEPP) {
        EntregaEPP nuevaEntrega = entregaEPPServicio.crearEntrega(entregaEPP);
        return ResponseEntity.ok(nuevaEntrega);
    }

    // Consultar todas las entregas
    @GetMapping("/consultar")
    public ResponseEntity<List<EntregaEPP>> obtenerEntregas() {
        List<EntregaEPP> entregas = entregaEPPServicio.obtenerEntregas();
        return ResponseEntity.ok(entregas);
    }

    // Consultar entregas por documento del empleado
    @GetMapping("/consultar/{documento}")
    public ResponseEntity<List<EntregaEPP>> obtenerEntregaPorDocumento(@PathVariable Integer documento) {
        List<EntregaEPP> entregas = entregaEPPServicio.obtenerEntregaPorDocumento(documento);
        if (entregas.isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no hay entregas, retorna 404
        }
        return ResponseEntity.ok(entregas);
    }
}
