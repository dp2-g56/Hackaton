package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Charge;
import repositories.ChargeRepository;

@Service
@Transactional
public class ChargeService {

	@Autowired
	private ChargeRepository chargeRepository;

	// CRUDS

	public Charge findOne(Integer id) {
		return this.chargeRepository.findOne(id);
	}

	public List<Charge> findAll() {
		return this.chargeRepository.findAll();
	}

	public Charge save(Charge charge) {
		return this.chargeRepository.save(charge);
	}

	public void delete(Charge charge) {
		this.chargeRepository.delete(charge);
	}

}
