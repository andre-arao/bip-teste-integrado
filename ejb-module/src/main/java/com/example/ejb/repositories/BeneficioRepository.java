package com.example.ejb.repositories;

import com.example.ejb.entity.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long>{
    Optional<Beneficio> findByNome(String nome);
}
