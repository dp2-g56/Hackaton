
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.VisitorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Visitor;

@Service
@Transactional
public class VisitorService {

	@Autowired
	private VisitorRepository	visitorRepository;


	// -----------------------------------------SECURITY-----------------------------
	// ------------------------------------------------------------------------------

	public Visitor save(Visitor visitor) {
		return this.visitorRepository.save(visitor);
	}

	public Visitor loggedVisitor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		return this.visitorRepository.getVisitorByUsername(userAccount.getUsername());
	}

	public void loggedAsVisitor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("VISITOR"));
	}

	public Visitor findOne(int visitorId) {
		return this.visitorRepository.findOne(visitorId);
	}

}
