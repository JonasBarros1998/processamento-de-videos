package com.br.stream.processamentoDeVideo.useCase;

import com.br.stream.processamentoDeVideo.adapters.Job;
import com.br.stream.processamentoDeVideo.domain.ProcessarVideos;
import com.br.stream.processamentoDeVideo.view.DTO.ProcessarMidiasDTO;
import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarJobParaProcessamentoDeMidias {

	Job midiaJob;

	@Autowired
	public CriarJobParaProcessamentoDeMidias(Job midiaJob) {
		this.midiaJob = midiaJob;
	}

	public void criarNovoJob(ProcessarMidiasForm processarMidiasForm) {
		var processarMidiasDTO = ProcessarMidiasDTO.converterProcessarMidiasFormParaProcessarMidiasDTO(processarMidiasForm);
		var processarVideos = new ProcessarVideos(processarMidiasDTO.getLocalizacao());

		this.midiaJob.criar(processarVideos.getNomeDaMidia());
	}

}
