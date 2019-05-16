
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Product extends DomainEntity {

	private String		name;
	private String		description;
	private TypeProduct	type;
	private int			price;
	private int			stock;
	private Boolean		isDraftMode;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@ManyToOne
	public TypeProduct getType() {
		return this.type;
	}

	public void setType(TypeProduct type) {
		this.type = type;
	}

	@NotNull
	@Min(1)
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Min(0)
	@NotNull
	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@NotNull
	public Boolean getIsDraftMode() {
		return this.isDraftMode;
	}

	public void setIsDraftMode(Boolean isDraftMode) {
		this.isDraftMode = isDraftMode;
	}

}
