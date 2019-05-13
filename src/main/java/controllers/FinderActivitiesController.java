package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.FinderActivities;
import domain.Prisoner;
import services.ActivityService;
import services.FinderActivitiesService;
import services.PrisonerService;

@Controller
@RequestMapping("/finderActivities/prisoner")
public class FinderActivitiesController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private FinderActivitiesService finderActivitiesService;

	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private ActivityService activityService;

	// Constructors -----------------------------------------------------------
	public FinderActivitiesController() {
		super();
	}

	// List --------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Prisoner prisoner = this.prisonerService.loggedPrisoner();

		ModelAndView result = new ModelAndView("finderActivities/prisoner/list");

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		List<Activity> activities = this.finderActivitiesService.getResults(prisoner.getFinderActivities());

		result.addObject("activities", activities);
		result.addObject("locale", locale);
		result.addObject("map", this.activityService.getNumberOfApprobedRequestPerActivity(activities));

		return result;

	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editFinder() {
		ModelAndView res;

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		try {

			Prisoner prisoner = this.prisonerService.loggedPrisoner();

			FinderActivities finder = prisoner.getFinderActivities();

			Assert.notNull(finder);
			res = this.createEditModelAndView(finder);
		} catch (Throwable oops) {
			res = new ModelAndView("redirect:list.do");
		}

		res.addObject("locale", locale);
		return res;

	}

	// Save------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FinderActivities finderForm, BindingResult binding) {
		ModelAndView result;

		FinderActivities finder = this.finderActivitiesService.reconstruct(finderForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(finderForm);
		else
			try {
				this.finderActivitiesService.filter(finder);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				try {
					result = this.createEditModelAndView(finder, "finder.commit.error");
				} catch (Throwable oops2) {
					result = new ModelAndView("redirect:/");
				}
			}
		return result;
	}

	// CreateEditModelAndView----------------------------------------------------------
	protected ModelAndView createEditModelAndView(FinderActivities finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(FinderActivities finder, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finderActivities/prisoner/edit");

		result.addObject("finderActivities", finder);
		result.addObject("message", messageCode);

		return result;

	}

}
