package com.example.sprinbootsql.Repositorios;

import com.example.sprinbootsql.Entidades.RegistroPeso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistroPesoRepositorio extends JpaRepository<RegistroPeso, Integer> {
    Optional<RegistroPeso> findByUsuario_Documento(Integer documento);
}
