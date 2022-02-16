package messenger.backend.auth.access_levels;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    USER_PERMISSION("ALL_PERMISSIONS");

    private final String permission;
}
