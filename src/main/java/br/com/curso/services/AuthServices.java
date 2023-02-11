package br.com.curso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.curso.data.vo.v1.security.AccountCredentialsVO;
import br.com.curso.data.vo.v1.security.TokenVO;
import br.com.curso.repositories.UserRepository;
import br.com.curso.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	public ResponseEntity<TokenVO> signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			var user = repository.findByUsername(username);
			var tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception ex) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}