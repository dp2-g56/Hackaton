
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WardenRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Warden;

@Service
@Transactional
public class WardenService {

	@Autowired
	private WardenRepository	wardenRepository;


	// ----------------------------------------CRUD
	// METHODS--------------------------
	// ------------------------------------------------------------------------------

	public Warden save(Warden Warden) {
		return this.wardenRepository.save(Warden);
	}

	// -----------------------------------------SECURITY-----------------------------
	// ------------------------------------------------------------------------------

	/**
	 * LoggedWarden now contains the security of loggedAsWarden
	 * 
	 * @return
	 */
	public Warden loggedWarden() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("WARDEN"));
		return this.wardenRepository.getWardenByUsername(userAccount.getUsername());
	}

	public void loggedAsWarden() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("WARDEN"));

	}

	public void saveNewWarden(Warden Warden) {
		this.loggedAsWarden();
		this.wardenRepository.save(Warden);
	}

}
