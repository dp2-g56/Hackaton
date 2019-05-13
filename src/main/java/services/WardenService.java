
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import domain.ActivityStatus;
import domain.Actor;
import domain.Box;
import domain.Guard;
import domain.Message;
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
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private Validator			validator;

	@Autowired
	private GuardService		guardService;

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

	public boolean loggedAsWardenBoolean() {
		UserAccount userAccount;
		Boolean isWarden = false;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		if (authorities.get(0).toString().equals("WARDEN"))
			isWarden = true;
		return isWarden;
	}

	public Message reconstruct(Message message, BindingResult binding) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		domain.Message result;
		result = this.messageService.create();
		if (message.getId() == 0) {
			result = message;
			result.setSender(actor.getUserAccount().getUsername());
			Date thisMoment = new Date();
			thisMoment.setTime(thisMoment.getTime() - 1000);
			result.setMoment(thisMoment);
			result.setRecipient(actor.getUserAccount().getUsername());

		} else {
			result = this.messageService.findOne(message.getId());

			result.setBody(message.getBody());
			result.setPriority(message.getPriority());
			result.setTags(message.getTags());
			result.setSubject(message.getSubject());
			result.setRecipient(actor.getUserAccount().getUsername());
			result.setSender(actor.getUserAccount().getUsername());

		}

		this.validator.validate(result, binding);

		return result;

	}

	public void broadcastMessage(Message message) {
		this.loggedAsWarden();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor admin = this.actorService.getActorByUsername(userAccount.getUsername());

		List<Actor> actors = new ArrayList<Actor>();
		actors = this.actorService.findAll();

		message.setRecipient("BROADCAST");
		Message messageWardenSaved = this.messageService.save(message);

		Box outBox = this.boxService.getSentBoxByActor(admin);

		outBox.getMessages().add(messageWardenSaved);

		this.actorService.save(admin);

		for (Actor a : actors)
			if (!(a.equals(admin))) {
				message.setRecipient(a.getUserAccount().getUsername());
				this.messageService.sendMessageBroadcasted(message);
			}

	}

	public void broadcastMessageGuards(Message message) {
		this.loggedAsWarden();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor admin = this.actorService.getActorByUsername(userAccount.getUsername());

		List<Guard> guards = new ArrayList<Guard>();
		guards = this.guardService.findAll();

		message.setRecipient("BROADCAST");
		Message messageWardenSaved = this.messageService.save(message);

		Box outBox = this.boxService.getSentBoxByActor(admin);

		outBox.getMessages().add(messageWardenSaved);

		this.actorService.save(admin);

		for (Guard a : guards) {
			message.setRecipient(a.getUserAccount().getUsername());
			this.messageService.sendMessageBroadcasted(message);
		}

	}

	public void broadcastMessagePrisoners(Message message) {
		this.loggedAsWarden();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor admin = this.actorService.getActorByUsername(userAccount.getUsername());

		List<Prisoner> prisoners = new ArrayList<Prisoner>();
		prisoners = this.prisonerService.findAll();

		message.setRecipient("BROADCAST");
		Message messageWardenSaved = this.messageService.save(message);

		Box outBox = this.boxService.getSentBoxByActor(admin);

		outBox.getMessages().add(messageWardenSaved);

		this.actorService.save(admin);

		for (Prisoner a : prisoners) {
			message.setRecipient(a.getUserAccount().getUsername());
			this.messageService.sendMessageBroadcasted(message);
		}

	}

}
