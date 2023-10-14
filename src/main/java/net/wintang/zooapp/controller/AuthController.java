package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.AuthRequestDTO;
import net.wintang.zooapp.dto.response.AuthResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.security.JwtGenerator;
import net.wintang.zooapp.service.IAuthService;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtGenerator jwtGenerator;
    private final IAuthService authService;

    @Autowired
    public AuthController(JwtGenerator jwtGenerator, IAuthService authService) {
        this.jwtGenerator = jwtGenerator;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody AuthRequestDTO user, BindingResult result) throws NotFoundException {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            AuthResponseDTO response = new AuthResponseDTO();
            response.setErrors(errors);
            return new ResponseEntity<>(new ResponseObject(
                    ApplicationConstants.ResponseStatus.FAILED,
                    ApplicationConstants.ResponseMessage.INVALID,
                    response), HttpStatus.BAD_REQUEST);
        }
        return authService.checkLogin(user);
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
