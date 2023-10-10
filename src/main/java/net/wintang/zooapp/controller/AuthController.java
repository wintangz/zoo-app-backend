package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.AuthResponseDTO;
import net.wintang.zooapp.dto.UserDTO;
import net.wintang.zooapp.repository.RoleRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.security.JwtGenerator;
import net.wintang.zooapp.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            AuthResponseDTO response = new AuthResponseDTO();
            response.setErrors(errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        // Validate and invalidate the token
        TokenExtractor tokenExtractor = new TokenExtractor();
        String token = tokenExtractor.extractToken(authorization);
        if (token != null && jwtGenerator.validateToken(token)) {
            jwtGenerator.invalidateToken(token);
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
