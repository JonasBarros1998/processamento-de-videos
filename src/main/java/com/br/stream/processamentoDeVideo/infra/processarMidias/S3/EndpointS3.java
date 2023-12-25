package com.br.stream.processamentoDeVideo.infra.processarMidias.S3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EndpointS3 {

	private String nomeDaMidiaOriginal;

	@Value("${aplicacao.s3.buckets.videos-nao-processados}")
	private String videosNaoProcessadosBucket;


	@Value("${aplicacao.s3.buckets.videos-processados}")
	private String videosProcessadosBucket;

	private final String endpointInicial = "s3://";

	public String criarEndpointDeEntrada() {
		return "%s%s/%s".formatted(this.endpointInicial, this.videosNaoProcessadosBucket, this.nomeDaMidiaOriginal);
	}

	public String criarEndpointDeSaida() {

		return "%s%s/%s-%s/".formatted(
			this.endpointInicial,
			this.videosProcessadosBucket,
			nomeDaMidiaOriginal,
			UUID.randomUUID().toString());
	}

	public void setNomeDaMidiaOriginal(String nomeDaMidiaOriginal) {
		this.nomeDaMidiaOriginal = nomeDaMidiaOriginal;
	}

	public String getNomeDaMidiaOriginal() {
		return nomeDaMidiaOriginal;
	}
}

