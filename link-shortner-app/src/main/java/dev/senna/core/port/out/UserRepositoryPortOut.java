package dev.senna.core.port.out;

import dev.senna.core.domain.UserDomain;

public interface UserRepositoryPortOut {

    UserDomain save(UserDomain user);
}
