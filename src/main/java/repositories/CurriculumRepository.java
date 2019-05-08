
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.SocialWorker;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select a from SocialWorker a join a.curriculum b where b.id = ?1")
	public SocialWorker getSocialWorkerByCurriculum(int curriculumId);

}
