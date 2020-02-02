package ftn.xscience.dto;

import java.util.ArrayList;
import java.util.List;

import ftn.xscience.model.publication.Publication;

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
	

}
