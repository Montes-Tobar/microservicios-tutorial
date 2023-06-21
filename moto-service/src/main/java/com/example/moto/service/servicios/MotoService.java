package com.example.moto.service.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.moto.service.entidades.Moto;
import com.example.moto.service.repositorio.MotoRepository;

@Service
public class MotoService {

	@Autowired
	private MotoRepository motoRepository;
	
	public List<Moto> getAll(){
		return motoRepository.findAll();
	}
	
	public Moto getMotoById(int id) {
		return motoRepository.findById(id).orElse(null);
	}
	
	public Moto save(Moto moto) {
		Moto nuevaMoto = motoRepository.save(moto);
		return nuevaMoto;
	}
	
	//Método que lista todas las motos de un Usuario (usuario-service)
	public List<Moto> byUsuarioId(int usuarioId){
		return motoRepository.findByUsuarioId(usuarioId);
	}
}
