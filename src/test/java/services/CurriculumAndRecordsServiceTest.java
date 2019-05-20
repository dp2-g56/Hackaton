package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalRecord;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CurriculumAndRecordsServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService curriculumService;

	@Autowired
	private SocialWorkerService socialWorkerService;

	@Autowired
	private PersonalRecordService personalRecordService;

	/**
	 * R39. An actor who is authenticated as a SocialWorker must be able to:
	 *
	 * 3. Manage his or her curriculum, which includes showing it.
	 *
	 * Ratio of data coverage: 100% - Access as a SocialWorker or not. -
	 * SocialWorker has a curriculum or not.
	 *
	 **/
	@Test
	public void driverShowCurriculum() {

		Object testingData[][] = {

				/**
				 * POSITIVE TEST: SocialWorker is showing his or her curriculum
				 **/
				{ "socialWorker1", null },
				/**
				 * NEGATIVE TEST: Another user is trying to show a curriculum
				 **/
				{ "salesMan1", IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: SocialWorker without curriculum is trying to
				 * edit his or her curriculum
				 **/
				{ "socialWorker3", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.showCurriculumTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	private void showCurriculumTemplate(String socialWorker, Class<?> expected) {

		Class<?> caught = null;

		try {
			super.startTransaction();

			super.authenticate(socialWorker);

			this.curriculumService.getCurriculumOfLoggedSocialWorker();

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

	/**
	 * R39. An actor who is authenticated as a SocialWorker must be able to:
	 *
	 * 3. Manage his or her curriculum, which includes to create a curriculum
	 * with a personal record.
	 *
	 * Ratio of data coverage: 5/7 = 71.42% - Access as a SocialWorker or not.
	 * -SocialWorker has a curriculum or not - 4 attributes with restrictions
	 *
	 **/
	@Test
	public void driverCreateCurriculumPersonalRecord() {

		Object testingData[][] = {

				/**
				 * POSITIVE TEST: SocialWorker is creating a curriculum with
				 * personal record with correct information
				 **/
				{ "socialWorker3", "Name", "http://www.image.com", "email@email.com", "+34609235520",
						"http://www.linkedin.com", null },
				/**
				 * NEGATIVE TEST: SocialWorker is creating a curriculum but he
				 * or she has a curriculum
				 **/
				{ "socialWorker1", "Name", "http://www.image.com", "email@email.com", "+34609235520",
						"http://www.linkedin.com", IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: Another user is trying to create a curriculum
				 **/
				{ "salesMan1", "Name", "http://www.image.com", "email@email.com", "+34609235520",
						"http://www.linkedin.com", IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: SocialWorker is creating a curriculum with
				 * personal record with an incorrect emails
				 **/
				{ "socialWorker3", "Name", "http://www.image.com", "email", "+34609235520", "http://www.linkedin.com",
						ConstraintViolationException.class },
				/**
				 * NEGATIVE TEST: SocialWorker is creating a curriculum with
				 * personal record with a blank name
				 **/
				{ "socialWorker3", "", "http://www.image.com", "email@gmail.com", "+34609235520",
						"http://www.linkedin.com", ConstraintViolationException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.createPersonalDataTemplate((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (Class<?>) testingData[i][6]);

	}

	private void createPersonalDataTemplate(String socialWorker, String name, String image, String email, String phone,
			String linkedin, Class<?> expected) {

		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName(name);
		personalRecord.setPhoto(image);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phone);
		personalRecord.setUrlLinkedInProfile(linkedin);

		Class<?> caught = null;

		try {
			super.startTransaction();

			super.authenticate(socialWorker);

			this.socialWorkerService.addCurriculum(personalRecord);

			this.flushTransaction();

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

	/**
	 * R39. An actor who is authenticated as a SocialWorker must be able to:
	 *
	 * 3. Manage his or her curriculum, which includes to update a curriculum
	 * with a personal record.
	 *
	 * Ratio of data coverage: 5/7 = 71.42% - Access as a SocialWorker or not. -
	 * SocialWorker owns the personal record or not - 4 attributes with
	 * restrictions
	 *
	 **/
	@Test
	public void driverUpdateCurriculumPersonalRecord() {

		Object testingData[][] = {

				/**
				 * POSITIVE TEST: SocialWorker is updating his or her curriculum
				 * with the personal record with correct information
				 **/
				{ "socialWorker1", super.getEntityId("personalRecord1"), "Name", "http://www.image.com",
						"email@email.com", "+34609235520", "http://www.linkedin.com", null },
				/**
				 * NEGATIVE TEST: SocialWorker is trying to update a curriculum
				 * and personal record that does not belong to his or her
				 **/
				{ "socialWorker1", super.getEntityId("personalRecord2"), "Name", "http://www.image.com",
						"email@email.com", "+34609235520", "http://www.linkedin.com", IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: Another user is trying to update a curriculum
				 **/
				{ "salesMan1", super.getEntityId("personalRecord1"), "Name", "http://www.image.com", "email@email.com",
						"+34609235520", "http://www.linkedin.com", IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: SocialWorker is trying to update his or her
				 * curriculum with personal record with an incorrect emails
				 **/
				{ "socialWorker1", super.getEntityId("personalRecord1"), "Name", "http://www.image.com", "email",
						"+34609235520", "http://www.linkedin.com", ConstraintViolationException.class },
				/**
				 * NEGATIVE TEST: SocialWorker is trying to update his or her
				 * curriculum with personal record with a blank name
				 **/
				{ "socialWorker1", super.getEntityId("personalRecord1"), "", "http://www.image.com", "email@gmail.com",
						"+34609235520", "http://www.linkedin.com", ConstraintViolationException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.createPersonalDataTemplate((String) testingData[i][0], (Integer) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);

	}

	private void createPersonalDataTemplate(String socialWorker, Integer personalRecordId, String name, String image,
			String email, String phone, String linkedin, Class<?> expected) {

		PersonalRecord personalRecord = this.personalRecordService.findOne(personalRecordId);
		personalRecord.setFullName(name);
		personalRecord.setPhoto(image);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phone);
		personalRecord.setUrlLinkedInProfile(linkedin);

		Class<?> caught = null;

		try {
			super.startTransaction();

			super.authenticate(socialWorker);

			this.personalRecordService.getPersonalRecordAsSocialWorker(personalRecordId);
			this.socialWorkerService.updateCurriculum(personalRecord);

			this.flushTransaction();

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

}
