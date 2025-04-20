package com.samplepacks.digital_store.service;

import com.samplepacks.digital_store.dto.LoginRequest;
import com.samplepacks.digital_store.dto.SignupRequest;
import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.exception.UserAlreadyExistsException;
import com.samplepacks.digital_store.repository.LocalUserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private LocalUserDAO localUserDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(SignupRequest signupRequest) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(signupRequest.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(signupRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPassword(encryptionService.encryptPassword(signupRequest.getPassword()));
        return localUserDAO.save(user);
    }

    public String loginUser(LoginRequest loginRequest) {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginRequest.getUsername());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
