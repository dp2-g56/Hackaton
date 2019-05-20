
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

	@Query("select e from SocialWorker s join s.curriculum c join c.educationRecords e where s.id = ?1 and e.id = ?2")
	EducationRecord getEducationReportOfSocialWorker(int socialWorkerId, int educationRecordId);

}
