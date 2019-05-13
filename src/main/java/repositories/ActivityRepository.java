package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Prisoner;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query("select p from Activity a join a.requests r join r.prisoner p where a = ?1")
	public List<Prisoner> getPrisonersPerActivity(Activity a);

}
