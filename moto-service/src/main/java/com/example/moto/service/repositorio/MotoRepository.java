package com.example.moto.service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.moto.service.entidades.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer>{

	//Método que lista todas las motos de un Usuario (usuario-service)
	List<Moto> findByUsuarioId(int usuarioId);
}
