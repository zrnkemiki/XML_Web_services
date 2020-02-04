package ftn.xscience.dto;

import java.util.ArrayList;
import java.util.List;

import org.exist.xquery.TreatAsExpression;

import ftn.xscience.model.publication.Publication;
import ftn.xscience.model.publication.TAuthorInformation;
import ftn.xscience.model.review.Review;
import ftn.xscience.model.review.Review.Content;
import ftn.xscience.model.review.Review.Content.CommentForAuthor;
import ftn.xscience.model.review.Review.Content.Evaluation;
import ftn.xscience.model.review.Review.PublicationTitle;
import ftn.xscience.model.review.TCommentForEditor;
import ftn.xscience.model.review.TEditSuggestion;
import ftn.xscience.model.review.TRevisionEvaluation;
import ftn.xscience.model.user.TUser;

public class DTOConverter {

	public static ArrayList<PublicationDTO> convertPublicationsToDTO(List<Publication> publications){
		ArrayList<PublicationDTO> publicationsDTO = new ArrayList<PublicationDTO>();
		if(publications.size()!=0) {
			for (Publication publication : publications) {
				PublicationDTO temp = new PublicationDTO(publication);
				publicationsDTO.add(temp);
			}
		}
		return publicationsDTO;
	}
	
	public static Review convertReviewDTOtoReview(ReviewDTO reviewDTO, TUser user) {
		Review review = new Review();
		Content c = new Content();
		
		//TITLE & AUTHOR--------->>>
		TAuthorInformation author = user.getPersonalInformation();
		System.out.println("===-=-=-==-============");
		System.out.println(author.getAcademicDegree());
		System.out.println(author.getEmail());
		
		PublicationTitle publTittle = new PublicationTitle();
		publTittle.setValue(reviewDTO.getTitle());
		//---------------------------------------
		//EVALUATION----->
		Evaluation evaluation = new Evaluation();
		
		TRevisionEvaluation originality = TRevisionEvaluation.valueOf(reviewDTO.getOriginality());
		TRevisionEvaluation adequateLiterature = TRevisionEvaluation.valueOf(reviewDTO.getAdequate_literature());
		TRevisionEvaluation methodology = TRevisionEvaluation.valueOf(reviewDTO.getMethodology());
		TRevisionEvaluation inference =  TRevisionEvaluation.valueOf(reviewDTO.getInference());
		TRevisionEvaluation readability = TRevisionEvaluation.valueOf(reviewDTO.getReadability());
		
		evaluation.setOriginality(originality);
		evaluation.setAdequateLiterature(adequateLiterature);
		evaluation.setMethodology(methodology);
		evaluation.setInference(inference);
		evaluation.setReadability(readability);
		//--------------------------------------
		TCommentForEditor commentForEditor = new TCommentForEditor();
		commentForEditor.getContent().add(reviewDTO.getComment_for_editor());
		CommentForAuthor commentForAuthor = new CommentForAuthor();
		TEditSuggestion tEditSuggestion = new TEditSuggestion();
		tEditSuggestion.getContent().add(reviewDTO.getComment_for_author());
		commentForAuthor.getEditSuggestion().add(tEditSuggestion);
		
		c.setCommentForAuthor(commentForAuthor);
		c.setCommentForEditor(commentForEditor);
		c.setRecommendationForEditor(reviewDTO.getRecommendations_for_editor());
		c.setEvaluation(evaluation);
		c.setReviewer(author);
		
		review.setContent(c);
		review.setPublicationTitle(publTittle);
		
		return review;
	}
	

}
