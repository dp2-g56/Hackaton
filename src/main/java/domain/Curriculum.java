
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String						ticker;

	private List<MiscellaneousRecord>	miscellaneousRecords;
	private List<EducationRecord>		educationRecords;
	private List<ProfessionalRecord>	professionalRecords;
	private PersonalRecord				personalRecord;


	public Curriculum() {		//Created for Json purposes
		super();
	}

	@Pattern(regexp = "[0-9]{2}[0-1]{1}[0-9]{3}-([A-Za-z0-9]{6})")
	@Column(unique = true)
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public List<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(List<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public List<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(List<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public List<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setProfessionalRecords(List<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@Valid
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}
}
