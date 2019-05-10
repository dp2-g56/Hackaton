
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SalesManRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.SalesMan;

@Service
@Transactional
public class SalesManService {

	@Autowired
	private SalesManRepository	salesManRepository;


	public SalesMan loggedSalesMan() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("SALESMAN"));
		return this.salesManRepository.getSalesManByUsername(userAccount.getUsername());
	}

	public void loggedAsSalesMan() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("SALESMAN"));
	}

	public List<SalesMan> findAll() {
		return this.salesManRepository.findAll();
	}

	public SalesMan findOne(int salesManId) {
		return this.salesManRepository.findOne(salesManId);
	}

	public SalesMan save(SalesMan salesMan) {
		return this.salesManRepository.save(salesMan);
	}

}
