
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.EducationRecord;
import domain.SocialWorker;
import repositories.EducationRecordRepository;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository educationRecordRepository;

	@Autowired
	private SocialWorkerService socialProfileService;

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

	public EducationRecord getEducationRecordOfLoggedSocialWorker(int educationRecordId) {
		SocialWorker socialWorker = this.socialProfileService.loggedSocialWorker();
		EducationRecord educationRecord = this.educationRecordRepository
				.getEducationReportOfSocialWorker(socialWorker.getId(), educationRecordId);
		Assert.notNull(educationRecord);
		return educationRecord;
	}

}
