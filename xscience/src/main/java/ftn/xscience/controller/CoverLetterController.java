package ftn.xscience.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import ftn.xscience.service.CoverLetterService;
import ftn.xscience.utils.dom.DOMParser;

@RestController
@RequestMapping("/coverletter")
public class CoverLetterController {

	@Autowired
	CoverLetterService coverLetterService;

	@Autowired
	DOMParser domParser;

	@GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getCoverLetterById(@PathVariable("id") String coverletterId) {
		String transformedCL = coverLetterService.getTransformedCL(coverletterId);
		return new ResponseEntity<String>(transformedCL, HttpStatus.OK);
	}

	@PostMapping(value = "/uploadCoverLetter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadPublication(@RequestParam("file") MultipartFile coverLetter) throws IOException, SAXException, ParserConfigurationException, XMLDBException {
		String cvrLetterStr = new String(coverLetter.getBytes());
		System.out.println(cvrLetterStr);
		coverLetterService.saveCoverLetter(cvrLetterStr);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
