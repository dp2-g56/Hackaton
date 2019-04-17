
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String	spamWords;
	private String	spainTelephoneCode;
	private int		minFinderResults;
	private int		maxFinderResults;
	private int		minTimeResults;
	private int		maxTimeResults;
	private String	goodWords;
	private String	badWords;
	private int		timeFinderPrisoners;
	private String	welcomeMessageEnglish;
	private String	welcomeMessageSpanish;
	private String	systemName;
	private String	imageUrl;
	private int		timeFinderActivities;


	@NotBlank
	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(String spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	public String getSpainTelephoneCode() {
		return this.spainTelephoneCode;
	}

	public void setSpainTelephoneCode(String spainTelephoneCode) {
		this.spainTelephoneCode = spainTelephoneCode;
	}

	@NotBlank
	public String getGoodWords() {
		return this.goodWords;
	}

	public void setGoodWords(String goodWords) {
		this.goodWords = goodWords;
	}

	@NotBlank
	public String getBadWords() {
		return this.badWords;
	}

	public void setBadWords(String badWords) {
		this.badWords = badWords;
	}

	@NotBlank
	public String getWelcomeMessageEnglish() {
		return this.welcomeMessageEnglish;
	}

	public void setWelcomeMessageEnglish(String welcomeMessageEnglish) {
		this.welcomeMessageEnglish = welcomeMessageEnglish;
	}

	@NotBlank
	public String getWelcomeMessageSpanish() {
		return this.welcomeMessageSpanish;
	}

	public void setWelcomeMessageSpanish(String welcomeMessageSpanish) {
		this.welcomeMessageSpanish = welcomeMessageSpanish;
	}

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getMinFinderResults() {
		return this.minFinderResults;
	}

	public void setMinFinderResults(int minFinderResults) {
		this.minFinderResults = minFinderResults;
	}

	public int getMaxFinderResults() {
		return this.maxFinderResults;
	}

	public void setMaxFinderResults(int maxFinderResults) {
		this.maxFinderResults = maxFinderResults;
	}

	public int getMinTimeResults() {
		return this.minTimeResults;
	}

	public void setMinTimeResults(int minTimeResults) {
		this.minTimeResults = minTimeResults;
	}

	public int getMaxTimeResults() {
		return this.maxTimeResults;
	}

	public void setMaxTimeResults(int maxTimeResults) {
		this.maxTimeResults = maxTimeResults;
	}

	public int getTimeFinderPrisoners() {
		return this.timeFinderPrisoners;
	}

	public void setTimeFinderPrisoners(int timeFinderPrisoners) {
		this.timeFinderPrisoners = timeFinderPrisoners;
	}

	public int getTimeFinderActivities() {
		return this.timeFinderActivities;
	}

	public void setTimeFinderActivities(int timeFinderActivities) {
		this.timeFinderActivities = timeFinderActivities;
	}

}
