package com.br.stream.processamentoDeVideo.view.DTO;

import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;

public class ProcessarMidiasDTO {
	private String processarMidias;

	public ProcessarMidiasDTO(String processarMidias) {
		this.processarMidias = processarMidias;
	}

	public static ProcessarMidiasDTO converterProcessarMidiasFormParaProcessarMidiasDTO(ProcessarMidiasForm processarMidiasForm) {

		return new ProcessarMidiasDTO(
			processarMidiasForm.nomeDaMidia()
		);
	}

	public String getProcessarMidias() {
		return processarMidias;
	}


}
