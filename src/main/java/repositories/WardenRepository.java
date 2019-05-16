
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Charge;
import domain.Prisoner;
import domain.Request;
import domain.Visit;
import domain.Visitor;
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

	@Query("select distinct d.visitor from Visit d where (select max(cast((select count(p) from Visit p where p.prisoner = c.prisoner and p.visitor = c.visitor and p.visitStatus = 'PERMITTED' and p.date < (NOW())) as integer)) from Visit c where c.visitStatus = 'PERMITTED' and c.date < (NOW())) = (select count(i) from Visit i where i.prisoner = d.prisoner and i.visitor = d.visitor and i.visitStatus = 'PERMITTED' and i.date < (NOW())) and d.visitStatus = 'PERMITTED' and d.date < (NOW())")
	public List<Visitor> getVisitorsMostVisitsToAPrisoner();

	@Query("select j from Prisoner j where (select max(cast((select count(v) from Visit v where v.prisoner = p and v.visitStatus = 'PERMITTED' and v.date < (NOW()) and v.visitor.id = ?1)as integer)) from Prisoner p) = (select count(v) from Visit v where v.prisoner = j and v.visitStatus = 'PERMITTED' and v.date < (NOW()) and v.visitor.id = ?1)")
	public List<Prisoner> getPrisonersWithMostVisitToAVisitor(int visitorId);

	@Query("select a from Prisoner a where (select count(distinct b) from Visit v  join v.visitor b where v.prisoner = a and v.visitStatus = 'PERMITTED' and v.date < (NOW())) = (select max(cast((select count(distinct b) from Visit v  join v.visitor b where v.prisoner = p and v.visitStatus = 'PERMITTED' and v.date < (NOW()))as integer)) from Prisoner p)")
	public List<Prisoner> getPrisonersWithMostDifferentVisitors();

}
