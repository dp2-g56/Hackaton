
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Box;
import domain.Message;
import repositories.BoxRepository;

@Transactional
@Service
public class BoxService {

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private ActorService actorService;

	public Box createSystem() { // Crear cajas del sistema
		Box box = new Box();
		List<Message> messages = new ArrayList<Message>();

		box.setName("");
		box.setIsSystem(true);
		box.setMessages(messages);

		return box;
	}

	public Box saveSystem(Box box) {
		return this.boxRepository.save(box);
	}

	public List<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(int id) {
		return this.boxRepository.findOne(id);
	}

}
