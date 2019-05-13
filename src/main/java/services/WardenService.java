
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import domain.ActivityStatus;
import domain.Box;
import domain.Prisoner;
import domain.Request;
import domain.Visit;
import domain.VisitStatus;
import domain.Warden;
import forms.FormObjectWarden;
import repositories.WardenRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class WardenService {

	@Autowired
	private WardenRepository wardenRepository;
	@Autowired
	private BoxService boxService;

	@Autowired
	private PrisonerService prisonerService;

	// ----------------------------------------CRUD
	// METHODS--------------------------
	// ------------------------------------------------------------------------------

	public Warden save(Warden Warden) {
		return this.wardenRepository.save(Warden);
	}

	public Warden create() {

		// SE DECLARA EL WARDEN
		Warden s = new Warden();

		// SE CREAN LAS LISTAS VACIAS
		List<Box> boxes = new ArrayList<Box>();

		// SE AÑADE EL USERNAME Y EL PASSWORD
		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername("");
		userAccountActor.setPassword("");

		// SE AÑADEN TODOS LOS ATRIBUTOS
		s.setName("");
		s.setMiddleName("");
		s.setSurname("");
		s.setPhoto("");
		s.setBoxes(boxes);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.WARDEN);
		authorities.add(authority);

		userAccountActor.setAuthorities(authorities);
		// NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		userAccountActor.setIsNotLocked(true);

		s.setUserAccount(userAccountActor);
		return s;
	}

	// -----------------------------------------SECURITY-----------------------------
	// ------------------------------------------------------------------------------

	/**
	 * LoggedWarden now contains the security of loggedAsWarden
	 *
	 * @return
	 */
	public Warden loggedWarden() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("WARDEN"));
		return this.wardenRepository.getWardenByUsername(userAccount.getUsername());
	}

	public void loggedAsWarden() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("WARDEN"));
	}

	public void saveWarden(Warden warden) {
		this.loggedAsWarden();

		List<Box> boxes = new ArrayList<>();

		// Boxes
		Box box1 = this.boxService.createSystem();
		box1.setName("SUSPICIOUSBOX");
		Box saved1 = this.boxService.saveSystem(box1);
		boxes.add(saved1);

		Box box2 = this.boxService.createSystem();
		box2.setName("TRASHBOX");
		Box saved2 = this.boxService.saveSystem(box2);
		boxes.add(saved2);

		Box box3 = this.boxService.createSystem();
		box3.setName("OUTBOX");
		Box saved3 = this.boxService.saveSystem(box3);
		boxes.add(saved3);

		Box box4 = this.boxService.createSystem();
		box4.setName("INBOX");
		Box saved4 = this.boxService.saveSystem(box4);
		boxes.add(saved4);

		warden.setBoxes(boxes);

		this.wardenRepository.save(warden);
	}

	public Warden securityAndWarden() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("WARDEN"));
		return this.wardenRepository.getWardenByUsername(userAccount.getUsername());
	}

	public Warden reconstruct(FormObjectWarden formWarden, BindingResult binding) {
		Warden result = this.create();

		result.setName(formWarden.getName());
		result.setMiddleName(formWarden.getMiddleName());
		result.setSurname(formWarden.getSurname());
		result.setPhoto(formWarden.getPhoto());

		// USER ACCOUNT
		UserAccount userAccount = new UserAccount();

		// Authorities
		List<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.WARDEN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		// locked
		userAccount.setIsNotLocked(true);

		// Username
		userAccount.setUsername(formWarden.getUsername());

		// Password
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		userAccount.setPassword(encoder.encodePassword(formWarden.getPassword(), null));

		result.setUserAccount(userAccount);

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		// Confirmacion contrasena
		if (!formWarden.getPassword().equals(formWarden.getConfirmPassword()))
			if (locale.contains("ES"))
				binding.addError(new FieldError("formWarden", "password", formWarden.getPassword(), false, null, null,
						"Las contrasenas no coinciden"));
			else
				binding.addError(new FieldError("formWarden", "password", formWarden.getPassword(), false, null, null,
						"Passwords don't match"));

		// Confirmacion terminos y condiciones
		if (!formWarden.getTermsAndConditions())
			if (locale.contains("ES"))
				binding.addError(new FieldError("formWarden", "termsAndConditions", formWarden.getTermsAndConditions(),
						false, null, null, "Debe aceptar los terminos y condiciones"));
			else
				binding.addError(new FieldError("formWarden", "termsAndConditions", formWarden.getTermsAndConditions(),
						false, null, null, "You must accept the terms and conditions"));

		return result;
	}

	public void isolatePrisoner(Prisoner prisoner) {
		this.loggedAsWarden();
		List<Prisoner> suspects = this.prisonerService.getSuspectPrisoners();
		Assert.isTrue(prisoner != null && suspects.contains(prisoner));

		List<Visit> visits = this.wardenRepository.getFutureVisitsByPrisoner(prisoner.getId());
		List<Request> requests = this.wardenRepository.getRequestToFutureActivitiesByPrisoner(prisoner.getId());

		for (Visit v : visits)
			v.setVisitStatus(VisitStatus.REJECTED);
		for (Request r : requests) {
			r.setRejectReason("Isolated");
			r.setStatus(ActivityStatus.REJECTED);
		}

		prisoner.getUserAccount().setIsNotLocked(false);
		prisoner.getCharges().add(this.wardenRepository.getSuspiciousCharge());
		prisoner.setIsIsolated(true);
		this.prisonerService.save(prisoner);

	}

	public Warden findOne(int id) {
		return this.wardenRepository.findOne(id);
	}
}
