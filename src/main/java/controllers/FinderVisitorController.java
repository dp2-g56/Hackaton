package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Visitor;
import services.FinderService;
import services.VisitorService;

@Controller
@RequestMapping("/finder/visitor")
public class FinderVisitorController extends AbstractController {

	@Autowired
	private FinderService finderService;

	@Autowired
	private VisitorService visitorService;

	// Constructors -----------------------------------------------------------

	public FinderVisitorController() {
		super();
	}

	// List --------------------------------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {

		Visitor visitor = this.visitorService.getLoggedVisitor();

		ModelAndView result = new ModelAndView("finder/visitor/list");

		result.addObject("prisoners", this.finderService.getResults(visitor.getFinder()));

		return result;

	}

}
