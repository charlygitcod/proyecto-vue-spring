package com.example.sprinbootsql.Repositorios;

import com.example.sprinbootsql.Entidades.EntregaEPP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaEPPRepositorio extends JpaRepository<EntregaEPP, Integer> {
    List<EntregaEPP> findByUsuario_Documento(Integer documento);


}

