package com.autopartscrm.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autopartscrm.config.JwtProvider;
import com.autopartscrm.dto.TokenResponse;
import com.autopartscrm.model.Role;
import com.autopartscrm.model.Usuario;
import com.autopartscrm.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public Usuario salvar(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	public TokenResponse autenticar(String user, String senha) {
		Usuario admin = usuarioRepository.findByUsername(user)
				.orElseThrow(() -> new EntityNotFoundException("Usuário ou senha inválidos"));

		if (!passwordEncoder.matches(senha, admin.getPassword())) {
			throw new EntityNotFoundException("Usuário ou senha inválidos");
		}
		
		String accessToken = jwtProvider.generateAccessToken(admin);
		
		return new TokenResponse(accessToken);
	}

	public Usuario cadastrar(Usuario request) {
		// request.setRoles(Role.CLIENTE.asString());
		request.setPassword(passwordEncoder.encode(request.getPassword()));
		Usuario usuario = usuarioRepository.save(request);

		return usuario;
	}
}