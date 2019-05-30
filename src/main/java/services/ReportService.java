
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import domain.Prisoner;
import domain.Report;
import domain.Visit;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;

	@Autowired
	private GuardService		guardService;

	@Autowired
	private VisitService		visitService;

	@Autowired
	private WardenService		wardenService;

	@Autowired
	private Validator			validator;


	public void saveReport(Report report, int visitId) {
		this.guardService.loggedAsGuard();
		Visit visit = this.visitService.findOne(visitId);

		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.YEAR, -10);
		Date date1 = c1.getTime();

		Assert.notNull(visit);
		visit.setDate(date1);

		Report saved = this.reportRepository.save(report);

		visit.setReport(saved);

	}

	public void delete(int reportId) {
		this.reportRepository.delete(reportId);
	}

	// RECONSTRUCT
	public Report reconstruct(Report report, BindingResult binding) {
		Report result = new Report();

		result = report;

		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.YEAR, -10);
		Date date1 = c1.getTime();

		result.setDate(date1);

		this.validator.validate(result, binding);
		return result;
	}

	public Map<Report, Prisoner> getReportsAsWarden() {
		this.wardenService.loggedAsWarden();

		Map<Report, Prisoner> reportsAndPrisoner = new HashMap<>();

		List<Report> reports = this.reportRepository.findAll();

		for (Report r : reports) {
			Prisoner prisoner = this.reportRepository.getPrisonerOfReport(r.getId());
			reportsAndPrisoner.put(r, prisoner);
		}

		return reportsAndPrisoner;
	}

}
