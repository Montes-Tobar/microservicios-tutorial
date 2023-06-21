package com.example.usuario.service.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.usuario.service.entidad.Usuario;
import com.example.usuario.service.feignclients.CarroFeignClient;
import com.example.usuario.service.feignclients.MotoFeignClient;
import com.example.usuario.service.modelos.Carro;
import com.example.usuario.service.modelos.Moto;
import com.example.usuario.service.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
	
	//Inyección de la configuración de conexión de microservicios con RestTemplate
	@Autowired
	private RestTemplate restTemplate;
	
	//Inyección de la configuración de conexión de microservicios con Feign Client
	@Autowired
	private CarroFeignClient carroFeignClient;
	@Autowired
	private MotoFeignClient motoFeignClient;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
//------------------------- Rest Template ------------------------------
	
	/** El método getCarros() corresponde a la lista de carros por usuario,
	 *  utiliza la inyección de la configuración RestTemplate y conecta al
	 *  servidor del microservicio carro-service */
	public List<Carro> getCarros(int usuarioId){
		List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
		return carros;
	}
	
	/** El método getMotos() corresponde a la lista de motos por usuario,
	 *  utiliza la inyección de la configuración RestTemplate y conecta al
	 *  servidor del microservicio moto-service */
	public List<Moto> getMotos(int usuarioId){
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
		return motos;
	}
	
//------------------------- Rest Template ------------------------------
	
//------------------------- Feign Client -------------------------------
	
	/** El método saveCarro() utiliza el Feign Client para conectar los
	 *  microservicios. Este método guarda nuevos carros*/
	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}
	
	/** El método saveMoto() utiliza el Feign Client para conectar los
	 *  microservicios. Este método guarda nuevas motos */
	public Moto saveMoto(int usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevaMoto = motoFeignClient.save(moto);
		return nuevaMoto;
	}
	
	/** Este método retornará los registros de los microservicios conectados al
	 *  microservicio principal. En este ejemplo dentro del microservicio de
	 *  usuario-service se deberán de listar los elementos de carro-service y
	 *  los de moto-service que correspondan al mismo ID de usuario. */
	public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
		Map<String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		
		if(usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}
		
		resultado.put("Usuario", usuario);
		
		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}
		else {
			resultado.put("Carros", carros);
		}
		
		List<Moto> motos = motoFeignClient.getMotos(usuarioId);
		if(motos.isEmpty()) {
			resultado.put("Motos", "El usuario no tiene motos");
		}
		else {
			resultado.put("Motos", motos);
		}
		
		return resultado;		
	}
	
//------------------------- Feign Client -------------------------------
	
	public List<Usuario> getAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}
}
