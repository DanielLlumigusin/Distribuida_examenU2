package com.example.evento.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.evento.Entity.Asistente;

import java.util.List;

@FeignClient(name = "asistente", url = "http://localhost:8003/api/asistente")
public interface AsistenteFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<Asistente> getAsistente(@PathVariable Long id);

    @GetMapping
    List<Asistente> getAllAsistente();
}
