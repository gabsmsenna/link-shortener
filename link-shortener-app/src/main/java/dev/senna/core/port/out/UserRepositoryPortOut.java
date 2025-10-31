package dev.senna.core.port.out;

import dev.senna.core.domain.UserDomain;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPortOut {

    UserDomain save(UserDomain user);

    Optional<UserDomain> findByEmail(String email);

    void deleteById(UUID userId);

    Optional<UserDomain> findById(UUID userId);

    Long count();
}
