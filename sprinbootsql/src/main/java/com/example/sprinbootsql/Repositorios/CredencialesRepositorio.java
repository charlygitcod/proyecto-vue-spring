package com.example.sprinbootsql.Repositorios;

import com.example.sprinbootsql.Entidades.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialesRepositorio extends JpaRepository<Credenciales, Integer> {
}
