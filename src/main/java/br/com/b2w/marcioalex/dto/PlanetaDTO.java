package br.com.b2w.marcioalex.dto;

public class PlanetaDTO {
	private String nome;
	private String terreno;
	private String clima;
	private Integer qtdFilmes;
	private String uri;
	
	
	public PlanetaDTO(String nome, String terreno, String clima, Integer qtdFilmes, String uri) {
		super();
		this.nome = nome;
		this.terreno = terreno;
		this.clima = clima;
		this.qtdFilmes = qtdFilmes;
		this.uri = uri;
	}
	public Integer getQtdFilmes() {
		return qtdFilmes;
	}
	public void setQtdFilmes(Integer qtdFilmes) {
		this.qtdFilmes = qtdFilmes;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
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
	
	
}
