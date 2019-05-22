package lucene;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class LuceneExample {

	public static final File INDEX_DIRECTORY = new File("IndexDirectory");

	public void createIndex() {

		System.out.println("-- Indexing --");

		try {
			// JDBC Section
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Assuming database bookstore exists
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acme-alcatraz", "root", "root");
			Statement stmt = conn.createStatement();
			String sql = "select DISTINCT p.* from Prisoner p join prisoner_charges pc on pc.prisoner=p.id join charge c"
					+ " on c.id=pc.charges where p.freedom=0";
			ResultSet rs = stmt.executeQuery(sql);

			// Lucene Section
			Directory directory = new SimpleFSDirectory(LuceneExample.INDEX_DIRECTORY);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			@SuppressWarnings("deprecation")
			IndexWriter iWriter = new IndexWriter(directory, analyzer, true, MaxFieldLength.UNLIMITED);

			// Looping through resultset and adding to index file
			int count = 0;
			while (rs.next()) {
				Document doc = new Document();

				doc.add(new Field("name", rs.getString("name"), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("surname", rs.getString("surname"), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("ticker", rs.getString("ticker"), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("middle_name", rs.getString("middle_name"), Field.Store.YES, Field.Index.ANALYZED));

				// Adding doc to iWriter
				iWriter.addDocument(doc);
				count++;
			}

			System.out.println(count + " record indexed");

			// Closing iWriter
			iWriter.optimize();
			iWriter.commit();
			iWriter.close();

			// Closing JDBC connection
			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void search(String keyword, String charge) {

		System.out.println("-- Seaching --");

		try {

			// Searching
			IndexReader reader = IndexReader.open(FSDirectory.open(LuceneExample.INDEX_DIRECTORY), true);
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			// MultiFieldQueryParser is used to search multiple fields
			String[] filesToSearch = { "name", "middle_name", "surname", "ticker" };
			MultiFieldQueryParser mqp = new MultiFieldQueryParser(Version.LUCENE_30, filesToSearch, analyzer);

			// Sirve para hacer el LIKE del filter
			mqp.setAllowLeadingWildcard(true);

			Query query = mqp.parse(keyword);// search the given keyword

			System.out.println("query >> " + query);

			TopDocs hits = searcher.search(query, 100); // run the query

			System.out.println("Results found >> " + hits.totalHits);

			for (int i = 0; i < hits.totalHits; i++) {
				Document doc = searcher.doc(hits.scoreDocs[i].doc);// get the
																	// next
																	// document
				System.out.println(doc.get("name") + " " + doc.get("middle_name") + " " + " " + doc.get("surname"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		LuceneExample obj = new LuceneExample();

		// creating index
		obj.createIndex();

		// searching keyword
		obj.search("s", "");

		// using wild card serach
		obj.search("*s*", "");
	}
}
