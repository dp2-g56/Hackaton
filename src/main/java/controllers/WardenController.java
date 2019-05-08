
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
@RequestMapping("/warden")
public class WardenController extends AbstractController {

	@Autowired
	private PrisonerService	prisonerService;


	public WardenController() {
		super();
	}

	// Listar Prisioneros liberados
	@RequestMapping(value = "/freePrisoners/list", method = RequestMethod.GET)
	public ModelAndView listFreePrisoners() {

		ModelAndView result;
		List<Prisoner> prisoners;

		prisoners = this.prisonerService.getFreePrisoners();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("anonymous/prisoner/list");
		result.addObject("prisoners", prisoners);
		result.addObject("requestURI", "warden/freePrisoners/list.do");
		result.addObject("locale", locale);
		result.addObject("warden", true);

		return result;
	}

	@RequestMapping(value = "/charge/list", method = RequestMethod.GET)
	public ModelAndView listCharge(@RequestParam int prisonerId) {

		ModelAndView result;

		Prisoner prisoner = this.prisonerService.findOne(prisonerId);

		if (prisoner == null)
			return this.listFreePrisoners();

		List<Charge> charges = prisoner.getCharges();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("anonymous/charge/list");
		result.addObject("charges", charges);
		result.addObject("locale", locale);
		result.addObject("requestURI", "warden/charge/list.do");
		result.addObject("warden", true);

		return result;
	}
}
