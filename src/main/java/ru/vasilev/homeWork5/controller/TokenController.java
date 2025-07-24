package ru.vasilev.homeWork5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vasilev.homeWork5.dto.TokenRequest;
import ru.vasilev.homeWork5.dto.TokenResponse;
import ru.vasilev.homeWork5.model.TokenPayload;
import ru.vasilev.homeWork5.service.TokenService;

@Tag(name = "Token API", description = "Операции по генерации и валидации токенов")
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Генерация токена", description = "Создает JWE+JWS токен на основе имени пользователя и роли")
    @PostMapping("/generate")
    public ResponseEntity<TokenResponse> generate(@RequestBody TokenRequest request) throws Exception {
        TokenPayload payload = new TokenPayload(request.getUsername(), request.getRole());
        String token = tokenService.generateToken(payload);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Operation(summary = "Валидирует токен")
    @PostMapping("/validate")
    public ResponseEntity<TokenPayload> validate(@RequestBody TokenResponse request) throws Exception {
        TokenPayload payload = tokenService.validateToken(request.getToken());
        return ResponseEntity.ok(payload);
    }
}