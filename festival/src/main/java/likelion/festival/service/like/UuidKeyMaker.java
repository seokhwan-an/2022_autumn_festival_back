package likelion.festival.service.like;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidKeyMaker implements UniqueKeyMaker {

    @Override
    public String generate() {
        return String.valueOf(UUID.randomUUID());
    }
}
