package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.SocialWorker;
import services.ActivityService;
import services.PrisonerService;
import services.SocialWorkerService;

@Controller
@RequestMapping("/activity/socialworker")
public class ActivityController extends AbstractController {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private SocialWorkerService socialWorkerService;

	public ActivityController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Activity> activities;

		SocialWorker sw = this.socialWorkerService.loggedSocialWorker();

		activities = sw.getActivities();

		result = new ModelAndView("activity/socialworker/list");
		result.addObject("activities", activities);
		result.addObject("requestURI", "activity/socialworker/list.do");

		return result;
	}

}
