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

	@Test
	public void statistics() {
		// 1
		System.out.println("ENTRA 1: " + this.wardenService.statistics().get(0));
		Assert.isTrue(this.wardenService.statistics().get(0) == 50.);
		System.out.println("SALE 1\n");

		// 2
		System.out.println("ENTRA 2: " + this.wardenService.statistics().get(1));
		Assert.isTrue(this.wardenService.statistics().get(1) == 0.);
		System.out.println("SALE 2\n");

		// 3
		System.out.println("ENTRA 3: " + this.wardenService.statistics().get(2));
		Assert.isTrue(
				this.wardenService.statistics().get(2) <= 66.68 && this.wardenService.statistics().get(2) >= 66.66);
		System.out.println("SALE 3\n");

//		// 4
//		System.out.println("ENTRA 4: " + this.wardenService.statistics().get(3));
//		Assert.isTrue(this.wardenService.statistics().get(3) == 0.);
//		System.out.println("SALE 4\n");
//
//		// 5
//		System.out.println("ENTRA 5: " + this.wardenService.statistics().get(4));
//		Assert.isTrue(this.wardenService.statistics().get(4) == 0.);
//		System.out.println("SALE 5\n");
//
//		// 6
//		System.out.println("ENTRA 6: " + this.wardenService.statistics().get(5));
//		Assert.isTrue(this.wardenService.statistics().get(5) == 0.);
//		System.out.println("SALE 6\n");
//
//		// 7
//		System.out.println("ENTRA 7: " + this.wardenService.statistics().get(6));
//		Assert.isTrue(this.wardenService.statistics().get(6) == 0.);
//		System.out.println("SALE 7\n");
//
//		// 8
//		System.out.println("ENTRA 8: " + this.wardenService.statistics().get(7));
//		Assert.isTrue(this.wardenService.statistics().get(7) == 0.);
//		System.out.println("SALE 8\n");
//
//		// 9
//		System.out.println("ENTRA 9: " + this.wardenService.statistics().get(8));
//		Assert.isTrue(this.wardenService.statistics().get(8) == 0.);
//		System.out.println("SALE 9\n");
//
//		// 10
//		System.out.println("ENTRA 10: " + this.wardenService.statistics().get(9));
//		Assert.isTrue(this.wardenService.statistics().get(9) == 0.);
//		System.out.println("SALE 10\n");
//
//		// 11
//		System.out.println("ENTRA 11: " + this.wardenService.statistics().get(10));
//		Assert.isTrue(this.wardenService.statistics().get(10) == 0.);
//		System.out.println("SALE 11\n");
	}

}
