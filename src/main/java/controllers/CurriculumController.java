
package controllers;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.SocialWorker;
import services.ActorService;
import services.CurriculumService;
import services.PersonalRecordService;
import services.SocialWorkerService;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	@Autowired
	private SocialWorkerService socialWorkerService;
	@Autowired
	private PersonalRecordService personalRecordService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CurriculumService curriculumService;

	public CurriculumController() {
		super();
	}

	@RequestMapping(value = "/socialWorker/show", method = RequestMethod.GET)
	public ModelAndView showCurriculumSocialWorker() {
		ModelAndView result;

		try {
			try {
				Curriculum curriculum = this.curriculumService.getCurriculumOfLoggedSocialWorker();

				result = new ModelAndView("curriculum/socialWorker/show");

				SocialWorker socialWorker = this.socialWorkerService.loggedSocialWorker();
				result.addObject("socialId", socialWorker.getId());
				result.addObject("curriculum", curriculum);
				result.addObject("requestURI", "curriculum/socialWorker/show.do");
			} catch (Throwable oops) {
				Actor actor = this.actorService.loggedActor();

				result = new ModelAndView("authenticated/showProfile");

				result.addObject("curriculum", null);
				result.addObject("actor", actor);
			}
		} catch (Throwable oops2) {
			result = new ModelAndView("redirect:/");
		}

		return result;

	}

	@RequestMapping(value = "/socialWorker/register", method = RequestMethod.GET)
	public ModelAndView addCurriculum() {

		ModelAndView result;
		PersonalRecord personalRecord = this.personalRecordService.create();

		result = this.createEditModelAndView(personalRecord);
		return result;

	}

	@RequestMapping(value = "/socialWorker/editPersonalRecord", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) String personalRecordId) {
		ModelAndView result;

		try {
			Assert.isTrue(StringUtils.isNumeric(personalRecordId));
			int personalRecordIdInt = Integer.parseInt(personalRecordId);

			PersonalRecord personalRecord = this.personalRecordService
					.getPersonalRecordAsSocialWorker(personalRecordIdInt);

			result = this.createEditModelAndView(personalRecord);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:show.do");
		}

		return result;

	}

	@RequestMapping(value = "/socialWorker/editPersonalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView addCurriculum(@Valid PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				if (personalRecord.getId() == 0)
					this.socialWorkerService.addCurriculum(personalRecord);
				else
					this.socialWorkerService.updateCurriculum(personalRecord);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/socialWorker/editPersonalRecord");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
