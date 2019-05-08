
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialWorker;

@Repository
public interface SocialWorkerRepository extends JpaRepository<SocialWorker, Integer> {

	@Query("select m from SocialWorker m join m.userAccount u where u.username = ?1")
	public SocialWorker getSocialWorkerByUsername(String username);
}
