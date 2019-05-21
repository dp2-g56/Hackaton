
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import services.PrisonerService;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box/actor")
public class BoxController extends AbstractController {

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private PrisonerService	prisonerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		this.actorService.loggedAsActor();

		ModelAndView result;
		List<Box> boxes = new ArrayList<>();

		boxes = this.boxService.getActorBoxes();
		result = new ModelAndView("box/actor/list");

		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/actor/list.do");

		return result;

	}
	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		this.actorService.loggedAsActor();
		ModelAndView result;
		Box box;

		box = this.boxService.create();
		result = this.createEditModelAndView(box);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Box box, BindingResult binding) {
		this.actorService.loggedAsActor();
		ModelAndView result;

		box = this.boxService.reconstruct(box, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(box);
		else
			try {
				this.boxService.updateBox(box);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(box, "message.commit.error");
			}
		return result;
	}

	//Create
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int boxId) {
		this.actorService.loggedAsActor();
		ModelAndView result;
		Box box;

		box = this.boxService.findOne(boxId);

		Assert.notNull(box);
		result = this.createEditModelAndView(box);

		return result;
	}

	//Save
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int rowId) {
		this.actorService.loggedAsActor();
		ModelAndView result;
		Actor actor = this.actorService.loggedActor();

		Box box = this.boxService.findOne(rowId);

		if (!actor.getBoxes().contains(box))
			return this.list();

		try {
			this.boxService.deleteBox(box);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(box, "box.commit.error");

		}
		return result;
	}
	protected ModelAndView createEditModelAndView(Box box) {
		ModelAndView result;

		result = this.createEditModelAndView(box, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Box box, String messageCode) {
		ModelAndView result;

		List<Box> boxes = new ArrayList<Box>();

		boxes = this.boxService.getActorBoxes();

		result = new ModelAndView("box/actor/create");
		result.addObject("box", box);
		result.addObject("boxes", boxes);
		result.addObject("message", messageCode);

		return result;
	}

}
