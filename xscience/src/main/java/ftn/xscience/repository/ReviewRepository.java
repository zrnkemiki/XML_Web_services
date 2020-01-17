package ftn.xscience.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ftn.xscience.util.xmldb.BasicXMLConnectionPool;

@Repository
public class ReviewRepository {

	private static String collectionId = "/db/data/reviews";
	
	@Autowired
	BasicXMLConnectionPool connectionPool;
}
