package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

	@Autowired
	private ConfigurationService configurationService;

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

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() > 0);
		Assert.isTrue(visitor.getFinder().getId() == finder.getId());

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

	public List<Prisoner> getResults(Finder finder) {
		Visitor visitor = this.visitorService.securityAndVisitor();

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() > 0);
		Assert.isTrue(visitor.getFinder().getId() == finder.getId());

		List<Prisoner> res = new ArrayList<>();
		List<Prisoner> prisoners = finder.getPrisoners();

		if (finder.getLastEdit() != null) {

			// Current Date
			Date currentDate = new Date();

			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(currentDate);

			// LastEdit Finder
			Date lastEdit = finder.getLastEdit();

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(lastEdit);

			Integer time = this.configurationService.getConfiguration().getTimeFinderActivities();

			calendar2.add(Calendar.HOUR, time);

			if (calendar2.after(calendar1)) {
				// TODO Hay que cambiarlo
				Integer numFinderResult = this.configurationService.getConfiguration().getMaxFinderResults();

				if (prisoners.size() > numFinderResult)
					for (int i = 0; i < numFinderResult; i++)
						res.add(prisoners.get(i));
				else
					res = prisoners;
			}
		}

		return res;

	}
}
