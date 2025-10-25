package dev.senna.core.usecase;

import dev.senna.core.exception.UserNotFoundException;
import dev.senna.core.port.in.DeleteUserPortIn;
import dev.senna.core.port.out.UserRepositoryPortOut;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteUserUseCase implements DeleteUserPortIn {

    private final UserRepositoryPortOut userRepository;

    public DeleteUserUseCase(UserRepositoryPortOut userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UUID userId) {
        userRepository.findById(userId)
                        .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(userId);
    }
}
