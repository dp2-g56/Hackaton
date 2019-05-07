package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Finder;
import domain.Prisoner;
import domain.Visitor;
import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository finderRepository;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private PrisonerService prisonerService;

	// CRUDS ----------------------------------------------

	public Finder create() {

		Finder res = new Finder();
		List<Prisoner> prisoners = new ArrayList<>();

		res.setKeyWord("");
		res.setCharge("");
		res.setPrisoners(prisoners);

		return res;
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(Integer id) {
		return this.finderRepository.findOne(id);
	}

	public void delete(Finder finder) {
		this.finderRepository.delete(finder);
	}

	public Finder save(Finder finder) {
		return this.finderRepository.save(finder);
	}

	public void filter(Finder finder) {
		Visitor visitor = this.visitorService.securityAndVisitor();

		List<Prisoner> res = this.prisonerService.findAll();
		List<Prisoner> filter = new ArrayList<>();

		if (finder.getKeyWord() != "") {
			filter = this.finderRepository.filterByKeyWord(finder.getKeyWord());
			res.retainAll(filter);
		}
		if (finder.getCharge() != "") {
			filter = this.finderRepository.filterByCharge(finder.getCharge());
			res.retainAll(filter);

		}

		finder.setPrisoners(res);

		Finder finderRes = this.save(finder);
		visitor.setFinder(finderRes);
		this.visitorService.save(visitor);
	}
}
