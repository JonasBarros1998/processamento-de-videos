package com.br.stream.processamentoDeVideo.controllers;


import com.br.stream.processamentoDeVideo.useCase.CriarJobParaProcessamentoDeMidias;
import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessarVideosController {

	private CriarJobParaProcessamentoDeMidias criarJobParaProcessamentoDeMidias;

	@Autowired
	ProcessarVideosController(CriarJobParaProcessamentoDeMidias criarJobParaProcessamentoDeMidias) {
		this.criarJobParaProcessamentoDeMidias = criarJobParaProcessamentoDeMidias;
	}

	@SqsListener("processamento-de-videos")
	public void processarVideos(ProcessarMidiasForm processarMidiasForm) {
		this.criarJobParaProcessamentoDeMidias.criarNovoJob(processarMidiasForm);
	}
}
