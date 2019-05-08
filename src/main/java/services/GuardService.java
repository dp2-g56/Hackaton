
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GuardRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Guard;
import domain.Visit;

@Service
@Transactional
public class GuardService {

	@Autowired
	private GuardRepository	guardRepository;


	// -----------------------------------------SECURITY-----------------------------
	// ------------------------------------------------------------------------------

	public Guard save(Guard guard) {
		return this.guardRepository.save(guard);
	}

	public Guard loggedGuard() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		return this.guardRepository.getGuardByUsername(userAccount.getUsername());
	}

	public void loggedAsGuard() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("GUARD"));
	}

	public Guard findOne(int guardId) {
		return this.guardRepository.findOne(guardId);
	}

	public List<Visit> getFutureAcceptedVisits() {
		return this.guardRepository.getFutureAcceptedVisits();
	}

}
