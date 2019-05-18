package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Request;
import services.RequestService;

@Controller
@RequestMapping("/request/socialWorker")
public class RequestSocialWorkerCotroller extends AbstractController {

	@Autowired
	private RequestService requestService;

	public RequestSocialWorkerCotroller() {
		super();
	}

	// LIST----------------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listRequests(@RequestParam Integer activityId) {
		ModelAndView result;
		try {
			List<Request> requests = this.requestService.getRequestsFromSocialWorker(activityId);

			result = new ModelAndView("request/socialWorker/list");
			result.addObject("requests", requests);
			result.addObject("activityId", activityId);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;

	}

	// Change Status Request ---------------------------------------------------
	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ModelAndView approveRequest(@RequestParam Integer requestId, @RequestParam Integer activityId) {
		ModelAndView result;
		try {
			this.requestService.approveRequest(requestId, activityId);
			result = new ModelAndView("redirect:list.do");
			result.addObject("activityId", activityId);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView rejectRequest(@RequestParam Integer requestId, @RequestParam Integer activityId) {
		ModelAndView result;
		try {
			this.requestService.securityRequestSocialWorker(activityId, requestId);
			result = this.createEditModelAndView(this.requestService.findeOne(requestId));
			result.addObject("activityId", activityId);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView rejectRequestSave(Request requestForm, @RequestParam Integer activityId,
			BindingResult binding) {

		ModelAndView result;
		Request request = this.requestService.reconstructRejectRequest(requestForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(requestForm);
		else
			try {
				this.requestService.rejectRequest(request, activityId);
				result = new ModelAndView("redirect:list.do");
				result.addObject("activityId", activityId);
			} catch (Exception e) {
				result = this.createEditModelAndView(requestForm);
			}
		result.addObject("activityId", activityId);
		return result;
	}

	// DELETE ------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteRequest(Integer requestId, Integer activityId) {
		ModelAndView result;

		try {
			this.requestService.deleteRequestFromSocialWorker(this.requestService.findeOne(requestId));

			result = new ModelAndView("redirect:list.do");

		} catch (Exception e) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("errorDelete", true);
		}
		result.addObject("activityId", activityId);
		return result;
	}

	// CreateEditModelAndView----------------------------------------------------------
	protected ModelAndView createEditModelAndView(Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Request request, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("request/socialWorker/edit");

		result.addObject("request", request);
		result.addObject("message", messageCode);

		return result;

	}

}
