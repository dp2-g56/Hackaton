
package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Charge;
import domain.Prisoner;
import domain.SocialWorker;
import forms.FormObjectSocialWorker;
import services.ConfigurationService;
import services.PrisonerService;
import services.SocialWorkerService;

@Controller
@RequestMapping("/anonymous")
public class AnonymousController extends AbstractController {

	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private SocialWorkerService socialWorkerService;

	public AnonymousController() {
		super();
	}

	// -------------------------------------------------------------------
	// ---------------------------REGISTER--------------------------------

	@RequestMapping(value = "/socialWorker/create", method = RequestMethod.GET)
	public ModelAndView registerAsSocialWorker() {
		ModelAndView result;
		try {
			FormObjectSocialWorker formObjectSocialWorker = new FormObjectSocialWorker();
			formObjectSocialWorker.setTermsAndConditions(false);

			result = this.createEditModelAndView(formObjectSocialWorker);

		} catch (Exception e) {

			result = new ModelAndView("redirect:/anonymous/socialWorker/create.do");
		}

		return result;

	}

	@RequestMapping(value = "/socialWorker/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FormObjectSocialWorker formObjectSocialWorker, BindingResult binding) {
		try {
			ModelAndView result;

			SocialWorker socialWorker = new SocialWorker();
			socialWorker = this.socialWorkerService.create();
			// Reconstruccion
			socialWorker = this.socialWorkerService.reconstruct(formObjectSocialWorker, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(formObjectSocialWorker);
			else
				try {
					this.socialWorkerService.saveSocialWorker(socialWorker);

					result = new ModelAndView("redirect:/security/login.do");

				} catch (Throwable oops) {
					result = this.createEditModelAndView(formObjectSocialWorker, "socialWorker.duplicated.user");

				}
			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/anonymous/socialWorker/create.do");
		}
	}

	protected ModelAndView createEditModelAndView(FormObjectSocialWorker formObjectSocialWorker) {
		ModelAndView result;

		result = this.createEditModelAndView(formObjectSocialWorker, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(FormObjectSocialWorker formObjectSocialWorker, String messageCode) {
		ModelAndView result;

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("anonymous/socialWorker/create");
		result.addObject("formObjectSocialWorker", formObjectSocialWorker);
		result.addObject("message", messageCode);
		result.addObject("locale", locale);

		return result;
	}

	// -------------------------------------------------------------------
	// ---------------------------LIST------------------------------------

	// Listar todos los prisioneros del sistema
	@RequestMapping(value = "/prisoner/list", method = RequestMethod.GET)
	public ModelAndView listPrisoner() {

		ModelAndView result;
		List<Prisoner> prisoners;

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		prisoners = this.prisonerService.getIncarceratedPrisoners();

		result = new ModelAndView("anonymous/prisoner/list");
		result.addObject("prisoners", prisoners);
		result.addObject("locale", locale);
		result.addObject("requestURI", "anonymous/prisoner/list.do");

		return result;
	}

	// Listar todos los cargos de un prisionero
	@RequestMapping(value = "/charge/list", method = RequestMethod.GET)
	public ModelAndView listCharge(@RequestParam int prisonerId) {

		ModelAndView result;

		Prisoner prisoner = this.prisonerService.findOne(prisonerId);

		if (prisoner == null)
			return this.listPrisoner();

		List<Charge> charges = prisoner.getCharges();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("anonymous/charge/list");
		result.addObject("charges", charges);
		result.addObject("locale", locale);
		result.addObject("requestURI", "anonymous/charge/list.do");
		result.addObject("warden", false);

		return result;
	}

	@RequestMapping(value = "/termsAndConditionsEN", method = RequestMethod.GET)
	public ModelAndView listEN(HttpServletRequest request) {
		try {
			ModelAndView result;

			String imageURL = this.configurationService.getConfiguration().getImageURL();

			request.getSession().setAttribute("imageURL", imageURL);
			result = new ModelAndView("termsAndConditionsEN");

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}

	@RequestMapping(value = "/termsAndConditionsES", method = RequestMethod.GET)
	public ModelAndView listES(HttpServletRequest request) {
		try {
			ModelAndView result;

			String imageURL = this.configurationService.getConfiguration().getImageURL();

			request.getSession().setAttribute("imageURL", imageURL);
			result = new ModelAndView("termsAndConditionsES");

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}
}
