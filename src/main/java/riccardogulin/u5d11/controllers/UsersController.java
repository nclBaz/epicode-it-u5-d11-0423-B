package riccardogulin.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riccardogulin.u5d11.entities.User;
import riccardogulin.u5d11.exceptions.BadRequestException;
import riccardogulin.u5d11.payloads.users.NewUserDTO;
import riccardogulin.u5d11.payloads.users.NewUserResponseDTO;
import riccardogulin.u5d11.services.UsersService;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersService usersService;

	@GetMapping
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
	                           @RequestParam(defaultValue = "10") int size,
	                           @RequestParam(defaultValue = "id") String orderBy) {
		return usersService.getUsers(page, size, orderBy);
	}

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable UUID userId) {
		return usersService.findById(userId);
	}

	@PostMapping
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

	@PutMapping("/{userId}")
	public User getUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody User modifiedUserPayload) {
		return usersService.findByIdAndUpdate(userId, modifiedUserPayload);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void getUserByIdAndDelete(@PathVariable UUID userId) {
		usersService.findByIdAndDelete(userId);
	}


}
