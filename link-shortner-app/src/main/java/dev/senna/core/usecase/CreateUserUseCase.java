package dev.senna.core.usecase;

import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.in.CreateUserPortIn;
import dev.senna.core.port.out.UserRepositoryPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase implements CreateUserPortIn {

    private final UserRepositoryPortOut userRepositoryPortOut;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);

    public CreateUserUseCase(UserRepositoryPortOut userRepositoryPortOut, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepositoryPortOut = userRepositoryPortOut;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDomain execute(UserDomain user) {
        logger.info("Creating user {}", user.getNickname());

        user.encodePassword(bCryptPasswordEncoder);

        var userCreated =  userRepositoryPortOut.save(user);
        logger.info("User created {}", userCreated.getUserId());
        return userCreated;
    }
}
