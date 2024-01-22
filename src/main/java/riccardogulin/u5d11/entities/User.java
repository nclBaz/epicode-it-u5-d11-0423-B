package riccardogulin.u5d11.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String email;
	private String password;
}
