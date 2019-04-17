
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private ActivityStatus	status;
	private String			motivation;
	private String			rejectReason;
	private Activity		activity;


	@NotNull
	public ActivityStatus getStatus() {
		return this.status;
	}

	public void setStatus(ActivityStatus status) {
		this.status = status;
	}

	@NotBlank
	public String getMotivation() {
		return this.motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@ManyToOne
	public Activity getActivity() {
		return this.activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
