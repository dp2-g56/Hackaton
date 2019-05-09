
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculumService;
import services.EducationRecordService;
import services.SocialWorkerService;
import domain.Curriculum;
import domain.EducationRecord;
import domain.SocialWorker;

@Controller
@RequestMapping("/curriculum")
public class EducationRecordController extends AbstractController {

	@Autowired
	private EducationRecordService	educationRecordService;
	@Autowired
	private SocialWorkerService		socialWorkerService;
	@Autowired
	private CurriculumService		curriculumService;


	//Constructor
	public EducationRecordController() {
		super();
	}

	@RequestMapping(value = "/socialWorker/addEducationRecord", method = RequestMethod.GET)
	public ModelAndView addEducationRecord() {

		ModelAndView result;

		SocialWorker socialWorker = this.socialWorkerService.getSocialWorkerByUsername(LoginService.getPrincipal().getUsername());
		Curriculum curriculum = socialWorker.getCurriculum();

		if (curriculum == null) {
			result = new ModelAndView("redirect:show.do");
		} else {
			EducationRecord educationRecord = this.educationRecordService.create();

			result = this.createEditModelAndView(educationRecord);
		}
		return result;

	}

	@RequestMapping(value = "/socialWorker/editEducationRecord", method = RequestMethod.GET)
	public ModelAndView editEducationRecord(@RequestParam int educationRecordId) {

		ModelAndView result;
		EducationRecord educationRecord = this.educationRecordService.findOne(educationRecordId);
		SocialWorker socialWorker = this.socialWorkerService.getSocialWorkerByUsername(LoginService.getPrincipal().getUsername());

		if (educationRecord == null || socialWorker.getCurriculum() == null || !socialWorker.getCurriculum().getEducationRecords().contains(educationRecord)) {
			result = new ModelAndView("redirect:show.do");
		} else {

			result = this.createEditModelAndView(educationRecord);
		}
		return result;

	}

	@RequestMapping(value = "/socialWorker/editEducationRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid EducationRecord educationRecord, BindingResult binding) {
		ModelAndView result;

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		if (educationRecord.getEndDateStudy() != null && educationRecord.getStartDateStudy() != null && educationRecord.getStartDateStudy().after(educationRecord.getEndDateStudy())) {
			if (locale.contains("ES")) {
				binding.addError(new FieldError("positionData", "startDate", educationRecord.getStartDateStudy(), false, null, null, "La fecha de fin no puede ser anterior a la de inicio"));
			} else {
				binding.addError(new FieldError("positionData", "startDate", educationRecord.getStartDateStudy(), false, null, null, "The end date can not be before the start date"));
			}
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(educationRecord);
		} else {
			try {
				if (educationRecord.getId() == 0) {
					this.curriculumService.addEducationRecord(educationRecord);
				} else {
					this.curriculumService.updateEducationRecord(educationRecord);
				}

				result = new ModelAndView("redirect:show.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "note.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/socialWorker/editEducationRecord", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid EducationRecord educationRecord, BindingResult binding) {
		ModelAndView result;

		try {
			this.curriculumService.deleteEducationRecord(educationRecord);

			result = new ModelAndView("redirect:show.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "note.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(EducationRecord educationRecord, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/socialWorker/editEducationRecord");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", messageCode);

		return result;
	}
}