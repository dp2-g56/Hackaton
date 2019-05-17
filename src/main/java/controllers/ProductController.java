
package controllers;

import java.util.List;

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
import services.ProductService;
import services.SalesManService;
import domain.Configuration;
import domain.Prisoner;
import domain.Product;
import domain.SalesMan;
import domain.TypeProduct;

@Controller
@RequestMapping("/product")
public class ProductController extends AbstractController {

	@Autowired
	private ProductService			productService;

	@Autowired
	private SalesManService			salesManService;

	@Autowired
	private PrisonerService			prisonerService;

	@Autowired
	private ConfigurationService	configurationService;


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

		if (salesMan == null) {
			return this.listSalesManPrisoner();
		}

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

	@RequestMapping(value = "/salesman/list", method = RequestMethod.GET)
	public ModelAndView listSalesman() {

		ModelAndView result;
		List<Product> products;

		SalesMan salesman = this.salesManService.loggedSalesMan();

		products = salesman.getProducts();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("product/salesman/list");
		result.addObject("products", products);
		result.addObject("locale", locale);
		result.addObject("salesman", true);
		result.addObject("requestURI", "product/salesman/list.do");

		return result;
	}

	@RequestMapping(value = "/salesman/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		Product product = this.productService.create();

		result = this.createEditModelAndView(product);

		return result;
	}

	@RequestMapping(value = "/salesman/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int productId) {

		ModelAndView result;

		Product product = this.productService.findOne(productId);
		SalesMan salesman = this.salesManService.loggedSalesMan();

		if (product == null || salesman == null || !salesman.getProducts().contains(product)) {
			result = new ModelAndView("redirect:list.do");
		} else {
			result = this.createEditModelAndView(product);
		}
		return result;
	}

	@RequestMapping(value = "/salesman/restock", method = RequestMethod.GET)
	public ModelAndView restock(@RequestParam int productId) {

		ModelAndView result;

		Product product = this.productService.findOne(productId);
		SalesMan salesman = this.salesManService.loggedSalesMan();

		if (product == null || salesman == null || !salesman.getProducts().contains(product)) {
			result = new ModelAndView("redirect:list.do");
		} else {
			result = this.restockModelAndView(product);
		}
		return result;
	}

	@RequestMapping(value = "/salesman/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Product product, BindingResult binding) {
		ModelAndView result;

		Product pro = this.productService.reconstruct(product, binding);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(pro);
		} else {
			try {
				if (product.getId() == 0) {
					this.productService.addProduct(pro);
				} else {
					this.productService.updateProduct(pro);
				}

				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(product, "commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/salesman/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Product product, BindingResult binding) {
		ModelAndView result;

		Product pro = this.productService.findOne(product.getId());

		try {
			this.productService.delete(pro);
			result = new ModelAndView("redirect:list.do");

		} catch (Throwable oops) {
			result = this.createEditModelAndView(product, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/salesman/restock", method = RequestMethod.POST, params = "save")
	public ModelAndView restock(Product product, BindingResult binding) {
		ModelAndView result;

		Product pro = this.productService.reconstruct(product, binding);

		if (binding.hasErrors()) {
			result = this.restockModelAndView(pro);
		} else {
			try {

				this.productService.updateProduct(pro);

				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				result = this.restockModelAndView(product, "commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Product product) {
		ModelAndView result;

		result = this.createEditModelAndView(product, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Product product, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("product/salesman/edit");
		result.addObject("product", product);
		result.addObject("message", messageCode);

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
		Configuration configuration = this.configurationService.getConfiguration();
		List<TypeProduct> productType = configuration.getTypeProducts();

		result.addObject("locale", locale);
		result.addObject("productType", productType);

		return result;
	}

	protected ModelAndView restockModelAndView(Product product) {
		ModelAndView result;

		result = this.restockModelAndView(product, null);

		return result;
	}

	protected ModelAndView restockModelAndView(Product product, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("product/salesman/restock");
		result.addObject("product", product);
		result.addObject("message", messageCode);

		return result;
	}

}
