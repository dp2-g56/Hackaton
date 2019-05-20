
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalRecord;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {

	@Query("select p from SocialWorker s join s.curriculum c join c.personalRecord p where s.id = ?1 and p.id = ?2")
	PersonalRecord getPersonalRecordOfSocialWorker(int socialWorkerId, int personalRecordId);

}
