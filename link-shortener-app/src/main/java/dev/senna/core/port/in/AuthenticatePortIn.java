package dev.senna.core.port.in;

import dev.senna.adapter.in.web.dto.request.LoginRequest;
import dev.senna.adapter.in.web.dto.response.LoginResponse;

public interface AuthenticatePortIn {

    LoginResponse execute(LoginRequest request);
}
