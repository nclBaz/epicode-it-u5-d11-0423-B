package riccardogulin.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riccardogulin.u5d11.payloads.users.UserLoginDTO;
import riccardogulin.u5d11.payloads.users.UserLoginResponseDTO;
import riccardogulin.u5d11.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping("/login")
	public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
		String accessToken = authService.authenticateUser(body);
		return new UserLoginResponseDTO(accessToken);
	}
}
