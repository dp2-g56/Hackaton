
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
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/listSuspects", method = RequestMethod.GET)
	public ModelAndView listSuspects() {
		ModelAndView result;

		try {
			List<Prisoner> prisoners = this.prisonerService.getSuspectPrisoners();

			result = new ModelAndView("prisoner/warden/listSuspects");
			result.addObject("prisoners", prisoners);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/isolate", method = RequestMethod.GET)
	public ModelAndView isolate(@RequestParam int prisonerId) {
		ModelAndView result;

		try {

			Prisoner prisoner = this.prisonerService.findOne(prisonerId);
			List<Prisoner> prisoners = this.prisonerService.getSuspectPrisoners();

			if (prisoner == null || !prisoners.contains(prisoner))
				return this.listSuspects();
			else {
				this.wardenService.isolatePrisoner(prisoner);
				return this.listSuspects();
			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/listSuspectCharges", method = RequestMethod.GET)
	public ModelAndView listCharge(@RequestParam int prisonerId) {

		ModelAndView result;

		try {

			Prisoner prisoner = this.prisonerService.findOne(prisonerId);
			List<Prisoner> prisoners = this.prisonerService.getSuspectPrisoners();

			if (prisoner == null || !prisoners.contains(prisoner))
				return this.listSuspects();

			List<Charge> charges = prisoner.getCharges();

			String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

			result = new ModelAndView("anonymous/charge/list");
			result.addObject("charges", charges);
			result.addObject("locale", locale);
			result.addObject("requestURI", "prisoner/warden/listSuspectCharges.do");
			result.addObject("warden", true);
			result.addObject("suspect", true);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPrisoner(@RequestParam int prisonerId) {
		ModelAndView result;

		try {
			Prisoner prisoner = this.wardenService.getPrisonerAsWarden(prisonerId);

			result = new ModelAndView("warden/editPrisoner");
			result.addObject("prisoner", prisoner);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editPrisoner(Prisoner prisoner, BindingResult binding) {
		ModelAndView result;

		Prisoner prisonerReconstructed = new Prisoner();
		prisonerReconstructed = this.prisonerService.reconstruct(prisoner, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("warden/editPrisoner");
			result.addObject("prisoner", prisonerReconstructed);
		} else
			try {
				this.prisonerService.savePrisoner(prisonerReconstructed);

				result = new ModelAndView("redirect:/anonymous/prisoner/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("warden/editPrisoner");
				result.addObject("prisoner", prisonerReconstructed);
				result.addObject("message", "warden.save.commit.error");
			}

		return result;
	}
}
