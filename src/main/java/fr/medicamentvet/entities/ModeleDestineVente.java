package fr.medicamentvet.entities;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * This class is ModeleDestineVente object.
 */
@JsonbPropertyOrder({ "idMdv", "libelle", "codeGTIN", "numeroAMM" })
public class ModeleDestineVente {

	private int idMdv;
	private String libelle;
	private String codeGTIN;
	private String numeroAMM;

	public ModeleDestineVente() {
		super();
	}

	public ModeleDestineVente(int idMdv, String libelle, String codeGTIN, String numeroAMM) {
		super();
		this.idMdv = idMdv;
		this.libelle = libelle;
		this.codeGTIN = codeGTIN;
		this.numeroAMM = numeroAMM;
	}

	public int getIdMdv() {
		return idMdv;
	}

	public void setIdMdv(int idMdv) {
		this.idMdv = idMdv;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCodeGTIN() {
		return codeGTIN;
	}

	public void setCodeGTIN(String codeGTIN) {
		this.codeGTIN = codeGTIN;
	}

	public String getNumeroAMM() {
		return numeroAMM;
	}

	public void setNumeroAMM(String numeroAMM) {
		this.numeroAMM = numeroAMM;
	}
}