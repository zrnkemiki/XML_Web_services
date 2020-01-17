package ftn.xscience.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ftn.xscience.util.xmldb.BasicXMLConnectionPool;

@Repository
public class CoverLetterRepository {

	private static String collectionId = "/db/data/cover-letters";
	
	@Autowired
	BasicXMLConnectionPool connectionPool;
	
	
}
