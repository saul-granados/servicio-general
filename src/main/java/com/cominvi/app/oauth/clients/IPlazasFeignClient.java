package com.cominvi.app.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicio-capitalhumano")
public interface IPlazasFeignClient {
	
	@GetMapping("/plazas/empleado/{idempleado}")
	public long findCentrotrabajoByIdempleado(@PathVariable Long idempleado);

}
