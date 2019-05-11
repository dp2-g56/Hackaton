
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Charge;
import domain.Request;
import domain.Visit;
import domain.Warden;

@Repository
public interface WardenRepository extends JpaRepository<Warden, Integer> {

	@Query("select m from Warden m join m.userAccount u where u.username = ?1")
	public Warden getWardenByUsername(String username);

	@Query("select a from Charge a where a.titleEnglish = 'Suspicious'")
	public Charge getSuspiciousCharge();

	@Query("select v from Prisoner p join p.visits v where v.date > (NOW()) and p.id = ?1 and v.visitStatus != 'REJECTED'")
	public List<Visit> getFutureVisitsByPrisoner(int prisonerId);

	@Query("select r from Prisoner p join p.requests r join r.activity a where p.id = ?1 and r.status = 'APPROVED' and a.realizationDate > (NOW())")
	public List<Request> getRequestToFutureActivitiesByPrisoner(int prisonerId);

}
