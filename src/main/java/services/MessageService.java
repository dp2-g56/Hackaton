
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Message;
import domain.PriorityLvl;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	// Actualizar caja que tiene el mensaje EN ESTE ORDEN
	// ACTUALIZAR CAJA SIN EL MENSAJE
	// BORRAR EL MENSAJE Y TODAS SUS COPIAS
	public void delete(Message m) {
		this.messageRepository.delete(m);
	}

	public Message sendMessageBroadcasted(Message message) {

		this.actorService.loggedAsActor();

		Box boxNotification = new Box();

		Box boxSent = new Box();

		Message messageSaved = this.messageRepository.saveAndFlush(message);
		Message messageCopy = this.create(messageSaved.getSubject(), messageSaved.getBody(), messageSaved.getPriority(), messageSaved.getTags(), messageSaved.getRecipient());
		messageCopy.setTags(messageSaved.getTags());

		Message messageCopySaved = this.messageRepository.save(messageCopy);
		Actor actorSent = this.actorService.getActorByUsername(messageSaved.getSender());
		Actor actorReceipent = this.actorService.getActorByUsername(messageSaved.getRecipient());

		boxSent = this.boxService.getSentBoxByActor(actorSent);
		boxNotification = this.boxService.getNotificationBoxByActor(actorReceipent);

		// Guardar la box con ese mensaje;

		boxNotification.getMessages().add(messageCopySaved);
		boxSent.getMessages().add(messageSaved);
		// boxRecieved.setMessages(list);
		this.boxService.saveSystem(boxSent);
		this.boxService.saveSystem(boxNotification);
		this.actorService.save(actorSent);
		this.actorService.flushSave(actorReceipent);

		return messageSaved;
	}

	// Metodo para enviar un mensaje a un ACTOR (O varios, que tambien puede ser)
	public Message sendMessage(Message message) {

		this.actorService.loggedAsActor();

		Actor actorRecieved = this.actorService.getActorByUsername(message.getRecipient());
		Actor senderActor = this.actorService.getActorByUsername(message.getSender());

		Box boxRecieved = new Box();
		Box boxSpam = new Box();
		Box boxSent = new Box();

		List<String> spam = new ArrayList<String>();

		spam = this.configurationService.getConfiguration().getSpamWords();

		Message messageSaved = this.messageRepository.save(message);
		Message messageCopy = this.create(messageSaved.getSubject(), messageSaved.getBody(), messageSaved.getPriority(), messageSaved.getTags(), messageSaved.getRecipient());
		Message messageCopySaved = this.messageRepository.save(messageCopy);

		boxSent = this.boxService.getSentBoxByActor(senderActor);
		boxRecieved = this.boxService.getRecievedBoxByActor(actorRecieved);
		boxSpam = this.boxService.getSpamBoxByActor(actorRecieved);

		// Guardar la box con ese mensaje;

		if (this.configurationService.isStringSpam(messageSaved.getBody(), spam) || this.configurationService.isStringSpam(messageSaved.getSubject(), spam)) {
			boxSent.getMessages().add(messageSaved);
			boxSpam.getMessages().add(messageCopySaved);

			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxSpam);
			this.actorService.save(senderActor);
			this.actorService.save(actorRecieved);

		} else {
			boxRecieved.getMessages().add(messageCopySaved);
			boxSent.getMessages().add(messageSaved);
			// boxRecieved.setMessages(list);
			this.boxService.saveSystem(boxSent);
			this.boxService.saveSystem(boxRecieved);
			this.actorService.save(senderActor);
			this.actorService.save(actorRecieved);
		}

		// Calculamos la Polarity y el hasSpam
		//TODO: this.actorService.updateActorSpam(senderActor);
		//TODO: this.configurationService.computeScore(senderActor);
		return messageSaved;
	}

	public Message save(Message message) {
		return this.messageRepository.save(message);

	}

	public Message create() {

		this.actorService.loggedAsActor();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1000);

		Message message = new Message();
		String sender = userAccount.getUsername();
		message.setMoment(thisMoment);
		message.setSubject("");
		message.setBody("");
		message.setPriority(PriorityLvl.NEUTRAL);
		message.setRecipient("");
		message.setTags("");
		message.setSender(sender);

		return message;
	}

	public Message createSecurityBreach() {

		this.actorService.loggedAsActor();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1000);

		Message message = new Message();
		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());
		Actor receiver = new Actor();
		message.setMoment(thisMoment);
		message.setSubject("Error de seguridad / Security Breach");
		message.setBody("Esto es un mensaje para informar que ha habido una brecha de seguridad // This is a message to inform about a security breach");
		message.setPriority(PriorityLvl.HIGH);
		message.setRecipient(receiver.getUserAccount().getPassword());
		message.setTags("Security, Breach, Notification, Urgent, Important");
		message.setSender(sender.getUserAccount().getPassword());

		return message;
	}

	public Message create(String Subject, String body, PriorityLvl priority, String tags, String recipient) {

		this.actorService.loggedAsActor();

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		Message message = new Message();

		Actor sender = this.actorService.getActorByUsername(userAccount.getUsername());

		message.setMoment(thisMoment);
		message.setSubject(Subject);
		message.setBody(body);
		message.setPriority(priority);
		message.setRecipient(recipient);
		message.setTags(tags);
		message.setSender(sender.getUserAccount().getUsername());

		return message;
	}

	public Message createNotification(String Subject, String body, PriorityLvl priority, String tags, String recipient) {
		this.actorService.loggedAsActor();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		Message message = new Message();

		Actor sender = this.actorService.getActorByUsername("system");

		message.setMoment(thisMoment);
		message.setSubject(Subject);
		message.setBody(body);
		message.setPriority(priority);
		message.setRecipient(recipient);
		message.setTags(tags);
		message.setSender(sender.getUserAccount().getUsername());

		return message;
	}

	public void updateMessage(Message message, Box box) { // Posible problema
		// con copia

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		for (Box b : actor.getBoxes()) {
			if (b.getMessages().contains(message))
				b.getMessages().remove(message);
			// list.remove(message);
			// b.setMessages(list);
			if (b.getName().equals(box.getName())) {
				List<Message> list = b.getMessages();
				list.add(message);
				b.setMessages(list);
			}
		}
	}

	public void deleteMessageFinal(Message message) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		for (Box b : actor.getBoxes())
			if (b.getMessages().contains(message))
				b.getMessages().remove(message);
		// list.remove(message);
		// b.setMessages(list);
		this.delete(message);
	}

	public void deleteMessageToTrashBox(Message message) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		// Box currentBox = this.boxService.getCurrentBoxByMessage(message);

		List<Box> currentBoxes = new ArrayList<>();

		for (Box b : actor.getBoxes())
			if (b.getMessages().contains(message))
				currentBoxes.add(b);

		Box trash = this.boxService.getTrashBoxByActor(actor);

		// When an actor removes a message from a box other than trash box, it
		// is moved to the trash box;

		for (Box b : actor.getBoxes()) {
			if (b.getMessages().contains(message))
				b.getMessages().remove(message);
			// list.remove(message);
			// b.setMessages(list);
			if (b.equals(trash)) {
				List<Message> list = b.getMessages();
				list.add(message);
				b.setMessages(list);
			}
		}

		/*
		 * for (Box currentBox : currentBoxes)
		 * if (currentBox.equals(trash)) {
		 * for (Box b : actor.getBoxes())
		 * if (b.getMessages().contains(message)) {
		 * b.getMessages().remove(message);
		 * this.messageRepository.delete(message);
		 * }
		 * } else
		 * this.updateMessage(message, trash);
		 */
	}
	public void copyMessage(Message message, Box box) {

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());
		Assert.isTrue(actor.getBoxes().contains(box));

		box.getMessages().add(message);
	}

	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public List<Message> findAll2() {
		return this.messageRepository.findAll2();
	}

	public Message findOne(int id) {
		return this.messageRepository.findOne(id);
	}

	public List<Message> getMessagesByBox(Box b) {
		return this.messageRepository.getMessagesOfBox(b);
	}

	public domain.Message reconstruct(domain.Message messageTest, BindingResult binding) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		domain.Message result;
		if (messageTest.getId() == 0) {
			result = messageTest;
			result.setSender(userAccount.getUsername());
			Date thisMoment = new Date();
			thisMoment.setTime(thisMoment.getTime() - 1000);
			result.setMoment(thisMoment);

		} else {
			result = this.messageRepository.findOne(messageTest.getId());

			result.setBody(messageTest.getBody());
			result.setPriority(messageTest.getPriority());
			result.setTags(messageTest.getTags());
			result.setSubject(messageTest.getSubject());
			result.setRecipient(messageTest.getRecipient());
			// result.setMoment(messageTest.getMoment());
		}

		this.validator.validate(result, binding);
		return result;

	}

	public Message reconstructDelete(Message messageTest) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		Message result;

		result = this.messageRepository.findOne(messageTest.getId());

		return result;

	}

	public void deleteInBatch(List<Message> messages) {
		this.messageRepository.deleteInBatch(messages);
	}
}
