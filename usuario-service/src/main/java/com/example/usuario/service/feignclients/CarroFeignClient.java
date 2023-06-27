package com.example.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.usuario.service.modelos.Carro;

//Para gateway no es necesario especificar url en la anotación @FeignClient
@FeignClient(name = "carro-service")
@RequestMapping("/carro")
public interface CarroFeignClient {

	@PostMapping()
	public Carro save(@RequestBody Carro carro);
	
	@GetMapping("/usuario/{usuarioId}")
	public List<Carro> getCarros(@PathVariable("usuarioId") int usuarioId);
}
