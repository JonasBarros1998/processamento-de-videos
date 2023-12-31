package com.br.stream.processamentoDeVideo.adapters;

import software.amazon.awssdk.services.mediaconvert.model.AudioSelector;
import software.amazon.awssdk.services.mediaconvert.model.CreateJobResponse;
import software.amazon.awssdk.services.mediaconvert.model.JobSettings;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroup;

import java.util.Map;

public interface Job {

	CreateJobResponse criar(String midiaDeEntrada);

	JobSettings configurarJob(OutputGroup gruposDeSaida, Map<String, AudioSelector> trilhaDeAudio);

	OutputGroup criarCodecs();

	CreateJobResponse construirJob(JobSettings job);

}
