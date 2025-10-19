package dev.senna.core.usecase;

import dev.senna.core.domain.Link;
import dev.senna.core.port.in.MyLinksPortIn;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyLinksUseCase implements MyLinksPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    public MyLinksUseCase(LinkRepositoryPortOut linkRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
    }

    @Override
    public List<Link> execute(String userId) {
        return linkRepositoryPortOut.finAllByUserId(userId);
    }
}
