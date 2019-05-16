package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class TypeProduct extends DomainEntity {

	private List<String> typeProductEN;
	private List<String> typeProductES;

	@NotBlank
	public List<String> getTypeProductEN() {
		return this.typeProductEN;
	}

	public void setTypeProductEN(List<String> typeProductEN) {
		this.typeProductEN = typeProductEN;
	}

	@NotBlank
	public List<String> getTypeProductES() {
		return this.typeProductES;
	}

	public void setTypeProductES(List<String> typeProductES) {
		this.typeProductES = typeProductES;
	}

}
