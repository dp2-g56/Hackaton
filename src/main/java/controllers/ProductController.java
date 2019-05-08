
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
import services.ProductService;
import services.SalesManService;
import domain.Prisoner;
import domain.Product;
import domain.SalesMan;

@Controller
@RequestMapping("/product")
public class ProductController extends AbstractController {

	@Autowired
	private ProductService	productService;

	@Autowired
	private SalesManService	salesManService;

	@Autowired
	private PrisonerService	prisonerService;


	public ProductController() {
		super();
	}

	// Listar Visitantes del prisionero logueado
	@RequestMapping(value = "/anonymous/list", method = RequestMethod.GET)
	public ModelAndView listAnonymous() {

		ModelAndView result;
		List<Product> products;

		products = this.productService.getProductsFinalMode();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("anonymous/product/list");
		result.addObject("products", products);
		result.addObject("locale", locale);
		result.addObject("prisoner", false);
		result.addObject("requestURI", "product/anonymous/list.do");

		return result;
	}

	// Listar Visitantes del prisionero logueado
	@RequestMapping(value = "/prisoner/list", method = RequestMethod.GET)
	public ModelAndView listPrisoner(@RequestParam int salesmanId) {

		ModelAndView result;
		List<Product> products;

		SalesMan salesMan = this.salesManService.findOne(salesmanId);

		if (salesMan == null)
			return this.listSalesManPrisoner();

		products = this.productService.getProductsFinalModeWithStockBySalesMan(salesmanId);

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		Prisoner loggedPrisoner = this.prisonerService.loggedPrisoner();
		int points = loggedPrisoner.getPoints();

		result = new ModelAndView("anonymous/product/list");
		result.addObject("products", products);
		result.addObject("points", points);
		result.addObject("locale", locale);
		result.addObject("prisoner", true);
		result.addObject("requestURI", "product/prisoner/list.do");

		return result;
	}
	// Listar Visitantes del prisionero logueado
	@RequestMapping(value = "/salesman/prisoner/list", method = RequestMethod.GET)
	public ModelAndView listSalesManPrisoner() {

		ModelAndView result;
		List<SalesMan> salesMen;

		salesMen = this.salesManService.findAll();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("prisoner/salesMen/list");
		result.addObject("salesMen", salesMen);
		result.addObject("locale", locale);
		result.addObject("requestURI", "product/salesman/prisoner/list.do");

		return result;
	}

}
