package com.br.stream.processamentoDeVideo.adaptadores;

import software.amazon.awssdk.services.mediaconvert.model.AudioSelector;
import software.amazon.awssdk.services.mediaconvert.model.CreateJobResponse;
import software.amazon.awssdk.services.mediaconvert.model.JobSettings;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroup;

import java.util.Map;

public interface Job {

	void criar(String midiaDeEntrada);

	JobSettings configurarJob(OutputGroup gruposDeSaida, Map<String, AudioSelector> trilhaDeAudio);

	OutputGroup criarCodecs();

	CreateJobResponse construirJob(JobSettings job);

}
