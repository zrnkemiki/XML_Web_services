package ftn.xscience.service;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xscience.repository.CoverLetterRepository;
import ftn.xscience.utils.dom.DOMParser;

@Service
public class CoverLetterService {

	@Autowired
	ServletContext context;
	
	@Autowired
	CoverLetterRepository coverLetterRepository;
	
	@Autowired
	DOMParser domParser;
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/coverLetter.xsd";
}
