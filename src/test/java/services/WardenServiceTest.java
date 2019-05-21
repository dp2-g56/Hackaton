package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Prisoner;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class WardenServiceTest extends AbstractTest {

	@Autowired
	private WardenService wardenService;

	@Autowired
	private PrisonerService prisonerService;

	/**
	 * SENTENCE COVERAGE:
	 */

	/**
	 * 15. An actor who is authenticated as a warden must be able to:
	 *
	 * 9. Isolate a Prisoner with suspicious messages, when a Prisoner is
	 * isolated, Suspicious is added to his list of charges, this action cannot
	 * be undone.
	 *
	 * Ratio of data coverage: 100% - Access as a Warden or not. - Prisoner is
	 * suspicious or not
	 *
	 **/
	@Test
	public void driverIsolatePrisoner() {

		Object testingData[][] = {

				/**
				 * POSITIVE TEST: Warden isolates a suspicious prisoner
				 **/
				{ "warden1", super.getEntityId("prisoner1"), null },
				/**
				 * NEGATIVE TEST: Another user is trying to isolate a prisoner
				 **/
				{ "prisoner2", super.getEntityId("prisoner1"), IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: Warden is trying to isolate a non-suspicious
				 * prisoner
				 **/
				{ "warden1", super.getEntityId("prisoner2"), IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.isolatePrisonerTemplate((String) testingData[i][0], (Integer) testingData[i][1],
					(Class<?>) testingData[i][2]);

	}

	private void isolatePrisonerTemplate(String warden, Integer prisonerId, Class<?> expected) {

		Prisoner prisoner = this.prisonerService.findOne(prisonerId);
		Boolean b1 = prisoner.getIsIsolated();

		Class<?> caught = null;

		try {
			super.startTransaction();

			super.authenticate(warden);

			this.wardenService.isolatePrisoner(prisoner);

			Prisoner prisonerIsolated = this.prisonerService.findOne(prisonerId);
			Boolean b2 = prisonerIsolated.getIsIsolated();

			Assert.isTrue(b1 != b2);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

}
