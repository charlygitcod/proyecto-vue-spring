package com.example.sprinbootsql.Repositorios;

import com.example.sprinbootsql.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {

}
