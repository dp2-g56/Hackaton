package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Visitor;
import repositories.VisitorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class VisitorService {

	@Autowired
	private VisitorRepository visitorRepository;

	// CRUDS ------------------------------------------------------
	public Visitor findOne(Integer id) {
		return this.visitorRepository.findOne(id);
	}

	public Visitor save(Visitor visitor) {
		return this.visitorRepository.save(visitor);
	}

	// Auxiliar methods
	public Visitor securityAndVisitor() {
		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();

		Visitor logged = this.visitorRepository.getVisitorByUsername(username);
		Assert.notNull(logged);
		List<Authority> authorities = (List<Authority>) logged.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("VISITOR"));

		return logged;
	}

}
