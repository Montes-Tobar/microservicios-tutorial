package com.example.carro.service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.carro.service.entidades.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer>{

	//Método que lista todos los Carros de un Usuario (usuario-service)
	List<Carro> findByUsuarioId(int usuarioId);
}
