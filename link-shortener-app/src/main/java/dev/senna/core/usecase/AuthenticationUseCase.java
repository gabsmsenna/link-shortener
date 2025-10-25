package dev.senna.core.usecase;

import dev.senna.adapter.in.web.dto.request.LoginRequest;
import dev.senna.adapter.in.web.dto.response.LoginResponse;
import dev.senna.config.JwtConfig;
import dev.senna.core.exception.LoginException;
import dev.senna.core.port.in.AuthenticatePortIn;
import dev.senna.core.port.out.UserRepositoryPortOut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import java.time.Instant;

import static dev.senna.config.Constants.JWT_EMAIL_CLAIM;


@Component
public class AuthenticationUseCase implements AuthenticatePortIn {


    private final UserRepositoryPortOut userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConfig jwtConfig;

    public AuthenticationUseCase(UserRepositoryPortOut userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public LoginResponse execute(LoginRequest request) {
        var userOpt = this.userRepository.findByEmail(request.email())
                .orElseThrow(LoginException::new);

        var isPasswordValid = bCryptPasswordEncoder.matches(request.password(), userOpt.getPassword());

        if (!isPasswordValid) {
            throw new LoginException();
        }

        var expiresIn = jwtConfig.getExpiresIn();

        var claims = JwtClaimsSet.builder()
                .subject(userOpt.getUserId().toString())
                .issuer(jwtConfig.getIssuer())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .claim(JWT_EMAIL_CLAIM, userOpt.getEmail())
                .build();

        var tokenJwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(tokenJwt, expiresIn);
    }
}
