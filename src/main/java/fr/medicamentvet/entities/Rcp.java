package fr.medicamentvet.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * This class describes the Résumé des caractéristiques du produit (RCP): the
 * characteristics of a medicament.
 */
@JsonbPropertyOrder({ "imageURLList", "dateValidation", "lienRcp", "titreList", "contenuList" })
public class Rcp {

	private List<String> imageURLList;
	private LocalDate dateValidation;
	private String lienRcp;
	private List<String> titreList;
	private List<String> contenuList;

	public Rcp() {
		super();
	}

	public Rcp(List<String> imageURLList, LocalDate dateValidation, String lienRcp, List<String> titreList,
			List<String> contenuList) {
		super();
		this.imageURLList = imageURLList;
		this.dateValidation = dateValidation;
		this.lienRcp = lienRcp;
		this.titreList = titreList;
		this.contenuList = contenuList;
	}

	public List<String> getImageURLList() {
		return imageURLList;
	}

	public void setImageURLList(List<String> imageURLList) {
		this.imageURLList = imageURLList;
	}

	public LocalDate getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(LocalDate dateValidation) {
		this.dateValidation = dateValidation;
	}

	public String getLienRcp() {
		return lienRcp;
	}

	public void setLienRcp(String lienRcp) {
		this.lienRcp = lienRcp;
	}

	public List<String> getTitreList() {
		return titreList;
	}

	public void setTitreList(List<String> titreList) {
		this.titreList = titreList;
	}

	public List<String> getContenuList() {
		return contenuList;
	}

	public void setContenuList(List<String> contenuList) {
		this.contenuList = contenuList;
	}
}