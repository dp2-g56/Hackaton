
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.PersonalRecordService;
import services.SocialWorkerService;
import domain.Actor;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.SocialWorker;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	@Autowired
	private SocialWorkerService		socialWorkerService;
	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private ActorService			actorService;


	public CurriculumController() {
		super();
	}

	@RequestMapping(value = "/socialWorker/show", method = RequestMethod.GET)
	public ModelAndView showCurriculumSocialWorker() {

		ModelAndView result;
		SocialWorker socialWorker = this.socialWorkerService.getSocialWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = socialWorker.getCurriculum();

		if (curriculum == null) {
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Actor logguedActor = new Actor();

			result = new ModelAndView("authenticated/showProfile");

			logguedActor = this.actorService.getActorByUsername(userAccount.getUsername());

			result.addObject("curriculum", null);

			result.addObject("actor", logguedActor);
			result.addObject("requestURI", "authenticated/showProfile.do");
		} else {

			result = new ModelAndView("curriculum/socialWorker/show");

			result.addObject("socialId", socialWorker.getId());
			result.addObject("curriculum", curriculum);
			result.addObject("requestURI", "curriculum/socialWorker/show.do");
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
	public ModelAndView edit(@RequestParam int personalRecordId) {

		ModelAndView result;
		PersonalRecord personalRecord = this.personalRecordService.findOne(personalRecordId);
		SocialWorker socialWorker = this.socialWorkerService.getSocialWorkerByUsername(LoginService.getPrincipal().getUsername());

		if (personalRecord == null || socialWorker.getCurriculum() == null || socialWorker.getCurriculum().getPersonalRecord().getId() != personalRecordId) {
			result = new ModelAndView("redirect:show.do");
		} else {

			result = this.createEditModelAndView(personalRecord);
		}
		return result;

	}

	@RequestMapping(value = "/socialWorker/editPersonalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView addCurriculum(@Valid PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(personalRecord);
		} else {
			try {
				if (personalRecord.getId() == 0) {
					this.socialWorkerService.addCurriculum(personalRecord);
				} else {
					this.socialWorkerService.updateCurriculum(personalRecord);
				}
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "commit.error");
			}
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
