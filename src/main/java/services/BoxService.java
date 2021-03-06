
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Message;

@Transactional
@Service
public class BoxService {

	@Autowired
	private BoxRepository	boxRepository;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	public Box flushSave(Box box) {
		return this.boxRepository.saveAndFlush(box);
	}

	public Box create() {

		this.actorService.loggedAsActor();
		//UserAccount userAccount;
		//userAccount = LoginService.getPrincipal();
		//Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());
		Box box = new Box();
		List<Message> messages = new ArrayList<Message>();

		box.setName("");
		box.setIsSystem(false);
		box.setMessages(messages);

		//actor.getBoxes().add(box);
		return box;
	}

	public Box createSystem() {		//Crear cajas del sistema
		Box box = new Box();
		List<Message> messages = new ArrayList<Message>();

		box.setName("");
		box.setIsSystem(true);
		box.setMessages(messages);

		return box;
	}

	public Box create(String name, Box fatherBox) {

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		Box box = new Box();
		List<Message> messages = new ArrayList<Message>();
		box.setName(name);
		box.setIsSystem(false);
		box.setMessages(messages);

		List<Box> newBoxes = actor.getBoxes();
		newBoxes.add(box);
		actor.setBoxes(newBoxes);

		return box;
	}

	public Box saveSystem(Box box) {
		return this.boxRepository.save(box);
	}

	public Box save(Box box) {
		Assert.isTrue(!box.getIsSystem());
		this.actorService.loggedAsActor();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		Box savedBox = new Box();
		savedBox = this.boxRepository.save(box);
		actor.getBoxes().add(savedBox);
		this.actorService.save(actor);

		return savedBox;
	}

	public Box updateBox(Box box) {
		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		Assert.isTrue(!box.getIsSystem());

		Box savedBox = new Box();

		savedBox = this.boxRepository.save(box);
		if (actor.getBoxes().contains(savedBox)) {
			actor.getBoxes().remove(savedBox);
			actor.getBoxes().add(savedBox);
		} else
			actor.getBoxes().add(savedBox);

		this.actorService.save(actor);
		return savedBox;
	}

	public void deleteBox(Box box) {
		this.actorService.loggedAsActor();
		Assert.isTrue(!box.getIsSystem());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());
		List<Message> messagesToDelete = this.messageService.getMessagesByBox(box);

		box.getMessages().removeAll(messagesToDelete);

		for (Message m : messagesToDelete) {
			box.getMessages().remove(m);
			if (this.getCurrentBoxByMessage(m).size() == 0)
				this.messageService.delete(m);
		}
		actor.getBoxes().remove(box);
		this.boxRepository.delete(box);
		this.actorService.save(actor);

	}
	public List<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(int id) {
		return this.boxRepository.findOne(id);
	}

	public Box getRecievedBoxByActor(Actor a) {
		return this.boxRepository.getRecievedBoxByActor(a);
	}

	public Box getSuspiciousBoxByActor(Actor a) {
		return this.boxRepository.getSuspiciousBoxByActor(a);
	}

	public Box getTrashBoxByActor(Actor a) {
		return this.boxRepository.getTrashBoxByActor(a);
	}

	public Box getNotificationBoxByActor(Actor a) {
		return this.boxRepository.getNotificationBoxByActor(a);
	}

	public Box getSentBoxByActor(Actor a) {
		return this.boxRepository.getSentBoxByActor(a);
	}

	public List<Box> getCurrentBoxByMessage(Message m) {
		return this.boxRepository.getCurrentBoxByMessage(m);

	}

	public List<Integer> getActorBoxesId() {

		this.actorService.loggedAsActor();
		List<Integer> idBoxes = new ArrayList<Integer>();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());
		for (int i = 0; i < actor.getBoxes().size(); i++)
			idBoxes.add(actor.getBoxes().get(i).getId());

		return idBoxes;
	}

	public List<Box> getActorBoxes() {
		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());
		return actor.getBoxes();
	}

	public Box reconstruct(Box box, BindingResult binding) {

		Box result;
		List<Message> messages = new ArrayList<Message>();
		Box pururu;

		if (box.getId() == 0) {
			result = box;
			result.setIsSystem(false);
			result.setMessages(messages);
		} else {
			pururu = this.boxRepository.findOne(box.getId());
			result = box;

			result.setIsSystem(pururu.getIsSystem());
			result.setMessages(pururu.getMessages());

		}

		this.validator.validate(result, binding);
		return result;

	}

	public void flush() {
		this.boxRepository.flush();
	}
}
