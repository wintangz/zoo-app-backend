package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AuthRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ResponseObject> checkLogin(AuthRequestDTO userDto) throws NotFoundException;
}
