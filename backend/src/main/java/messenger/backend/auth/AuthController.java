package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.dto.RefreshTokenDto;
import messenger.backend.auth.dto.RegisterRequestDto;
import messenger.backend.refreshToken.RefreshTokenEntity;
import messenger.backend.refreshToken.RefreshTokenRepository;
import messenger.backend.user.dto.CurrentUserInfoDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService            authService;

    @PostMapping("/login")
    public Response<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
            return Response.success(authService.login(authRequestDto));
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
         authService.register(registerRequestDto);
    }

    @PostMapping("/logout")
    public void logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        authService.logout(refreshTokenDto);
    }

    @GetMapping("/me")
    public Response<CurrentUserInfoDto> getUserInfo() {
            return Response.success(authService.getCurrentUserInfo());
    }

    @PostMapping("/refresh")
    public Response<AuthResponseDto> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
            return Response.success(authService.refreshToken(refreshTokenDto));
    }

    @GetMapping("/tokens") // just for test todo delete this
    public List<RefreshTokenEntity> getTokens() {
        return refreshTokenRepository.findAll();
    }

    @PostMapping("/logout/all") // just for test (or no) todo delete this?
    public void logoutFromAllDevices() {
        authService.logoutAll();
    }

}
