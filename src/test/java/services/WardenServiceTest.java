
package services;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Charge;
import domain.Prisoner;
import domain.SalesMan;
import domain.Warden;
import forms.FormObjectPrisoner;
import forms.FormObjectSalesman;
import forms.FormObjectWarden;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class WardenServiceTest extends AbstractTest {

	@Autowired
	private WardenService wardenService;

	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private SalesManService salesManService;

	@Autowired
	private ChargeService chargeService;

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

		// 4
		System.out.println("ENTRA 4: " + this.wardenService.statistics().get(3));
		Assert.isTrue(this.wardenService.statistics().get(3) == 25.);
		System.out.println("SALE 4\n");

		// 5
		System.out.println("ENTRA 5: " + this.wardenService.statistics().get(4));
		Assert.isTrue(this.wardenService.statistics().get(4) == 1.);
		System.out.println("SALE 5\n");

		// 6
		System.out.println("ENTRA 6: " + this.wardenService.statistics().get(5));
		Assert.isTrue(this.wardenService.statistics().get(5) == 2.);
		System.out.println("SALE 6\n");

		// 7
		System.out.println("ENTRA 7: " + this.wardenService.statistics().get(6));
		Assert.isTrue(this.wardenService.statistics().get(6) <= 0.8 && this.wardenService.statistics().get(6) >= 0.6);
		System.out.println("SALE 7\n");

		// 8
		System.out.println("ENTRA 8: " + this.wardenService.statistics().get(7));
		Assert.isTrue(this.wardenService.statistics().get(7) >= -1 && this.wardenService.statistics().get(7) <= -0.8);
		System.out.println("SALE 8\n");

		// 9
		System.out.println("ENTRA 9: " + this.wardenService.statistics().get(8));
		Assert.isTrue(
				this.wardenService.statistics().get(8) <= 0.612 && this.wardenService.statistics().get(8) >= 0.61);
		System.out.println("SALE 9\n");

		// 10
		System.out.println("ENTRA 10: " + this.wardenService.statistics().get(9));
		Assert.isTrue(
				this.wardenService.statistics().get(9) <= -0.311 && this.wardenService.statistics().get(9) >= -0.313);
		System.out.println("SALE 10\n");

		// 11
		System.out.println("ENTRA 11: " + this.wardenService.statistics().get(10));
		Assert.isTrue(this.wardenService.statistics().get(10) == 67.);
		System.out.println("SALE 11\n");

		// 12
		System.out.println("ENTRA 12: " + this.wardenService.getActivitiesLargestAvgCrimeRate().size());
		Assert.isTrue(this.wardenService.getActivitiesLargestAvgCrimeRate().size() == 1);
		System.out.println("SALE 12\n");

		// 13
		System.out.println("ENTRA 13: " + this.wardenService.getActivitiesSmallestAvgCrimeRate().size());
		Assert.isTrue(this.wardenService.getActivitiesSmallestAvgCrimeRate().size() == 1);
		System.out.println("SALE 13\n");

		// 14
		System.out.println("ENTRA 14: " + this.wardenService.getActivitiesMostSearched().size());
		Assert.isTrue(this.wardenService.getActivitiesMostSearched().size() == 4);
		System.out.println("SALE 14\n");

		// 15
		System.out.println("ENTRA 15: " + this.wardenService.getActivitiesLargestNumberPrisoners().size());
		Assert.isTrue(this.wardenService.getActivitiesLargestNumberPrisoners().size() == 2);
		System.out.println("SALE 15\n");

		// 16
		System.out.println("ENTRA 16: " + this.wardenService.getCouplesWithMostVisits().size());
		Assert.isTrue(this.wardenService.getCouplesWithMostVisits().size() == 2);
		System.out.println("SALE 16\n");

		// 17
		System.out.println("ENTRA 17: " + this.wardenService.getGuardsWithTheLargestNumberOfReportsWritten().size());
		Assert.isTrue(this.wardenService.getGuardsWithTheLargestNumberOfReportsWritten().size() == 2);
		System.out.println("SALE 17\n");

		// 18
		System.out.println("ENTRA 18: " + this.wardenService
				.getPrisonersMostRejectedRequestToDifferentActivitiesAndNoApprovedOnThoseActivities().size());
		Assert.isTrue(this.wardenService
				.getPrisonersMostRejectedRequestToDifferentActivitiesAndNoApprovedOnThoseActivities().size() == 2);
		System.out.println("SALE 18\n");

		// 19
		System.out.println("ENTRA 19: " + this.wardenService.getPrisonersWithVisitsToMostDifferentVisitors().size());
		Assert.isTrue(this.wardenService.getPrisonersWithVisitsToMostDifferentVisitors().size() == 2);
		System.out.println("SALE 19\n");

		// 20
		System.out.println("ENTRA 20: " + this.wardenService.getSocialWorkerMostActivitiesFull().size());
		Assert.isTrue(this.wardenService.getSocialWorkerMostActivitiesFull().size() == 3);
		System.out.println("SALE 20\n");

		// 21
		System.out.println("ENTRA 21: " + this.wardenService.getSocialWorkersLowestRatioPrisonersPerActivity().size());
		Assert.isTrue(this.wardenService.getSocialWorkersLowestRatioPrisonersPerActivity().size() == 2);
		System.out.println("SALE 21\n");

		// 22
		System.out.println("ENTRA 22: " + this.wardenService.getTop3PrisonersLowestCrimeRate().size());
		Assert.isTrue(this.wardenService.getTop3PrisonersLowestCrimeRate().size() == 3);
		System.out.println("SALE 22\n");

		// 23
		System.out.println(
				"ENTRA 23: " + this.wardenService.getTop5PrisonersParticipatedMostActivitiesLastMonth().size());
		Assert.isTrue(this.wardenService.getTop5PrisonersParticipatedMostActivitiesLastMonth().size() == 4);
		System.out.println("SALE 23\n");

		// 24
		System.out.println("ENTRA 24: " + this.wardenService.getVisitorsWithVisitsToMostDifferentPrisoners().size());
		Assert.isTrue(this.wardenService.getVisitorsWithVisitsToMostDifferentPrisoners().size() == 2);
		System.out.println("SALE 24\n");

	}

	@Test
	public void driverRegisterSalesman() {

		Object testingData[][] = { {
				// Positive test, create a salesMan
				"warden1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B", "storeName",
				"username", "password", "password", true, null },
				{
						// Positive test, blank middleName
						"warden1", "name", "", "surname", "https://www.youtube.com", "ESA0011012B", "storeName",
						"username", "password", "password", true, null },
				{
						// Negative test, Blank name
						"warden1", "", "middleName", "surname", "https://www.youtube.com", "ESA0011012B", "storeName",
						"username", "password", "password", true, ConstraintViolationException.class },
				{
						// Negative test, Blank surname
						"warden1", "name", "middleName", "", "https://www.youtube.com", "ESA0011012B", "storeName",
						"username", "password", "password", true, ConstraintViolationException.class },
				{
						// Negative test, no URL photo
						"warden1", "name", "middleName", "surname", "notURL", "ESA0011012B", "storeName", "username",
						"password", "password", true, ConstraintViolationException.class },
				{
						// Negative test, Blank store name
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B", "",
						"username", "password", "password", true, ConstraintViolationException.class },
				{
						// Negative test, Blank username
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B",
						"storeName", "", "password", "password", true, ConstraintViolationException.class },
				{
						// Negative test, Blank password
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B",
						"storeName", "username", "", "sasa", true, NullPointerException.class },
				{
						// Negative test, not equal password
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B",
						"storeName", "username", "sav", "sasa", true, NullPointerException.class },
				{
						// Negative test, false terms
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B",
						"storeName", "username", "password", "password", false, NullPointerException.class },
				{
						// Negative test, Vat number invalid
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "invalid", "storeName",
						"username", "password", "password", false, NullPointerException.class },
				{
						// Negative test, not a warden creating salesMan
						"prisoner1", "name", "middleName", "surname", "https://www.youtube.com", "ESA0011012B",
						"storeName", "username", "password", "password", true, IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterSalesman((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9], (Boolean) testingData[i][10],
					(Class<?>) testingData[i][11]);
	}

	protected void templateRegisterSalesman(String loggedUsername, String name, String middleName, String surname,
			String photo, String VATnumber, String storeName, String username, String password, String confirmPassword,
			Boolean terms, Class<?> expected) {

		Class<?> caught = null;

		try {

			// En cada iteraccion comenzamos una transaccion, de esya manera, no
			// se toman valores residuales de otros test
			this.startTransaction();

			super.authenticate(loggedUsername);

			FormObjectSalesman formObject = new FormObjectSalesman();

			formObject.setName(name);
			formObject.setMiddleName(middleName);
			formObject.setSurname(surname);
			formObject.setPhoto(photo);
			formObject.setUsername(username);
			formObject.setPassword(password);
			formObject.setConfirmPassword(confirmPassword);
			formObject.setTermsAndConditions(terms);
			formObject.setVATNumber(VATnumber);
			formObject.setStoreName(storeName);

			BindingResult binding = null;
			SalesMan salesMan = this.salesManService.reconstruct(formObject, binding);

			this.salesManService.saveSalesman(salesMan);
			this.salesManService.flush();

			super.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			// Se fuerza el rollback para que no de ningun problema la siguiente
			// iteracion
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

	/**
	 * ---------------------------------------------------------------------------------------------------------------------
	 **/

	@Test
	public void driverRegisterEditPrisoner() {

		Object testingData[][] = { {
				// Positive test, create a prisoner
				"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
				"password", "Murder", true, null },
				{
						// Positive test, blank middleName
						"warden1", "name", "", "surname", "https://www.youtube.com", "username", "password", "password",
						"Murder", true, null },
				{
						// Negative test, Blank name
						"warden1", "", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", "Murder", true, ConstraintViolationException.class },
				{
						// Negative test, Blank surname
						"warden1", "name", "middleName", "", "https://www.youtube.com", "username", "password",
						"password", "Murder", true, ConstraintViolationException.class },
				{
						// Negative test, no URL photo
						"warden1", "name", "middleName", "surname", "notURL", "username", "password", "password",
						"Murder", true, ConstraintViolationException.class },
				{
						// Negative test, Blank username
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "", "password",
						"password", "Murder", true, ConstraintViolationException.class },
				{
						// Negative test, Blank password
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "", "sasa",
						"Murder", true, NullPointerException.class },
				{
						// Negative test, not equal password
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "sav",
						"sasa", "Murder", true, NullPointerException.class },
				{
						// Negative test, false terms
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", "Murder", false, NullPointerException.class },
				{
						// Negative test, no charges selected
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", "", true, NullPointerException.class },
				{
						// Negative test, not a warden creating prisoner
						"prisoner1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", "Murder", true, IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterPrisoner((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void templateRegisterPrisoner(String loggedUsername, String name, String middleName, String surname,
			String photo, String username, String password, String confirmPassword, String charge, Boolean terms,
			Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			List<Charge> c = this.chargeService.getCharge(charge);

			// En cada iteraccion comenzamos una transaccion, de esya manera, no
			// se toman valores residuales de otros test

			super.authenticate(loggedUsername);

			FormObjectPrisoner formObject = new FormObjectPrisoner();

			formObject.setName(name);
			formObject.setMiddleName(middleName);
			formObject.setSurname(surname);
			formObject.setPhoto(photo);
			formObject.setUsername(username);
			formObject.setPassword(password);
			formObject.setConfirmPassword(confirmPassword);
			formObject.setTermsAndConditions(terms);
			formObject.setCharges(c);

			BindingResult binding = null;
			Prisoner prisoner = this.prisonerService.reconstruct(formObject, binding);

			this.prisonerService.savePrisoner(prisoner);
			this.prisonerService.flush();

			super.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			// Se fuerza el rollback para que no de ningun problema la siguiente
			// iteracion
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

	@Test
	public void driverRegisterEditWarden() {

		Object testingData[][] = { {
				// Positive test, create a warden
				"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
				"password", true, "warden@gmail.com", null },
				{
						// Positive test, blank middleName
						"warden1", "name", "", "surname", "https://www.youtube.com", "username", "password", "password",
						true, "warden@gmail.com", null },
				{
						// Negative test, Blank name
						"warden1", "", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", true, "warden@gmail.com", ConstraintViolationException.class },
				{
						// Negative test, Blank surname
						"warden1", "name", "middleName", "", "https://www.youtube.com", "username", "password",
						"password", true, "warden@gmail.com", ConstraintViolationException.class },
				{
						// Negative test, no URL photo
						"warden1", "name", "middleName", "surname", "notURL", "username", "password", "password", true,
						"warden@gmail.com", ConstraintViolationException.class },
				{
						// Negative test, Blank username
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "", "password",
						"password", true, "warden@gmail.com", ConstraintViolationException.class },
				{
						// Negative test, Blank password
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "", "sasa",
						true, "warden@gmail.com", NullPointerException.class },
				{
						// Negative test, not equal password
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "sav",
						"sasa", true, "warden@gmail.com", NullPointerException.class },
				{
						// Negative test, false terms
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", false, "warden@gmail.com", NullPointerException.class },
				{
						// Negative test, invalid email
						"warden1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", true, "invalid", ConstraintViolationException.class },
				{
						// Negative test, not a warden creating warden
						"prisoner1", "name", "middleName", "surname", "https://www.youtube.com", "username", "password",
						"password", true, "warden@gmail.com", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterWarden((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(Boolean) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void templateRegisterWarden(String loggedUsername, String name, String middleName, String surname,
			String photo, String username, String password, String confirmPassword, Boolean terms, String email,
			Class<?> expected) {

		Class<?> caught = null;

		try {

			// En cada iteraccion comenzamos una transaccion, de esya manera, no
			// se toman valores residuales de otros test
			this.startTransaction();

			super.authenticate(loggedUsername);

			FormObjectWarden formObject = new FormObjectWarden();

			formObject.setName(name);
			formObject.setMiddleName(middleName);
			formObject.setSurname(surname);
			formObject.setPhoto(photo);
			formObject.setUsername(username);
			formObject.setPassword(password);
			formObject.setConfirmPassword(confirmPassword);
			formObject.setTermsAndConditions(terms);
			formObject.setEmail(email);

			BindingResult binding = null;
			Warden warden = this.wardenService.reconstruct(formObject, binding);

			this.wardenService.saveWarden(warden);
			this.wardenService.flush();

			super.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			// Se fuerza el rollback para que no de ningun problema la siguiente
			// iteracion
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteWarden() {

		Object testingData[][] = {

				/**
				 * POSITIVE TEST: delete own accountr
				 **/
				{ "warden1", "one", null },
				/**
				 * NEGATIVE TEST: Another user is trying to delete the account
				 **/
				{ "prisoner1", "one", IllegalArgumentException.class },
				/**
				 * NEGATIVE TEST: All wardens are deleted
				 **/
				{ "warden1", "All", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.deleteWardenTemplate((String) testingData[i][0], (String) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}

	private void deleteWardenTemplate(String usernameLoggued, String all, Class<?> expected) {

		Class<?> caught = null;

		try {
			super.startTransaction();
			if (all == "All") {
				List<Warden> wardens = this.wardenService.findAll();
				for (Warden w : wardens) {
					super.authenticate(w.getUserAccount().getUsername());

					this.wardenService.deleteLoggedWarden();
					this.wardenService.flush();

					super.unauthenticate();
				}
			} else {
				super.authenticate(usernameLoggued);

				this.wardenService.deleteLoggedWarden();

				super.unauthenticate();
			}
		} catch (Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}
}
