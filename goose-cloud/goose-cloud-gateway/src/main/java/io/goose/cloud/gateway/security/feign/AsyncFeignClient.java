package io.goose.cloud.gateway.security.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "async-server", url = "localhost:9002",path="/redis")
public interface AsyncFeignClient {
	
	@PostMapping("/put/token")
	Boolean putToken(@RequestParam("mobileNumber") String mobileNumber,
			@RequestParam("accessToken") String accessToken,
			@RequestParam("refreshToken") String refreshToken,
			@RequestParam("accessExpire") Integer accessExpire,
			@RequestParam("refreshExpire") Integer refreshExpire);
	
	@GetMapping("/match/token")
	public Boolean existToken(
			@RequestParam("mobileNumber") String mobileNumber,
			@RequestParam("token") String token,
			@RequestParam(name="type", required=false) Integer type);


}
