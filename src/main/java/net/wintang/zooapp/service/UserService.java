package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.OrderMapper;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.dto.request.VerificationRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.dto.response.VerificationResponseDTO;
import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.security.JwtGenerator;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IEmailService emailService;
    private final JwtGenerator jwtGenerator;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAuthService authService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper, IEmailService emailService, JwtGenerator jwtGenerator, OrderRepository orderRepository, PasswordEncoder passwordEncoder, IAuthService authService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.jwtGenerator = jwtGenerator;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        UserMapper.mapToUserDTO(userRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getUserById(int id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        UserMapper.mapToUserDTO(user))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        UserMapper.mapToUserDTO(userRepository.findByRole(4)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getStaff() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        UserMapper.mapToUserDTO(userRepository.findByRole(2)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getZooTrainers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        UserMapper.mapToUserDTO(userRepository.findByRole(3)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createCustomer(UserRequestDTO userDto) throws DuplicatedKeyException {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            if (!userRepository.existsByEmail(userDto.getEmail())) {
                User user = userMapper.mapToUserEntity(userDto);
                user.setRoles(Collections.singletonList(new Role(4, "CUSTOMER")));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS, userDto)
                );
            }
            throw new DuplicatedKeyException("Email: " + userDto.getEmail());
        }
        throw new DuplicatedKeyException("Username: " + userDto.getUsername());
    }

    @Override
    public ResponseEntity<ResponseObject> createStaff(UserRequestDTO userDto) throws DuplicatedKeyException {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            if (!userRepository.existsByEmail(userDto.getEmail())) {
                User user = userMapper.mapToUserEntity(userDto);
                user.setRoles(Collections.singletonList(new Role(2, "STAFF")));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS, userDto)
                );
            }
            throw new DuplicatedKeyException("Email: " + userDto.getEmail());
        }
        throw new DuplicatedKeyException("Username: " + userDto.getUsername());
    }

    @Override
    public ResponseEntity<ResponseObject> createZooTrainer(UserRequestDTO userDto) throws DuplicatedKeyException {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            if (!userRepository.existsByEmail(userDto.getEmail())) {
                User user = userMapper.mapToUserEntity(userDto);
                user.setRoles(Collections.singletonList(new Role(3, "ZOO_TRAINER")));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS, userDto)
                );
            }
            throw new DuplicatedKeyException("Email: " + userDto.getEmail());
        }
        throw new DuplicatedKeyException("Username: " + userDto.getUsername());
    }

    @Override
    public ResponseEntity<ResponseObject> updateUserById(UserUpdateDTO newUser, int id) throws NotFoundException, PermissionDeniedException {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Integer.parseInt(authenticatedUser.getUsername()) != id && !authenticatedUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))
                && !authenticatedUser.getAuthorities().contains(new SimpleGrantedAuthority("STAFF"))) {
            throw new PermissionDeniedException();
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (existingUser.getRoles().get(0).getName().equals("STAFF") && Integer.parseInt(authenticatedUser.getUsername()) != id && !authenticatedUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                throw new PermissionDeniedException();
            }
            User updatedUser = userMapper.mapToUserEntity(newUser, existingUser);
            updatedUser.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            UserMapper.mapToUserDTO(userRepository.save(updatedUser)))
            );
        }
        throw new NotFoundException("User ID: " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteUserById(int id) throws NotFoundException {
        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id));
            } catch (DataAccessException ex) {
                User user = userRepository.findById(id).get();
                user.setStatus(false);
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id));
            }
        }
        throw new NotFoundException("User ID: " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> resetPassword(String newPassword, String token) throws NotFoundException {
        if (jwtGenerator.validateToken(token)) {
            User user = userRepository.findByEmail(jwtGenerator.getUserIdFromJwt(token)).orElseThrow(() -> new NotFoundException("Email"));
            user.setResetToken(null);
            userRepository.save(userMapper.encodePassword(user, newPassword));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            "Password reset")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.INVALID,
                        "Expired")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> verifyEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email: " + email));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        emailService.sendResetPasswordMail(user))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> verifyCode(VerificationRequestDTO request) throws NotFoundException {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("Email: " + request.getEmail()));
        String token = user.getResetToken();
        if (jwtGenerator.validateToken(token)) {
            if (jwtGenerator.getCodeFromJwt(token).equals(request.getCode())) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                new VerificationResponseDTO(token))
                );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.INVALID,
                        "Expired")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getOrdersByUserId(int id) throws PermissionDeniedException {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authenticatedUser.getUsername().equals(String.valueOf(id))) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            OrderMapper.mapToOrderDto(orderRepository.findAllByCustomer(User.builder().id(id).build())))
            );
        }
        throw new PermissionDeniedException();
    }

    @Override
    public ResponseEntity<ResponseObject> changePassword(String oldPassword, String newPassword, String token) throws NotFoundException {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(Integer.parseInt(authenticatedUser.getUsername()))
                .orElseThrow(() -> new NotFoundException("User ID: " + authenticatedUser.getUsername()));
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            authService.logout(token);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            "Changed password, please log in again")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                        "Old password not matched")
        );
    }
}
