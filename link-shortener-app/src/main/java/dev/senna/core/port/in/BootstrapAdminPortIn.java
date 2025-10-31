package dev.senna.core.port.in;

import dev.senna.adapter.in.web.dto.request.CreateUserRequest;
import dev.senna.core.domain.UserDomain;

public interface BootstrapAdminPortIn {
    UserDomain execute(UserDomain request);
}
