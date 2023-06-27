package com.example.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.usuario.service.modelos.Moto;

//Para gateway no es necesario especificar url en la anotación @FeignClient
@FeignClient(name = "moto-service")
@RequestMapping("/moto")
public interface MotoFeignClient {

	@PostMapping()
	public Moto save(@RequestBody Moto moto);
	
	@GetMapping("/usuario/{usuarioId}")
	public List<Moto> getMotos(@PathVariable("usuarioId") int usuarioId);
}
