
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculumService;
import services.PersonalRecordService;
import services.SocialWorkerService;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.SocialWorker;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private SocialWorkerService		socialWorkerService;
	@Autowired
	private PersonalRecordService	personalRecordService;


	public CurriculumController() {
		super();
	}

	@RequestMapping(value = "/anonymous/show", method = RequestMethod.GET)
	public ModelAndView showCurriculumAnonymous(@RequestParam int socialId) {

		ModelAndView result;
		SocialWorker h = this.socialWorkerService.findOne(socialId);
		Curriculum curriculum = h.getCurriculum();
		Boolean canEdit = false;

		result = new ModelAndView("curriculum/anonymous/show");

		result.addObject("socialId", socialId);
		result.addObject("canEdit", canEdit);
		result.addObject("curriculum", curriculum);
		result.addObject("requestURI", "curriculum/anonymous/show.do?socialId=" + socialId);

		return result;

	}

	@RequestMapping(value = "/socialWorker/show", method = RequestMethod.GET)
	public ModelAndView showCurriculumSocialWorker() {

		ModelAndView result;
		SocialWorker socialWorker = this.socialWorkerService.getSocialWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = socialWorker.getCurriculum();
		Boolean canEdit;

		try {
			Assert.isTrue(socialWorker.getCurriculum().equals(curriculum));
			canEdit = true;
		} catch (Exception e) {
			canEdit = false;
		}

		result = new ModelAndView("curriculum/socialWorker/show");

		result.addObject("socialId", socialWorker.getId());
		result.addObject("canEdit", canEdit);
		result.addObject("curriculum", curriculum);
		result.addObject("requestURI", "curriculum/socialWorker/show.do");

		return result;

	}

	@RequestMapping(value = "/socialWorker/add", method = RequestMethod.GET)
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

		result = this.createEditModelAndView(personalRecord);
		return result;

	}

	@RequestMapping(value = "/socialWorker/editPersonalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView addCurriculum(@Valid PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				SocialWorker socialWorker = this.socialWorkerService.loggedSocialWorker();

				Assert.isTrue(personalRecord.getId() == 0 && socialWorker.getCurriculum() == null);
				Curriculum c = this.curriculumService.create();
				c.setPersonalRecord(personalRecord);
				this.socialWorkerService.addCurriculum(c);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "note.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/socialWorker/editPersonalRecord", method = RequestMethod.POST, params = "edit")
	public ModelAndView editPersonalRecord(@Valid PersonalRecord personalRecord, BindingResult binding) {
		ModelAndView result;
		SocialWorker socialWorker = this.socialWorkerService.getSocialWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum c = socialWorker.getCurriculum();

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				Assert.notNull(c);
				Assert.isTrue(c.getPersonalRecord().getId() == personalRecord.getId());
				c.setPersonalRecord(personalRecord);
				this.curriculumService.save(c);
				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "note.commit.error");
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
