
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SalesManRepository;
import domain.SalesMan;

@Service
@Transactional
public class SalesManService {

	@Autowired
	private SalesManRepository	salesManRepository;


	public List<SalesMan> findAll() {
		return this.salesManRepository.findAll();
	}

	public SalesMan findOne(int salesManId) {
		return this.salesManRepository.findOne(salesManId);
	}

}
