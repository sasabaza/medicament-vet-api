package fr.medicamentvet.entities;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * This class describes the composition of a Medicament object.
 */
@JsonbPropertyOrder({ "idComposition", "substanceActive", "quantite", "unite" })
public class Composition {

	private int idComposition;
	private String substanceActive;
	private String quantite;
	private String unite;

	public Composition() {
		super();
	}

	public Composition(int idComposition, String substanceActive, String quantite, String unite) {
		super();
		this.idComposition = idComposition;
		this.substanceActive = substanceActive;
		this.quantite = quantite;
		this.unite = unite;
	}

	public int getIdComposition() {
		return idComposition;
	}

	public void setIdComposition(int idComposition) {
		this.idComposition = idComposition;
	}

	public String getSubstanceActive() {
		return substanceActive;
	}

	public void setSubstanceActive(String substanceActive) {
		this.substanceActive = substanceActive;
	}

	public String getQuantite() {
		return quantite;
	}

	public void setQuantite(String quantite) {
		this.quantite = quantite;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}
}