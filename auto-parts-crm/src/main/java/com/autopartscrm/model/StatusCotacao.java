package com.autopartscrm.model;

public enum StatusCotacao {
    ABERTA("ABERTA"),
    EM_ANALISE("EM_ANALISE"),
    APROVADA("APROVADA"),
    REJEITADA("REJEITADA"),
    CONCLUIDA("CONCLUIDA");
    
	private String status;
	
	private StatusCotacao(String status) {
		this.status = status;
	}

	public String asString() {
		return status;
	}
	
	public static StatusCotacao of(String status) {
		if (status == null) return null;
		
		return StatusCotacao.valueOf(status);
	}
}