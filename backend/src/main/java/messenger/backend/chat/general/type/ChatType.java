package messenger.backend.chat.general.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChatType {
    GROUP("GROUP"),
    PERSONAL("PERSONAL");

    private final String type;
}
