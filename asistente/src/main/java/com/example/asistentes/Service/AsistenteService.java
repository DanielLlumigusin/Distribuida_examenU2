package com.example.asistentes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.asistentes.Entity.Asistente;
import com.example.asistentes.Repository.AsistenteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenteService {

    @Autowired
    private AsistenteRepository asistenteRepository;

    public Asistente saveAsistente(Asistente asistente) {
    	asistente.setCreated_at(LocalDateTime.now());
        return asistenteRepository.save(asistente);
    }

    public Optional<Asistente> getAsistenteById(Long id) {
        return asistenteRepository.findById(id);
    }

        public List<Asistente> getAllAsistentes() {
        return asistenteRepository.findAll();
    }

    public Asistente updateAsistente(Long id, Asistente updatedAsistente) {
        	return asistenteRepository.save(updatedAsistente);
        }

    public void deleteAsistente(Long id) {
        if (asistenteRepository.existsById(id)) {
            asistenteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Asistente " + id + " No encontrado");
        }
    }
}
