
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;


	public Configuration getConfiguration() {
		return this.configurationRepository.findAll().get(0);
	}

	public Configuration save(Configuration configuration) {
		return this.configurationRepository.save(configuration);
	}

	public Boolean isStringSpam(String s, List<String> spamWords) {
		Boolean result = false;

		List<String> trimmedString = new ArrayList<String>();
		trimmedString = Arrays.asList(s.split("\\+|(?=[,.¿?;!¡])"));

		// ("\\s*(=>|,|\\s)\\s*"));
		for (String g : spamWords)
			for (String c : trimmedString)
				if (g.equals(c) || g.equalsIgnoreCase(c)) {
					result = true;
					break;
				}

		return result;
	}

	/*
	 * public Boolean isActorSuspicious(Actor a) {
	 * boolean result = false;
	 * List<String> spamWords = new ArrayList<String>();
	 * spamWords = this.getSpamWords();
	 * Integer spamCount = 0;
	 * Integer messagesCount = 0;
	 * Double spamPorcent = 0.;
	 * 
	 * // COMPROBANDO LAS CAJAS DEL ACTOR
	 * 
	 * for (Message g : a.getMessages())
	 * if (g.getSender().equals(a) && (this.isStringSpam(g.getBody(), spamWords) || this.isStringSpam(g.getSubject(), spamWords)))
	 * spamCount++;
	 * 
	 * spamPorcent = spamCount / messagesCount * 100.;
	 * 
	 * if (spamPorcent >= 10)
	 * result = true;
	 * 
	 * return result;
	 * }
	 */

	public void loggedAsWarden() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("Warden"));
	}

}
