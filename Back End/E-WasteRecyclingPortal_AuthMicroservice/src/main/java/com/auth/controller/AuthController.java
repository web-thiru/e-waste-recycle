package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.AuthRequest;
import com.auth.dto.TokenResponse;
import com.auth.model.Consumer;
import com.auth.model.Recycler;
import com.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	@Autowired
	private AuthService service;

//	@Autowired
//	private JwtService jwtService;

//	@PostMapping("/welcome")
//	public String welcome(@RequestBody String s) {
//		return "Welcome this endpoint is not secure"+s;
//	}
//
//	@GetMapping("/recycler")
//	@PreAuthorize("hasAuthority('ROLE_RECYCLER')")
//	public String recycler() {
//		return "Recycler!!!!";
//	}
//
//	@GetMapping("/consumer")
//	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
//	public String consumer() {
//		return "Consumer!!!!";
//	}

//	@GetMapping("/validate")
//	public String validateToken(@RequestParam("token") String token) {
//		jwtService.validateToken(token);
//		return "Token is valid";
//	}

	@PostMapping("/registerRecycler")
	public String addNewRecycler(@RequestBody Recycler recycler) {
		return service.registerRecycler(recycler);
	}

	@PostMapping("/registerConsumer")
	public String addNewConsumer(@RequestBody Consumer consumer) {
		return service.registerConsumer(consumer);
	}

	@PostMapping("/authenticate")
	public TokenResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		System.out.println(authRequest.getUsername());
		return service.authenticate(authRequest);

	}

}
