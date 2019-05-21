/*
 * SampleTest.java
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package lucene;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Prisoner;
import services.FinderService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class FinderTest extends AbstractTest {

	@Autowired
	private FinderService finderService;

	@Test
	public void SamplePositiveTest() {
		Finder finder = this.finderService.findOne(this.getEntityId("finder1"));
		List<Prisoner> res = this.finderService.search(finder);

		System.out.println(res);

		Assert.isTrue(true);
	}

}
