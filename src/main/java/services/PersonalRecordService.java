
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	public PersonalRecord create() {

		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName("");
		personalRecord.setPhoto("");
		personalRecord.setEmail("");
		personalRecord.setPhoneNumber("");
		personalRecord.setUrlLinkedInProfile("");

		return personalRecord;

	}

	public List<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}
	public PersonalRecord findOne(Integer id) {
		return this.personalRecordRepository.findOne(id);
	}

	public PersonalRecord save(PersonalRecord personalRecord) {
		return this.personalRecordRepository.save(personalRecord);
	}

}
