package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.AuthRequest;
import com.auth.dto.TokenResponse;
import com.auth.model.Consumer;
import com.auth.model.Recycler;
import com.auth.model.Roles;
import com.auth.model.UserInfo;
import com.auth.repository.ConsumerRepository;
import com.auth.repository.RecyclerRepository;
import com.auth.repository.RolesRepository;
import com.auth.repository.UserInfoRepository;

@Service
public class AuthService {

	@Autowired
	private UserInfoRepository usersRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ConsumerRepository consumerRepository;
	
	@Autowired
	private RecyclerRepository recyclerRepository;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	
	public void storeItInUserInfo(String name, String email, String password, int roleId) {
		
		UserInfo userInfo = new UserInfo(name,email,password, roleId);
		Roles role = rolesRepository.findById(userInfo.getRoleId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
		userInfo.setRole(role);
		usersRepository.save(userInfo);
	}

	public String registerConsumer(Consumer consumer) {
		consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
		storeItInUserInfo(consumer.getUsername(), consumer.getEmail(), consumer.getPassword(),2);
		consumerRepository.save(consumer);
	
		return "Successfully Consumer Registered!!";
	}

	public String registerRecycler(Recycler recycler) {
		recycler.setPassword(passwordEncoder.encode(recycler.getPassword()));
		storeItInUserInfo(recycler.getOrganizationName(), recycler.getEmail(), recycler.getPassword(),1);
		recyclerRepository.save(recycler);
		
		return "Successfully Recycler Registered!";
	}


	public TokenResponse authenticate (AuthRequest authRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(authRequest.getUsername());
			UserInfo userInfo = usersRepository.findByEmail(authRequest.getUsername()).orElse(null); 
			String role = userInfo.getRole().getRole();
			String email = authRequest.getUsername();		
			String username = userInfo.getName();
			
			int id = 0;
			if(role.equalsIgnoreCase("ROLE_RECYCLER")) {
				id = recyclerRepository.findByEmail(email).getRecyclerId();
			}else {
				id = consumerRepository.findByEmail(email).getConsumerId();
			}
			
			return new TokenResponse (token,username, email, role, id);
		} else {
			throw new UsernameNotFoundException("invalid user request!");
		}
	}
	
	public Consumer getUserDetails(int id) {
		Consumer consumer = consumerRepository.findById(id).orElseThrow();
		return consumer;
	}
	
}
