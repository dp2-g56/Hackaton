package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Prisoner;
import repositories.PrisonerRepository;

@Service
@Transactional
public class PrisonerService {

	@Autowired
	private PrisonerRepository prisonerRepository;

	// CRUDS-------------------------------------------

	public List<Prisoner> findAll() {
		return this.prisonerRepository.findAll();
	}

}
