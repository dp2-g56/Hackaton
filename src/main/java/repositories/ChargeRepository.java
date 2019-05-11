
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Charge;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Integer> {

	@Query("select c from Charge c where c.isDraftMode = false")
	public List<Charge> getFinalCharges();

}
