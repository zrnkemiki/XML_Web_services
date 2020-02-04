package ftn.xscience.dto;

public class ReviewDTO {
	private String title;
	private String author;
	private String originality;
	private String adequate_literature;
	private String methodology;
	private String inference;
	private String readability;
	private String recommendations_for_editor;
	private String comment_for_editor;
	private String comment_for_author;
	
	
	
	public ReviewDTO(String title, String author, String originality, String adequate_literature, String methodology,
			String inference, String readability, String recommendations_for_editor, String comment_for_editor,
			String comment_for_author) {
		super();
		this.title = title;
		this.author = author;
		this.originality = originality;
		this.adequate_literature = adequate_literature;
		this.methodology = methodology;
		this.inference = inference;
		this.readability = readability;
		this.recommendations_for_editor = recommendations_for_editor;
		this.comment_for_editor = comment_for_editor;
		this.comment_for_author = comment_for_author;
	}
	public ReviewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getOriginality() {
		return originality;
	}
	public void setOriginality(String originality) {
		this.originality = originality;
	}
	public String getAdequate_literature() {
		return adequate_literature;
	}
	public void setAdequate_literature(String adequate_literature) {
		this.adequate_literature = adequate_literature;
	}
	public String getMethodology() {
		return methodology;
	}
	public void setMethodology(String methodology) {
		this.methodology = methodology;
	}
	public String getInference() {
		return inference;
	}
	public void setInference(String inference) {
		this.inference = inference;
	}
	public String getReadability() {
		return readability;
	}
	public void setReadability(String readability) {
		this.readability = readability;
	}
	public String getRecommendations_for_editor() {
		return recommendations_for_editor;
	}
	public void setRecommendations_for_editor(String recommendations_for_editor) {
		this.recommendations_for_editor = recommendations_for_editor;
	}
	public String getComment_for_editor() {
		return comment_for_editor;
	}
	public void setComment_for_editor(String comment_for_editor) {
		this.comment_for_editor = comment_for_editor;
	}
	public String getComment_for_author() {
		return comment_for_author;
	}
	public void setComment_for_author(String comment_for_author) {
		this.comment_for_author = comment_for_author;
	}
}
