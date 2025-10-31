package dev.senna.core.usecase;

import dev.senna.adapter.in.web.dto.request.CreateUserRequest;
import dev.senna.config.FeatureFlagConfig;
import dev.senna.core.domain.UserDomain;
import dev.senna.core.exception.ResourceNotAvailableException;
import dev.senna.core.port.in.BootstrapAdminPortIn;
import dev.senna.core.port.out.UserRepositoryPortOut;
import org.springframework.stereotype.Component;

@Component
public class BootstrapAdminUseCase implements BootstrapAdminPortIn {

    private final UserRepositoryPortOut userRepositoryPortOut;
    private final CreateUserUseCase createUserUseCase;
    private final FeatureFlagConfig featureFlagConfig;

    public BootstrapAdminUseCase(UserRepositoryPortOut userRepositoryPortOut, CreateUserUseCase createUserUseCase, FeatureFlagConfig featureFlagConfig) {
        this.userRepositoryPortOut = userRepositoryPortOut;
        this.createUserUseCase = createUserUseCase;
        this.featureFlagConfig = featureFlagConfig;
    }

    @Override
    public UserDomain execute(UserDomain user) {

        if (featureFlagConfig.getCreateUsersEnabled()) {
            throw new ResourceNotAvailableException();
        }

        if (userRepositoryPortOut.count() > 0) {
            throw new ResourceNotAvailableException();
        }

        return createUserUseCase.execute(user);
    }
}
