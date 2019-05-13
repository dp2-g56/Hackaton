package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Charge;
import domain.Finder;
import domain.Visitor;
import services.ChargeService;
import services.FinderService;
import services.VisitorService;

@Controller
@RequestMapping("/finder/visitor")
public class FinderVisitorController extends AbstractController {

	@Autowired
	private FinderService finderService;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private ChargeService chargeService;

	// Constructors -----------------------------------------------------------

	public FinderVisitorController() {
		super();
	}

	// List --------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Visitor visitor = this.visitorService.loggedVisitor();

		ModelAndView result = new ModelAndView("finder/visitor/list");

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result.addObject("prisoners", this.finderService.getResults(visitor.getFinder()));
		result.addObject("locale", locale);

		return result;

	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editFinder() {
		ModelAndView res;

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		try {

			List<Charge> charges = this.chargeService.findAll();

			Visitor visitor = this.visitorService.loggedVisitor();

			List<String> values = new ArrayList<>();
			List<String> names = new ArrayList<>();

			names.add("");
			values.add("");

			Finder finder = visitor.getFinder();
			if (locale == "EN")
				for (int i = 0; i < charges.size(); i++) {
					values.add(charges.get(i).getTitleEnglish());
					names.add(charges.get(i).getTitleEnglish());
				}
			else
				for (int i = 0; i < charges.size(); i++) {
					values.add(charges.get(i).getTitleEnglish());
					names.add(charges.get(i).getTitleSpanish());
				}

			Assert.notNull(finder);
			res = this.createEditModelAndView(finder);
			res.addObject("values", values);
			res.addObject("names", names);
			res.addObject("sizeOfList", values.size());
		} catch (Throwable oops) {
			res = new ModelAndView("redirect:list.do");
		}

		res.addObject("locale", locale);
		return res;

	}

	// Save------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finderForm, BindingResult binding) {
		ModelAndView result;

		Finder finder = this.finderService.reconstruct(finderForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(finderForm);
		else
			try {
				this.finderService.filter(finder);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				try {
					result = this.createEditModelAndView(finder, "finder.commit.error");
				} catch (Throwable oops2) {
					result = new ModelAndView("redirect:/");
				}
			}
		return result;
	}

	// CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Finder finder, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/visitor/edit");

		result.addObject("finder", finder);
		result.addObject("message", messageCode);

		return result;

	}

}
