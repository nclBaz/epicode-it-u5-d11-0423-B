package riccardogulin.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import riccardogulin.u5d11.entities.User;
import riccardogulin.u5d11.exceptions.UnauthorizedException;
import riccardogulin.u5d11.payloads.users.UserLoginDTO;
import riccardogulin.u5d11.security.JWTTools;

@Service
public class AuthService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private JWTTools jwtTools;

	public String authenticateUser(UserLoginDTO body) {
		// 1. Verifichiamo che l'email dell'utente sia nel db
		User user = usersService.findByEmail(body.email());

		// 2. In caso affermativo, verifichiamo se la password fornita corrisponde a quella trovata nel db
		if (body.password().equals(user.getPassword())) {
			// 3. Se le credenziali sono OK --> Genere un token JWT e lo ritorno
			return jwtTools.createToken(user);
		} else {
			// 4. Se le credenziali NON sono OK --> 401 (Unauthorized)
			throw new UnauthorizedException("Credenziali non valide!");
		}
	}
}
