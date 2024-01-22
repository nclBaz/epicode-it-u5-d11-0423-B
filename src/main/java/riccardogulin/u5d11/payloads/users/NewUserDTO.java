package riccardogulin.u5d11.payloads.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
		@NotEmpty(message = "Il nome è un campo obbligatorio!")
		@Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri!")
		String name,
		@NotEmpty(message = "Il cognome è un campo obbligatorio!")
		@Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra 3 e 30 caratteri!")
		String surname,
		@Email(message = "L'indirizzo inserito non è un indirizzo valido")
		@NotEmpty(message = "La mail è un campo obbligatorio!")
		String email,
		@NotEmpty(message = "La password è un campo obbligatorio!")
		@Size(min = 4, message = "La password deve avere minimo 4 caratteri!")
		String password) {
}


// I Record definiscono delle classi speciali, IMMUTABILI (cioè non ho a disposizione dei setter per cambiarne i valori post creazione)
// Ci forniscono una maniera concisa per definire queste classi e sono adatti per i DTO
