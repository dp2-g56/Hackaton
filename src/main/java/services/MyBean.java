
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.Prisoner;

@Component("myBean")
public class MyBean {

	@Autowired
	private PrisonerService	prisonerService;


	public void printMessage() {
		List<Prisoner> prisonersToCheck = new ArrayList<Prisoner>();

		prisonersToCheck = this.prisonerService.getPrisonersToBeFree();

		if (prisonersToCheck.size() > 0)
			for (Prisoner p : prisonersToCheck) {
				p.getUserAccount().setIsNotLocked(false);
				p.setFreedom(true);

				this.prisonerService.save(p);
			}

		System.out.println("Hola Müller, Ponme A++");
	}
}
