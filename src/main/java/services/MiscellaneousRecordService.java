
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.MiscellaneousRecord;
import domain.SocialWorker;
import repositories.MiscellaneousRecordRepository;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Manged Repository

	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;

	@Autowired
	private SocialWorkerService socialWorkerService;;

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

	public MiscellaneousRecord getMiscellaneousRecordOfLoggedSocialWorker(Integer miscellaneousRecordId) {
		SocialWorker socialWorker = this.socialWorkerService.loggedSocialWorker();
		MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordRepository
				.getMiscellaneousRecordOfSocialWorker(socialWorker.getId(), miscellaneousRecordId);
		Assert.notNull(miscellaneousRecordId);
		return miscellaneousRecord;
	}

}
