package com.mathvideos.api.entity.enumerated;

public enum VideoVisibility {
	PUBLIC(0 , "Público"), 
	PRIVATE(1, "Privado"), 
	UNLISTED(2, "Não Listado");
	
	private Integer code;
	private String description;
	
	private VideoVisibility(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
