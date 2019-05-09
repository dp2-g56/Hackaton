
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;


	public ProfessionalRecord create() {

		ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setNameCompany("");
		professionalRecord.setStartDate(null);
		professionalRecord.setEndDate(null);
		professionalRecord.setRole("");
		professionalRecord.setLinkAttachment("");

		return professionalRecord;

	}

	public List<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}
	public ProfessionalRecord findOne(Integer id) {
		return this.professionalRecordRepository.findOne(id);
	}

	public void save(ProfessionalRecord professionalRecord) {
		this.professionalRecordRepository.save(professionalRecord);
	}

	public void delete(ProfessionalRecord professionalRecord) {
		this.professionalRecordRepository.delete(professionalRecord);
	}

}
