package com.samplepacks.digital_store.repository;

import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.entity.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
