package br.unip.ads.pim.config.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.UsuarioRepository;

@Component
public class ApiAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String email = authentication.getName();
		String senha = authentication.getCredentials().toString();

		Optional<Usuario> usuario = repository.findByEmailAndSenha(email, senha);
		
		if (usuario.isPresent()) {
			return new UsernamePasswordAuthenticationToken(email, senha, new ArrayList<>());
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
