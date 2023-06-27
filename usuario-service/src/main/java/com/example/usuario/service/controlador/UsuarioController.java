package com.example.usuario.service.controlador;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.usuario.service.entidad.Usuario;
import com.example.usuario.service.modelos.Carro;
import com.example.usuario.service.modelos.Moto;
import com.example.usuario.service.servicio.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	/** La clase ResponseEntity<> permite manipular respuestas HTTP desde
	  * el servidor hasta el cliente. En este ejemplo se regresa un mensaje
	  * indicando la ausencia de contenido en la lista de usuarios en caso
	  * de que no existan registros y se haya realizado la petición listarUsuarios()
	  * para obtener el listado completo de usuarios.*/
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			/*
			 * En este caso ResponseEntity envía un mensaje de "No encontrado",
			 * diferente al caso del método listarUsuarios() 
			 */
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}
	
//------------------------- Rest Template ------------------------------

	/** Controlador que lista los carros de un usuario, utiliza el servicio
	 *  getCarros() cuyo funcionamiento es conectar al microservicio carro-service.
	 *  Usando Rest Template*/
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Carro> carros = usuarioService.getCarros(id);
		return ResponseEntity.ok(carros);
	}
	
	/** Controlador que lista las motos de un usuario, utiliza el servicio
	 *  getMotos() cuyo funcionamiento es conectar al microservicio moto-service.
	 *  Usando Rest Template */
	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Moto> motos = usuarioService.getMotos(id);
		return ResponseEntity.ok(motos);
	}
	
//------------------------- Rest Template -----------------------------

//------------------------- Feign Client -------------------------------
	
	/** Controlador que guarda carros al usuario por medio de Feign Client*/
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro){
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}
	
	/** Controlador que guarda motos al usuario por medio de Feign Client*/
	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto){
		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	/** Controlador para listar todos los vehículos del usuario */
	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}
	
//------------------------- Feign Client -------------------------------
	
	
//-------------------Métodos de Circuit Breaker-------------------------
	
	public ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") int id, RuntimeException exception){
		return new ResponseEntity("El usuario : " + id + "tiene los carros en el taller", HttpStatus.OK);
	}
	
	public ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro, RuntimeException exception){
		return new ResponseEntity("El usuario : " + id + "no tiene dinero para el carro", HttpStatus.OK);
	}
	
	public ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") int id, RuntimeException exception){
		return new ResponseEntity("El usuario : " + id + "tiene las motos en el taller", HttpStatus.OK);
	}
	
	public ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto, RuntimeException exception){
		return new ResponseEntity("El usuario : " + id + "no tiene dinero para la moto", HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable("usuarioId") int id, RuntimeException exception){
		return new ResponseEntity("El usuario : " + id + "tiene los vehículos en el taller", HttpStatus.OK);
	}
}
