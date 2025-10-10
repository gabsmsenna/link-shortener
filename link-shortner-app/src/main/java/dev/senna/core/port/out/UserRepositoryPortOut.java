package dev.senna.core.port.out;

import dev.senna.core.domain.UserDomain;

import java.util.Optional;

public interface UserRepositoryPortOut {

    UserDomain save(UserDomain user);

    Optional<UserDomain> findByEmail(String email);
}
