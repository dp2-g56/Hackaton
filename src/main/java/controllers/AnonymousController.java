
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrisonerService;
import domain.Charge;
import domain.Prisoner;

@Controller
@RequestMapping("/anonymous")
public class AnonymousController extends AbstractController {

	@Autowired
	private PrisonerService	prisonerService;


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

		prisoners = this.prisonerService.findAll();

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

}
