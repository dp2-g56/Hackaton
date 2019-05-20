
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.PersonalRecord;
import domain.SocialWorker;
import repositories.SocialWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class SocialWorkerService {

	@Autowired
	private SocialWorkerRepository socialWorkerRepository;

	@Autowired
	private CurriculumService curriculumService;

	// ----------------------------------------CRUD
	// METHODS--------------------------
	// ------------------------------------------------------------------------------

	public SocialWorker save(SocialWorker SocialWorker) {
		return this.socialWorkerRepository.save(SocialWorker);
	}

	// -----------------------------------------SECURITY-----------------------------
	// ------------------------------------------------------------------------------

	/**
	 * LoggedSocialWorker now contains the security of loggedAsSocialWorker
	 *
	 * @return
	 */
	public SocialWorker loggedSocialWorker() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("SOCIALWORKER"));
		return this.socialWorkerRepository.getSocialWorkerByUsername(userAccount.getUsername());
	}

	public void loggedAsSocialWorker() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("SOCIALWORKER"));

	}

	public void saveNewSocialWorker(SocialWorker SocialWorker) {
		this.loggedAsSocialWorker();
		this.socialWorkerRepository.save(SocialWorker);
	}

	public SocialWorker findOne(int socialId) {
		return this.socialWorkerRepository.findOne(socialId);
	}

	public SocialWorker getSocialWorkerByUsername(String username) {
		return this.socialWorkerRepository.getSocialWorkerByUsername(username);
	}

	public void addCurriculum(PersonalRecord personalRecord) {

		SocialWorker logguedSocialWorker = this.loggedSocialWorker();

		Assert.isNull(logguedSocialWorker.getCurriculum());
		Assert.isTrue(personalRecord.getId() == 0);
		Curriculum curriculum = this.curriculumService.create();
		curriculum.setPersonalRecord(personalRecord);

		logguedSocialWorker.setCurriculum(curriculum);
		this.socialWorkerRepository.save(logguedSocialWorker);

	}

	public void updateCurriculum(PersonalRecord personalRecord) {

		SocialWorker logguedSocialWorker = this.loggedSocialWorker();

		Assert.notNull(logguedSocialWorker.getCurriculum());
		Assert.isTrue(personalRecord.getId() == logguedSocialWorker.getCurriculum().getPersonalRecord().getId());
		Curriculum curriculum = logguedSocialWorker.getCurriculum();
		curriculum.setPersonalRecord(personalRecord);

		logguedSocialWorker.setCurriculum(curriculum);
		this.socialWorkerRepository.save(logguedSocialWorker);

	}

}
