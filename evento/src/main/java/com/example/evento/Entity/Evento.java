package com.example.evento.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EventoAsistente> eventoAsistentes = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public List<EventoAsistente> getEventoAsistentes() {
		return eventoAsistentes;
	}

	public void setEventoAsistentes(List<EventoAsistente> eventoAsistentes) {
		this.eventoAsistentes = eventoAsistentes;
	}

    
    
}
