package dev.senna.core.usecase;

import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.in.CreateUserPortIn;
import dev.senna.core.port.out.UserRepositoryPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase implements CreateUserPortIn {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);

    private final UserRepositoryPortOut userRepositoryPortOut;

    public CreateUserUseCase(UserRepositoryPortOut userRepositoryPortOut) {
        this.userRepositoryPortOut = userRepositoryPortOut;
    }

    @Override
    public UserDomain execute(UserDomain user) {
        logger.info("Creating user  {}, ", user.getEmail());
        var userCreated = userRepositoryPortOut.save(user);
        logger.info("User created {}", userCreated.getUserId());
        return userCreated;
    }
}
