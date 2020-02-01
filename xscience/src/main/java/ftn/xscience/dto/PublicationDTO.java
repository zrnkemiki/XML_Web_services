package ftn.xscience.dto;

import ftn.xscience.model.publication.Publication;

public class PublicationDTO {

	private String title;
	
	private String author;
	
	private String status;
	
	public PublicationDTO(Publication publication) {
		this.title = publication.getTitle().getValue();
		this.status = publication.getMetaData().getStatus().getValue();
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
