
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warden extends Actor {

	private String	email;


	public Warden() {

	}

	@NotBlank
	@Pattern(regexp = "[\\w.%-]+\\@[-.\\w]+\\.[A-Za-z]{2,4}|[\\w.%-]+\\<+[\\w.%-]+\\@[-.\\w]+\\.[A-Za-z]{2,4}\\>|[\\w.%-]+\\<[\\w.%-]+\\@+\\>|[\\w.%-]+\\@+|[\\w.%-]+\\<+[\\w.%-]+\\@+\\>")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
