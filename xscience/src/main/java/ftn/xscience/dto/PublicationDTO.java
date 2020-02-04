package ftn.xscience.dto;

import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.publication.Publication.Author;

public class PublicationDTO {

	private String title;
	
	private String author;
	
	private String status;
	
	public PublicationDTO(Publication publication) {
		this.title = publication.getTitle().getValue();
		this.status = publication.getMetaData().getStatus().getValue();
		
		String authors = "";
		for (Author author : publication.getAuthor()) {
			authors = authors + author.getName().getLastName() + " " + author.getName().getFirstName().substring(0, 1) + ". , ";
		}
		authors = authors.substring(0, authors.length() - 3);
		this.author = authors;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
