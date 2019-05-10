
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Charge;
import repositories.ChargeRepository;

@Transactional
@Service
public class ChargeService {

	@Autowired
	private ChargeRepository chargeRepository;

	public List<Charge> findAll() {
		return this.chargeRepository.findAll();
	}

	public Charge findOne(int id) {
		return this.chargeRepository.findOne(id);
	}

	public List<Charge> getFinalCharges() {
		return this.chargeRepository.getFinalCharges();
	}

}
