package com.thomasgregsons.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomasgregsons.ecommerce.security.AuthenticationRequest;
import com.thomasgregsons.ecommerce.security.AuthenticationResponse;
import com.thomasgregsons.ecommerce.security.JwtUtil;
import com.thomasgregsons.ecommerce.service.UsuarioDetailService;
import com.thomasgregsons.ecommerce.utils.ApiRoutes;

@RestController
@RequestMapping(ApiRoutes.LOGIN)
public class LoginController {
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailService usuarioDetailService;

    @Autowired
    private JwtUtil jwtUtil;

  
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

    	try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsuario(), authenticationRequest.getContrasena())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Usuario o contraseña incorrectos", e);
        }

        final UserDetails userDetails = usuarioDetailService.loadUserByUsername(authenticationRequest.getUsuario());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Se crea el objeto de respuesta con JWT y datos del usuario
        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt(jwt);
        response.setUser(userDetails.getUsername());

        return ResponseEntity.ok(response);
   }
}
