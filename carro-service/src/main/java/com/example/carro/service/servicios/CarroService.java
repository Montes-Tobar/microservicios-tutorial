package com.example.carro.service.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carro.service.entidades.Carro;
import com.example.carro.service.repositorio.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;
	
	public List<Carro> getAll(){
		return carroRepository.findAll();
	}
	
	public Carro getCarroById(int id) {
		return carroRepository.findById(id).orElse(null);
	}
	
	public Carro save(Carro carro) {
		Carro nuevoCarro = carroRepository.save(carro);
		return nuevoCarro;
	}
	
	//Método que lista todos los Carros de un Usuario (usuario-service)
	public List<Carro> byUsuarioId(int usuarioId){
		return carroRepository.findByUsuarioId(usuarioId);
	}
}
