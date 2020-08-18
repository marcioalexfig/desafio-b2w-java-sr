package br.com.b2w.marcioalex.dominio;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Planeta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@Id
	private String id;
	private String nome;
	private String terreno;
	private String clima;
	private Integer qtdFilmes;
	
	
	public Planeta() {}
	
	public Planeta(String nome, String clima, String terreno, Integer qtdFimes) {
		this.setClima(clima);
		this.setNome(nome);
		this.setTerreno(terreno);	
		this.setQtdFilmes(qtdFimes);
	}
	
	public Integer getQtdFilmes() {
		return qtdFilmes;
	}

	public void setQtdFilmes(Integer qtdFilmes) {
		this.qtdFilmes = qtdFilmes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clima == null) ? 0 : clima.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((terreno == null) ? 0 : terreno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planeta other = (Planeta) obj;
		if (clima == null) {
			if (other.clima != null)
				return false;
		} else if (!clima.equals(other.clima))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (terreno == null) {
			if (other.terreno != null)
				return false;
		} else if (!terreno.equals(other.terreno))
			return false;
		return true;
	}
	
	
	
}
