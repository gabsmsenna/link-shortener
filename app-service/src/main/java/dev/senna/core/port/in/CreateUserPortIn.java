package dev.senna.core.port.in;

import dev.senna.core.domain.UserDomain;

public interface CreateUserPortIn {

    UserDomain execute(UserDomain req);
}
