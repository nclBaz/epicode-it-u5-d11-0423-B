package riccardogulin.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riccardogulin.u5d11.entities.User;
import riccardogulin.u5d11.exceptions.BadRequestException;
import riccardogulin.u5d11.payloads.users.NewUserDTO;
import riccardogulin.u5d11.payloads.users.NewUserResponseDTO;
import riccardogulin.u5d11.payloads.users.UserLoginDTO;
import riccardogulin.u5d11.payloads.users.UserLoginResponseDTO;
import riccardogulin.u5d11.services.AuthService;
import riccardogulin.u5d11.services.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthService authService;
	@Autowired
	UsersService usersService;

	@PostMapping("/login")
	public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
		String accessToken = authService.authenticateUser(body);
		return new UserLoginResponseDTO(accessToken);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) {
		// Per completare la validazione devo in qualche maniera fare un controllo del tipo: se ci sono errori -> manda risposta con 400 Bad Request
		System.out.println(validation);
		if (validation.hasErrors()) {
			System.out.println(validation.getAllErrors());
			throw new BadRequestException("Ci sono errori nel payload!"); // L'eccezione arriverà agli error handlers tra i quali c'è quello che manderà la risposta con status code 400
		} else {
			User newUser = usersService.save(newUserPayload);

			return new NewUserResponseDTO(newUser.getId());
		}
	}
}
