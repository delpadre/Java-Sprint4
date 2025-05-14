package com.autopartscrm.model;

public enum StatusPedido {
    PENDENTE("PENDENTE"),
    PROCESSANDO("PROCESSANDO"),
    ENVIADO("ENVIADO"),
    ENTREGUE("ENTREGUE"),
    CANCELADO("CANCELADO");
	
	private String status;
	
	private StatusPedido(String status) {
		this.status = status;
	}

	public String asString() {
		return status;
	}
	
	public static StatusPedido of(String status) {
		if (status == null) return null;
		
		return StatusPedido.valueOf(status);
	}
}