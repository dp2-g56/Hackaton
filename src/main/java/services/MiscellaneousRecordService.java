
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Manged Repository

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;


	// Simple CRUD methods

	public MiscellaneousRecord create() {

		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle("");
		miscellaneousRecord.setLinkAttachment("");

		return miscellaneousRecord;

	}

	public List<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}
	public MiscellaneousRecord findOne(Integer id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	public void save(MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}

	public void delete(MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

}
