package com.samplepacks.digital_store.service;

import com.samplepacks.digital_store.dto.LoginRequest;
import com.samplepacks.digital_store.dto.LoginResponse;
import com.samplepacks.digital_store.dto.SignupRequest;
import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.entity.VerificationToken;
import com.samplepacks.digital_store.enums.UserRole;
import com.samplepacks.digital_store.exception.EmailFailureException;
import com.samplepacks.digital_store.exception.UserAlreadyExistsException;
import com.samplepacks.digital_store.exception.UserNotVerifiedException;
import com.samplepacks.digital_store.repository.LocalUserDAO;
import com.samplepacks.digital_store.repository.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final LocalUserDAO localUserDAO;
    private final VerificationTokenDAO verificationTokenDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    private final EmailService emailService;

    public UserService(LocalUserDAO localUserDAO, VerificationTokenDAO verificationTokenDAO, EncryptionService encryptionService,
                       JWTService jwtService, EmailService emailService) {
        this.localUserDAO = localUserDAO;
        this.verificationTokenDAO = verificationTokenDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    // 2. Proper instance method
    @PreAuthorize("hasRole('ADMIN')")
    public List<LocalUser> getAllUsers() {
        return localUserDAO.findAll();  // Correct instance method call
    }

    /**
     * Attempts to register a user given the information provided.
     * @param signupRequest The registration information.
     * @return The local user that has been written to the database.
     * @throws UserAlreadyExistsException Thrown if there is already a user with the given information.
     */
    public LocalUser registerUser(SignupRequest signupRequest) throws UserAlreadyExistsException, EmailFailureException {
        if (LocalUserDAO.findByEmailIgnoreCase(signupRequest.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(signupRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPassword(encryptionService.encryptPassword(signupRequest.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return localUserDAO.save(user);
    }

    /**
     * Creates a VerificationToken object for sending to the user.
     * @param user The user the token is being generated for.
     * @return The object created.
     */
    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    /**
     * Logins in a user and provides an authentication token back.
     * @param loginRequest The login request.
     * @return The authentication token. Null if the request was invalid.
     */
    public String loginUser(LoginRequest loginRequest) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginRequest.getUsername());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() ||
                            verificationTokens.getFirst().getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }
    public LoginResponse registerUser(SignupRequest signupRequest, UserRole role) throws UserAlreadyExistsException {
        if (LocalUserDAO.findByEmailIgnoreCase(signupRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();
        user.setEmail(signupRequest.getEmail());
        user.setRole(role); // Set the role from parameter
        // ... rest of registration logic

        return new LoginResponse();
    }

    /**
     * Verifies a user from the given token.
     * @param token The token to use to verify a user.
     * @return True if it was verified, false if already verified or token invalid.
     */
    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}