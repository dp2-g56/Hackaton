
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.SocialWorkerService;
import domain.Actor;
import domain.SocialWorker;

@Controller
@RequestMapping("/authenticated")
public class SocialProfileController extends AbstractController {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private SocialWorkerService	socialWorkerService;


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

		result = new ModelAndView("authenticated/showProfile");

		logguedActor = this.actorService.getActorByUsername(userAccount.getUsername());

		if (authorities.get(0).toString().equals("SOCIALWORKER")) {
			SocialWorker socialWorker = this.socialWorkerService.findOne(logguedActor.getId());

			result.addObject("curriculum", socialWorker.getCurriculum());
		}

		result.addObject("actor", logguedActor);
		result.addObject("requestURI", "authenticated/showProfile.do");

		return result;
	}

}
