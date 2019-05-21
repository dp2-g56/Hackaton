
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

import services.ConfigurationService;
import services.PrisonerService;
import services.VisitorService;
import domain.Charge;
import domain.Configuration;
import domain.Prisoner;
import domain.Visitor;
import forms.FormObjectVisitor;

@Controller
@RequestMapping("/anonymous")
public class AnonymousController extends AbstractController {

	@Autowired
	private PrisonerService			prisonerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private VisitorService			visitorService;


	public AnonymousController() {
		super();
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

	@RequestMapping(value = "/visitor/create", method = RequestMethod.GET)
	public ModelAndView createAdmin(HttpServletRequest request) {
		try {
			ModelAndView result;

			FormObjectVisitor formObjectVisitor = new FormObjectVisitor();
			formObjectVisitor.setTermsAndConditions(false);

			String imageURL = this.configurationService.getConfiguration().getImageURL();

			request.getSession().setAttribute("imageURL", imageURL);

			result = new ModelAndView("anonymous/visitor/create");

			result = this.createEditModelAndView(formObjectVisitor);

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/anonymous/visitor/create.do");
		}
	}

	@RequestMapping(value = "/visitor/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FormObjectVisitor formObjectVisitor, BindingResult binding) {
		try {
			ModelAndView result;

			Visitor visitor = new Visitor();
			visitor = this.visitorService.create();

			Configuration configuration = this.configurationService.getConfiguration();
			String prefix = configuration.getSpainTelephoneCode();

			// Reconstruccion
			visitor = this.visitorService.reconstruct(formObjectVisitor, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(formObjectVisitor);
			else
				try {

					if (visitor.getPhoneNumber().matches("([0-9]{4,})$"))
						visitor.setPhoneNumber(prefix + visitor.getPhoneNumber());
					this.visitorService.save(visitor);

					result = new ModelAndView("redirect:/security/login.do");

				} catch (Throwable oops) {
					result = this.createEditModelAndView(formObjectVisitor, "company.duplicated.user");

				}
			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/anonymous/visitor/create.do");
		}
	}

	protected ModelAndView createEditModelAndView(FormObjectVisitor formObjectVisitor) {
		ModelAndView result;

		result = this.createEditModelAndView(formObjectVisitor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(FormObjectVisitor formObjectVisitor, String messageCode) {
		ModelAndView result;

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("anonymous/visitor/create");
		result.addObject("formObjectVisitor", formObjectVisitor);
		result.addObject("message", messageCode);
		result.addObject("locale", locale);

		return result;
	}

}
