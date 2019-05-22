package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Finder;
import domain.Prisoner;

@Component
@Scope(value = "singleton")
public class FinderManager {

	/** Logger. */
	private static Logger LOG = LoggerFactory.getLogger(FinderManager.class);

	/** JPA Persistence Unit. */
	@PersistenceContext(type = PersistenceContextType.EXTENDED, name = "booksPU")
	private EntityManager em;

	/** Hibernate Full Text Entity Manager. */
	private FullTextEntityManager ftem;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Prisoner> search(Finder finder) {
		// Create a Query Builder

		QueryBuilder qb = this.getFullTextEntityManager().getSearchFactory().buildQueryBuilder()
				.forEntity(Prisoner.class).get();
		// Create a Lucene Full Text Query

		org.apache.lucene.search.Query luceneQuery = qb.bool()
				.must(qb.keyword().onFields("name", "surname", "middlename").matching(finder.getKeyWord())
						.createQuery())
				.must(qb.keyword().onField("charge").matching(finder.getCharge()).createQuery()).createQuery();
		Query fullTextQuery = this.getFullTextEntityManager().createFullTextQuery(luceneQuery, Prisoner.class);
		// Run Query and print out results to console
		List<Prisoner> prisoners = fullTextQuery.getResultList();
		return prisoners;
	}

	/**
	 * Convenience method to get Full Test Entity Manager. Protected scope to
	 * assist mocking in Unit Tests.
	 *
	 * @return Full Text Entity Manager.
	 */

	protected FullTextEntityManager getFullTextEntityManager() {
		if (this.ftem == null)
			this.ftem = Search.getFullTextEntityManager(this.em);
		return this.ftem;
	}

	/**
	 * Get the JPA Entity Manager (required for the DBUnit Tests).
	 *
	 * @return Entity manager
	 */
	public EntityManager getEntityManager() {
		return this.em;
	}

	/**
	 * Sets the JPA Entity Manager (required to assist with mocking in Unit
	 * Test)
	 *
	 * @param em EntityManager
	 */
	protected void setEntityManager(EntityManager em) {
		this.em = em;
	}

}
