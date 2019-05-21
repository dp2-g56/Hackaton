
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer> {

	@Query("select p from SocialWorker s join s.curriculum c join c.professionalRecords p where s.id = ?1 and p.id = ?2")
	ProfessionalRecord getProfessionalReportOfSocialWorker(int SocialWorkerId, int professionalRecordId);

}
