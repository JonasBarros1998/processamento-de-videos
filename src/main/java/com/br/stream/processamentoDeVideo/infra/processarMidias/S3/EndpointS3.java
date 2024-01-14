package com.br.stream.processamentoDeVideo.infra.processarMidias.S3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class EndpointS3 {

	private String destino;

	@Value("${aplicacao.s3.buckets.videos-nao-processados}")
	private String videosNaoProcessadosBucket;

	@Value("${aplicacao.s3.buckets.videos-processados}")
	private String videosProcessadosBucket;

	private final String extensao = "mp4";

	private final String endpointInicial = "s3://";

	public String criarEndpointDeEntrada() {
		return "%s%s/%s.%s".formatted(this.endpointInicial, this.videosNaoProcessadosBucket, this.destino, this.extensao);
	}

	public String criarEndpointDeSaida() {
		return "%s%s/%s/".formatted(this.endpointInicial, this.videosProcessadosBucket, destino);
	}

	public void setNomeDaMidiaOriginal(String destino) {
		this.destino = destino;
	}

	public String getNomeDaMidiaOriginal() {
		return destino;
	}
}

