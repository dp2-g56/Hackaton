
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warden;

@Repository
public interface WardenRepository extends JpaRepository<Warden, Integer> {

	@Query("select m from Warden m join m.userAccount u where u.username = ?1")
	public Warden getWardenByUsername(String username);

}
