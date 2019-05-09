package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PrisonerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Prisoner;
import domain.Visitor;

@Service
@Transactional
public class PrisonerService {

	@Autowired
	private PrisonerRepository	prisonerRepository;


	// -----------------------------------------SECURITY-----------------------------
	// ------------------------------------------------------------------------------

	public Prisoner save(Prisoner prisoner) {
		return this.prisonerRepository.save(prisoner);
	}

	public Prisoner findOne(int prisonerId) {
		return this.prisonerRepository.findOne(prisonerId);
	}

	public Prisoner loggedPrisoner() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		return this.prisonerRepository.getPrisonerByUsername(userAccount.getUsername());
	}

	public void loggedAsPrisoner() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("PRISONER"));
	}

	public List<Prisoner> findAll() {
		return this.prisonerRepository.findAll();
	}


	public List<Visitor> getVisitorsToCreateVisit(Prisoner prisoner) {
		int prisonerId = prisoner.getId();
		return this.prisonerRepository.getVisitorsToCreateVisit(prisonerId);
	}

	public List<Prisoner> getFreePrisoners() {
		return this.prisonerRepository.getFreePrisoners();
	}

}
