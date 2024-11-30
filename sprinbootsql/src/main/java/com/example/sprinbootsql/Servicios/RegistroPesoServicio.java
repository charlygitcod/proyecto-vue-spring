package com.example.sprinbootsql.Servicios;

import com.example.sprinbootsql.Entidades.RegistroPeso;
import com.example.sprinbootsql.Repositorios.RegistroPesoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroPesoServicio {

    @Autowired
    private RegistroPesoRepositorio registroPesoRepositorio;

    // Insertar un nuevo registro con lógica de turno
    public RegistroPeso crearRegistro(RegistroPeso registroPeso) {
        return registroPesoRepositorio.save(registroPeso);
    }


    // Consultar todos los registros
    public List<RegistroPeso> obtenerRegistros() {
        return registroPesoRepositorio.findAll();
    }

    // Consultar un registro por el documento del empleado
    public RegistroPeso obtenerRegistroPorDocumento(Integer documento) {
        return registroPesoRepositorio.findByUsuario_Documento(documento)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el registro para el documento"));
    }
}
