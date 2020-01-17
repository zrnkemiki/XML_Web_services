package ftn.xscience.service;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xscience.repository.ReviewRepository;
import ftn.xscience.utils.dom.DOMParser;

@Service
public class ReviewService {

	@Autowired
	ServletContext context;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	DOMParser domParser;
	
	private static String schemaLocation = "WEB-INF/classes/data/xsd/review.xsd";
}
