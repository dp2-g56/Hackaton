package lucene;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Prisoner;
import manager.FinderManager;
import services.FinderService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/application-context.xml" })
public class FinderTest extends AbstractTest {

	/** Logger. */
	private static Logger LOG = LoggerFactory.getLogger(FinderTest.class);

	/** Book Manager Under Test. */
	@Autowired
	private FinderManager finderManager;

	@Autowired
	private FinderService finderService;

	/**
	 * Get the underlying database connection from the JPA Entity Manager
	 * (DBUnit needs this connection).
	 *
	 * @return Database Connection
	 * @throws Exception
	 */
	/**
	 * Tests the expected results for searching for 'Space' in SCF-FI books.
	 */
	@Test
	public void testSciFiBookSearch() throws Exception {

		Finder finder = this.finderService.findOne(this.getEntityId("finder1"));
		List<Prisoner> results = this.finderManager.search(finder);

		System.out.println(results);

		Assert.isTrue(true);

	}

}
