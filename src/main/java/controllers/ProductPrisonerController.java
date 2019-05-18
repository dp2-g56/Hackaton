package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Prisoner;
import domain.Product;
import services.PrisonerService;
import services.ProductService;

@Controller
@RequestMapping("/product/prisoner")
public class ProductPrisonerController extends AbstractController {

	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private ProductService productService;

	public ProductPrisonerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listProducts() {
		ModelAndView result;

		try {
			Prisoner prisoner = this.prisonerService.securityAndPrisoner();
			List<Product> products = this.productService.getProductsFinalModeWithStock();

			result = this.createEditModelAndView("prisoner/store", products, prisoner.getPoints());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	private ModelAndView createEditModelAndView(String tiles, List<Product> products, Integer points) {
		ModelAndView result = new ModelAndView(tiles);

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result.addObject("products", products);
		result.addObject("points", points);
		result.addObject("prisoner", true);
		result.addObject("store", true);
		result.addObject("locale", locale);

		return result;
	}

}
