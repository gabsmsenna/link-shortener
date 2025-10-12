package dev.senna.core.usecase;

import dev.senna.adapter.in.web.dto.request.LoginRequest;
import dev.senna.adapter.in.web.dto.response.LoginResponse;
import dev.senna.core.exception.LoginException;
import dev.senna.core.port.in.AuthenticatePortIn;
import dev.senna.core.port.out.UserRepositoryPortOut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import java.time.Instant;


@Component
public class AuthenticationUseCase implements AuthenticatePortIn {

    private final UserRepositoryPortOut userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationUseCase(UserRepositoryPortOut userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public LoginResponse execute(LoginRequest request) {
        var userOpt = this.userRepository.findByEmail(request.email())
                .orElseThrow(LoginException::new);

        var isPasswordValid = bCryptPasswordEncoder.matches(request.password(), userOpt.getPassword());

        if (!isPasswordValid) {
            throw new LoginException();
        }

        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .subject(userOpt.getUserId().toString())
                .issuer("link-shortner")
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .claim("email", userOpt.getEmail())
                .build();

        var tokenJwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(tokenJwt, expiresIn);
    }
}
