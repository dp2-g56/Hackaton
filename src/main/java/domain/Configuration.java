
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {


	private List<String>	spamWords;
	private List<String>	typeProductsEN;
	private List<String>	typeProductsES;
	private String			spainTelephoneCode;
	private int				minFinderResults;
	private int				maxFinderResults;
	private int				timeFinderPrisoners;
	private int 			finderResult;
	private String			welcomeMessageEnglish;
	private String			welcomeMessageSpanish;
	private String			systemName;
	private String			imageURL;
	private int				timeFinderActivities;


	@Valid
	public int getFinderResult() {
		return this.finderResult;
	}

	public void setFinderResult(int finderResult) {
		this.finderResult = finderResult;
	}

	@ElementCollection(targetClass = String.class)
	public List<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(List<String> spamWords) {
		this.spamWords = spamWords;
	}

	@ElementCollection(targetClass = String.class)
	public List<String> getTypeProductsEN() {
		return this.typeProductsEN;
	}

	public void setTypeProductsEN(List<String> typeProductsEN) {
		this.typeProductsEN = typeProductsEN;
	}

	@ElementCollection(targetClass = String.class)
	public List<String> getTypeProductsES() {
		return this.typeProductsES;
	}

	public void setTypeProductsES(List<String> typeProductsES) {
		this.typeProductsES = typeProductsES;
	}

	@NotBlank
	public String getSpainTelephoneCode() {
		return this.spainTelephoneCode;
	}

	public void setSpainTelephoneCode(String spainTelephoneCode) {
		this.spainTelephoneCode = spainTelephoneCode;
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
	public String getImageURL() {
		return this.imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Valid
	public int getMinFinderResults() {
		return this.minFinderResults;
	}

	public void setMinFinderResults(int minFinderResults) {
		this.minFinderResults = minFinderResults;
	}

	@Valid
	public int getMaxFinderResults() {
		return this.maxFinderResults;
	}

	public void setMaxFinderResults(int maxFinderResults) {
		this.maxFinderResults = maxFinderResults;
	}

	@Valid
	public int getTimeFinderPrisoners() {
		return this.timeFinderPrisoners;
	}

	public void setTimeFinderPrisoners(int timeFinderPrisoners) {
		this.timeFinderPrisoners = timeFinderPrisoners;
	}

	@Valid
	public int getTimeFinderActivities() {
		return this.timeFinderActivities;
	}

	public void setTimeFinderActivities(int timeFinderActivities) {
		this.timeFinderActivities = timeFinderActivities;
	}

}
