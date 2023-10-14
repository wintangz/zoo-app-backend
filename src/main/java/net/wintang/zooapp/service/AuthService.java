package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AuthRequestDTO;
import net.wintang.zooapp.dto.response.AuthResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.security.JwtGenerator;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtGenerator jwtGenerator;

    private final UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> checkLogin(AuthRequestDTO userDto) throws NotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRepository.findByUsername(userDto.getUsername())
                        .orElseThrow(() -> new NotFoundException(userDto.getUsername())).getId(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        new AuthResponseDTO(token))
        );
    }
}
