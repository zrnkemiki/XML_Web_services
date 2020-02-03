package ftn.xscience.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.repository.RepoProba;
import ftn.xscience.utils.template.RDFManager;

@RestController
@RequestMapping("/proba")
public class ProbaController {
	
	@Autowired
	RepoProba repo;
	
	//@Autowired
	RDFManager rdfManager = new RDFManager();
	
	// MORA DA BUDE /REST ZA AUTORIZACIJU
	@PreAuthorize("hasRole('EDITOR')")
	@GetMapping(value="/doc")
	public ResponseEntity<String> getDoc(@RequestHeader("Authorization") String token) {
		try {
			repo.retrieveDocument();
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value="/user")
	public ResponseEntity<String> getUser() {
		
		return null;
	}
	
	@PostMapping(value="/proba")
	public ResponseEntity<?> createCol() {
		repo.createCol();
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@GetMapping(value="/advanced-search")
	public ResponseEntity<?> searchForPublication(@RequestParam Map<String,String> params) throws JAXBException, XMLDBException {
		List<String> results = null;
		System.out.println(params);
		/*
		 * ---------------------------------ZA PUNJENJE FUSEKIJA----------------------------------------
		 * Rucno treba da se napravi data set "MetaDataSet" i izaberes opciju u sredini (prvi Persist)
		 * u svaki xml treba dodati <?xml-stylesheet type="text/xsl" href="../xsl/grddl.xsl"?> odma u drugoj liniji
		 * i xmlns:xs = "http://www.w3.org/2001/XMLSchema#" tu medju deklaracijama
		 * na datume dodati atribut datatype="xs:date"
		 * i onda se samo poziva metoda rdfManager.extractMetadata(publicationFile, rdfFilePath, grddlFilePath) -> ima u PubService save()
		
		 * ---------------------------------PUTANJE ZA SPARQL------------------------------------------
		 * pretraga po jednom -> advanced-search?paperType="RESEARCH_PAPER"
		 * po dva ili vise samo dodas izmedju & -> advanced-search?fieldOfStudy="Geopolitics"&paperType="RESEARCH_PAPER"
		 * AUTOR sa @ -> advanced-search?authoredBy=@"user-author-gmail.com"
		 * OR odvajas tacka-zarezom -> advanced-search?status="ACCEPTED";"REJECTED";"UPLOADED"
		 * NOT sa ! -> advanced-search?status=!"ACCEPTED"
		 * DATUMI oznaceni sa $ i stavlja se :
		 * OD-DO datum -> advanced-search?recieved=$"2017-01-01":"2018-01-01"
		 * VECE/MANJE/JEDNAKO ->advanced-search?recieved=$gt:"2017-01-01"
		 * 
		 * Pokretanje -> results = rdfManager.runSPARQL(params);
		 * 
		 * ---------------------------------NAPOMENA----------------------------------------
		 * Kombinujes ih onda tako sto ih samo spajas sa &(Nisam jos detaljno testirao, moguce da ima bagova)
		 * Vrednosti moraju u putanji da budu pod navodnicima, tokeni ne smeju(@,$,:,;)
		
		*/
		
		try {
			results = rdfManager.runSPARQL(params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String publication : results) {
			System.out.println(publication);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//@GetMapping(value="/extract")
	@PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> extractMetadata(@RequestParam("file") MultipartFile publicationFile) {
	
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value="/sparql")
	public ResponseEntity<String> sparqlProba() {
			
			try {
				repo.sparqlProba("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
