
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String			name;
	private Boolean			isSystem;
	private Box				fatherBox;
	private List<Message>	messages;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	public Boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Valid
	@ManyToOne(optional = true)
	public Box getFatherBox() {
		return this.fatherBox;
	}

	public void setFatherBox(Box fatherBox) {
		this.fatherBox = fatherBox;
	}

	@Valid
	@ManyToMany
	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
