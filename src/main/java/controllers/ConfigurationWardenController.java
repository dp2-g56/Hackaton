package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Configuration;
import domain.TypeProduct;
import services.ConfigurationService;

@Controller
@RequestMapping("/configuration/warden")
public class ConfigurationWardenController extends AbstractController {

	@Autowired
	private ConfigurationService configurationService;

	// Constructors -----------------------------------------------------------

	public ConfigurationWardenController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView configurationList() {
		try {
			ModelAndView result;
			Configuration configuration;

			configuration = this.configurationService.getConfiguration();

			result = new ModelAndView("warden/configuration");
			result.addObject("configuration", configuration);
			result.addObject("requestURI", "configuration/warden/list.do");

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}

	@RequestMapping(value = "/listSpam", method = RequestMethod.GET)
	public ModelAndView spamList() {
		try {
			ModelAndView result;
			Configuration configuration;
			List<String> spamWords;

			configuration = this.configurationService.getConfiguration();
			spamWords = configuration.getSpamWords();

			result = new ModelAndView("spamWords/configuration");
			result.addObject("configuration", configuration);
			result.addObject("spamWords", spamWords);
			result.addObject("requestURI", "configuration/warden/listSpam.do");

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}

	@RequestMapping(value = "/listTypeProd", method = RequestMethod.GET)
	public ModelAndView typesList() {
		try {
			ModelAndView result;
			Configuration configuration;
			List<TypeProduct> types;

			configuration = this.configurationService.getConfiguration();
			types = configuration.getTypeProducts();

			result = new ModelAndView("listTypeProd/configuration");
			result.addObject("configuration", configuration);
			result.addObject("types", types);
			result.addObject("requestURI", "configuration/warden/listTypeProd.do");

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView configurationEdit() {
		try {
			ModelAndView result;

			Configuration configuration = this.configurationService.getConfiguration();

			result = new ModelAndView("warden/editConfiguration");
			result.addObject("configuration", configuration);

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Configuration configuration, BindingResult binding) {
		try {
			ModelAndView result;

			Configuration configurationR = this.configurationService.reconstruct(configuration, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(configuration);
			else
				try {
					this.configurationService.save(configurationR);
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = this.createEditModelAndView(configuration, "warden.commit.error");
				}

			return result;
		} catch (Throwable oops) {
			return new ModelAndView("redirect:/");
		}
	}

	protected ModelAndView createEditModelAndView(Configuration configuration) {
		ModelAndView result = new ModelAndView("warden/editConfiguration");
		result.addObject("configuration", configuration);
		return result;
	}

	protected ModelAndView createEditModelAndView(Configuration configuration, String message) {
		ModelAndView result = new ModelAndView("warden/editConfiguration");
		result.addObject("configuration", configuration);
		result.addObject("message", message);
		return result;
	}

}
