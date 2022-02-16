package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.dto.UpdateProfileRequestDto;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/search") //todo @NotEmpty validation
    public Response<UserSearchInfoDto> getUserSearchInfo(@RequestParam(name = "username") String username) {
        return Response.success(userService.getUserSearchInfo(username));
    }

    @GetMapping("/all") //todo delete this
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/update-profile")
    public void updateProfile(@Valid @RequestBody UpdateProfileRequestDto requestDto) {
        userService.updateProfile(requestDto);
    }

}
