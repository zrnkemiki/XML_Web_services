package ftn.xscience.dto;

import ftn.xscience.model.TPublication;

public class PublicationDTO {

	private String title;
	
	private String author;
	
	public PublicationDTO(TPublication publication) {
		this.title = publication.getTitle();
		// TODO Auto-generated constructor stub
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
