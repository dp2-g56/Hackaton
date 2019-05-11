
package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Charge;
import domain.Prisoner;
import forms.FormObjectPrisoner;
import services.ChargeService;
import services.PrisonerService;
import services.WardenService;

@Controller
@RequestMapping("/prisoner/warden")
public class PrisonerWardenController extends AbstractController {

	@Autowired
	private ChargeService chargeService;

	@Autowired
	private WardenService wardenService;

	@Autowired
	private PrisonerService prisonerService;

	public PrisonerWardenController() {
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;

		try {
			this.wardenService.securityAndWarden();
			FormObjectPrisoner formPrisoner = new FormObjectPrisoner();

			String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

			List<Charge> finalCharges = this.chargeService.getFinalCharges();

			result = new ModelAndView("warden/registerPrisoner");
			result.addObject("formPrisoner", formPrisoner);
			result.addObject("locale", locale);
			result.addObject("finalCharges", finalCharges);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("formPrisoner") @Valid FormObjectPrisoner formPrisoner,
			BindingResult binding) {
		ModelAndView result = null;

		Prisoner prisoner = new Prisoner();
		prisoner = this.prisonerService.reconstruct(formPrisoner, binding);

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
		List<Charge> finalCharges = this.chargeService.getFinalCharges();

		if (binding.hasErrors()) {
			result = new ModelAndView("warden/registerPrisoner");
			result.addObject("formPrisoner", formPrisoner);
			result.addObject("locale", locale);
			result.addObject("finalCharges", finalCharges);
		} else
			try {
				this.prisonerService.savePrisoner(prisoner);
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = new ModelAndView("warden/registerPrisoner");
				result.addObject("formPrisoner", formPrisoner);
				result.addObject("locale", locale);
				result.addObject("finalCharges", finalCharges);
				result.addObject("message", "warden.register.commit.error");
			}

		return result;
	}
}
