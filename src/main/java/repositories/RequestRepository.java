package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Request;
import domain.SocialWorker;

public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select distinct(r) from SocialWorker s join s.activities a join a.requests where s=?1")
	public List<Request> getRequestsFromSocialWorker(SocialWorker socialWorker);
}
