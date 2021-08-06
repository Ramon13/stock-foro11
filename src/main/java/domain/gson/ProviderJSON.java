package domain.gson;

import entity.Provider;

public class ProviderJSON {

	private Long id;
	private String name;
	private String cnpj;
	
	public ProviderJSON(Provider provider) {
		this.id = provider.getId();
		this.name = provider.getName();
		this.cnpj = provider.getCnpj();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
