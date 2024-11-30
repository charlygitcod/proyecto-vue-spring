package com.example.sprinbootsql.Servicios;

import com.example.sprinbootsql.Entidades.EntregaEPP;
import com.example.sprinbootsql.Repositorios.EntregaEPPRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntregaEPPServicio {

    @Autowired
    private EntregaEPPRepositorio entregaEPPRepositorio;

    // Crear una nueva entrega
    public EntregaEPP crearEntrega(EntregaEPP entrega) {
        return entregaEPPRepositorio.save(entrega);
    }

    // Consultar todas las entregas
    public List<EntregaEPP> obtenerEntregas() {
        return entregaEPPRepositorio.findAll();
    }

    // Consultar entregas por documento de empleado
    public List<EntregaEPP> obtenerEntregaPorDocumento(Integer empleado_documento) {
        return entregaEPPRepositorio.findByUsuario_Documento(empleado_documento);
    }

    // Consultar entrega por ID
    public Optional<EntregaEPP> consultarPorId(Integer idEntrega) {
        return entregaEPPRepositorio.findById(idEntrega);
    }
}
