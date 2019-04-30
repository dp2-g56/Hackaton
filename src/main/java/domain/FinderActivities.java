
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FinderActivities extends DomainEntity {

	private String			keyWord;
	private Date			minDate;
	private Date			maxDate;

	private List<Activity>	activities;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMinDate() {
		return this.minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	@ManyToMany
	public List<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
