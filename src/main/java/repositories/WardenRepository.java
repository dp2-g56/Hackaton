
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Charge;
import domain.Warden;

@Repository
public interface WardenRepository extends JpaRepository<Warden, Integer> {

	@Query("select m from Warden m join m.userAccount u where u.username = ?1")
	public Warden getWardenByUsername(String username);

	@Query("select a from Charge a where a.titleEnglish = 'Suspicious'")
	public Charge getSuspiciousCharge();

}
