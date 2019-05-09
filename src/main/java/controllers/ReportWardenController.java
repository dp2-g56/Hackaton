
package controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Prisoner;
import domain.Report;
import services.ReportService;

@Controller
@RequestMapping("/report/warden")
public class ReportWardenController extends AbstractController {

	@Autowired
	private ReportService reportService;

	public ReportWardenController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		try {
			Map<Report, Prisoner> reports = this.reportService.getReportsAsWarden();

			result = new ModelAndView("warden/report");
			result.addObject("reportsAndPrisoner", reports);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}
}
