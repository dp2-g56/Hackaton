
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Prisoner;
import domain.SalesMan;
import domain.SocialWorker;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.PrisonerService;
import services.SalesManService;
import services.SocialWorkerService;

@Controller
@RequestMapping("/authenticated")
public class SocialProfileController extends AbstractController {

	@Autowired
	private ActorService actorService;
	@Autowired
	private SocialWorkerService socialWorkerService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SalesManService salesManService;

	// -------------------------------------------------------------------
	// ---------------------------LIST
	// BROTHERHOOD------------------------------------
	@RequestMapping(value = "/showProfile", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor logguedActor = new Actor();

		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("authenticated/showProfile");

		logguedActor = this.actorService.getActorByUsername(userAccount.getUsername());

		if (authorities.get(0).toString().equals("SOCIALWORKER")) {
			SocialWorker socialWorker = this.socialWorkerService.findOne(logguedActor.getId());

			result.addObject("curriculum", socialWorker.getCurriculum());
		}

		if (authorities.get(0).toString().equals("PRISONER")) {
			Prisoner prisoner = this.prisonerService.findOne(logguedActor.getId());

			result.addObject("prisoner", prisoner);
			result.addObject("size", prisoner.getCharges().size());
		}

		if (authorities.get(0).toString().equals("SALESMAN")) {
			SalesMan salesman = this.salesManService.findOne(logguedActor.getId());

			result.addObject("salesman", salesman);
		}

		result.addObject("actor", logguedActor);
		result.addObject("requestURI", "authenticated/showProfile.do");
		result.addObject("locale", locale);

		return result;
	}

}
