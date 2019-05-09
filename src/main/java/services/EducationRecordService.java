
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	educationRecordRepository;


	public EducationRecord create() {

		EducationRecord educationRecord = new EducationRecord();
		educationRecord.setTitle("");
		educationRecord.setStartDateStudy(null);
		educationRecord.setEndDateStudy(null);
		educationRecord.setInstitution("");
		educationRecord.setLink("");

		return educationRecord;

	}

	public List<EducationRecord> findAll() {

		return this.educationRecordRepository.findAll();
	}
	public EducationRecord findOne(Integer id) {
		return this.educationRecordRepository.findOne(id);
	}

	public void save(EducationRecord educationRecord) {
		this.educationRecordRepository.save(educationRecord);
	}

	public void delete(EducationRecord educationRecord) {
		this.educationRecordRepository.delete(educationRecord);
	}

}
