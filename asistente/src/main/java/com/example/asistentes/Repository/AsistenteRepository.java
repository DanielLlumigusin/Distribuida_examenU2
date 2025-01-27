package com.example.asistentes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.asistentes.Entity.Asistente;

@Repository
public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
}


