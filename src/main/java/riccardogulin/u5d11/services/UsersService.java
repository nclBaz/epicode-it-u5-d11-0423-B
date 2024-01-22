package riccardogulin.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import riccardogulin.u5d11.entities.User;
import riccardogulin.u5d11.exceptions.BadRequestException;
import riccardogulin.u5d11.exceptions.NotFoundException;
import riccardogulin.u5d11.payloads.users.NewUserDTO;
import riccardogulin.u5d11.repositories.UsersDAO;

import java.util.UUID;

@Service
public class UsersService {
	@Autowired
	private UsersDAO usersDAO;


	public Page<User> getUsers(int page, int size, String orderBy) {
		// return usersDAO.findAll();
		if (size >= 100) size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy)); // Di default l'ordine è ascendente
		// Se volessimo cambiare l'ordine si usa Sort.Direction.DESC
		// Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, orderBy));
		return usersDAO.findAll(pageable);
	}

	public User save(NewUserDTO body) {
		// Verifico se l'email è già in uso
		/*Optional<User> user = usersDAO.findByEmail(body.getEmail());
		if(user.isPresent()) throw new RuntimeException();*/

		usersDAO.findByEmail(body.email()).ifPresent(user -> {
			throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
		});

		User newUser = new User();
		newUser.setSurname(body.surname());
		newUser.setName(body.name());
		newUser.setEmail(body.email());
		newUser.setPassword(body.password());
		return usersDAO.save(newUser);
	}

	public User findById(UUID id) {
		return usersDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public void findByIdAndDelete(UUID id) {
		User found = this.findById(id);
		usersDAO.delete(found);
	}

	public User findByIdAndUpdate(UUID id, User body) {
		User found = this.findById(id);
		found.setSurname(body.getSurname());
		found.setName(body.getName());
		found.setEmail(body.getEmail());
		found.setPassword(body.getPassword());
		return usersDAO.save(found);
	}


	public User findByEmail(String email) throws NotFoundException {
		return usersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovata!"));
	}


}
