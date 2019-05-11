
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m")
	public List<Message> findAll2();

	@Query("select b.messages from Box b where b = ?1")
	public List<Message> getMessagesOfBox(Box box);

}
