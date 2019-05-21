/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package queries;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.VisitorRepository;
import repositories.WardenRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Visitor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class StatisticsPerformanceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 

	@Autowired
	private WardenRepository		wardenRepository;

	@Autowired
	private VisitorRepository		visitorRepository;

	@Autowired
	private SessionFactory			sessionFactory;

	@Autowired
	private EntityManagerFactory	emf;

	private Logger					log	= Logger.getLogger(this.getClass().getName());


	// it using JUnit.

	@Test
	public void getVisitorsMostVisitsToAPrisonerTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<Visitor> a = this.wardenRepository.getVisitorsMostVisitsToAPrisoner();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getPrisonersWithMostVisitToAVisitorTest() {

		super.authenticate("visitor1");
		UserAccount u = LoginService.getPrincipal();
		int id = this.visitorRepository.getVisitorByUsername(u.getUsername()).getId();
		super.unauthenticate();
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getPrisonersWithMostVisitToAVisitor(862);
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getPrisonersWithVisitsToMostDifferentVisitorsTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getPrisonersWithVisitsToMostDifferentVisitors();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getVisitorsWithVisitsToMostDifferentPrisonersTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getVisitorsWithVisitsToMostDifferentPrisoners();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRatioOfVisitsWithReportTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRatioOfVisitsWithReport();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getGuardsWithTheLargestNumberOfReportsWrittenTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getGuardsWithTheLargestNumberOfReportsWritten();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRatioOfGuardsWithMoreThan50PercentOfVisitsWithReportTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRatioOfGuardsWithMoreThan50PercentOfVisitsWithReport();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRatioOfPrisonersWithoutVisitsLastMonthTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRatioOfPrisonersWithoutVisitsLastMonth();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRegularVisitorToAtLeastOnePrisonerTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRegularVisitorToAtLeastOnePrisoner();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRatioOfAvailableGuardsVsFutureVisitsWithoutGuardTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRatioOfAvailableGuardsVsFutureVisitsWithoutGuard();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getTop3PrisonersLowestCrimeRateTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getTop3PrisonersLowestCrimeRate();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRatioOfNonIsolatedVsIsolatedPrisoners() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRatioOfNonIsolatedVsIsolatedPrisoners();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getStatisticsCrimeRateTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float[] a = this.wardenRepository.getStatisticsCrimeRate();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getSocialWorkerMostActivitiesFullTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getSocialWorkerMostActivitiesFull();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getPrisonersMostRejectedRequestToDifferentActivitiesAndNoApprovedOnThoseActivitiesTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getPrisonersMostRejectedRequestToDifferentActivitiesAndNoApprovedOnThoseActivities();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getRatioSocialWorkersWithCurriculumTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			Float a = this.wardenRepository.getRatioSocialWorkersWithCurriculum();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getActivitiesLargestNumberPrisonersTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getActivitiesLargestNumberPrisoners();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getActivitiesLargestAvgCrimeRateTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getActivitiesLargestAvgCrimeRate();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getActivitiesSmallestAvgCrimeRateTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getActivitiesSmallestAvgCrimeRate();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getSocialWorkersLowestRatioPrisonersPerActivityTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getSocialWorkersLowestRatioPrisonersPerActivity();
		}

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void getActivitiesMostSearched() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getActivitiesMostSearched();
		}

		em.getTransaction().commit();
		em.close();

	}
	@Test
	public void getTop5PrisonersParticipatedMostActivitiesLastMonthTest() {
		this.log.info("... statistics ...");

		EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();

		for (Integer c = 1000; c > 0; c--) {
			List<String> a = this.wardenRepository.getTop5PrisonersParticipatedMostActivitiesLastMonth();
		}

		em.getTransaction().commit();
		em.close();

	}

}
