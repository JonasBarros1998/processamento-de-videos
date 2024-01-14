package com.br.stream.processamentoDeVideo.view.DTO;

import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;

public class ProcessarMidiasDTO {
	private String localizacao;

	private String nomeDaMidia;

	public ProcessarMidiasDTO(String localizacao, String nomeDaMidia) {
		this.localizacao = localizacao;
		this.nomeDaMidia = nomeDaMidia;
	}

	public static ProcessarMidiasDTO converterProcessarMidiasFormParaProcessarMidiasDTO(ProcessarMidiasForm processarMidiasForm) {

		return new ProcessarMidiasDTO(
			processarMidiasForm.destino(),
			processarMidiasForm.nomeDaMidia()
		);
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public String getNomeDaMidia() {
		return nomeDaMidia;
	}


}
