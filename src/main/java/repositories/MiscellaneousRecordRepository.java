
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

	@Query("select m from SocialWorker s join s.curriculum c join c.miscellaneousRecords m where s.id = ?1 and m.id = ?2")
	MiscellaneousRecord getMiscellaneousRecordOfSocialWorker(int socialWorkerId, Integer miscellaneousRecordId);

}
