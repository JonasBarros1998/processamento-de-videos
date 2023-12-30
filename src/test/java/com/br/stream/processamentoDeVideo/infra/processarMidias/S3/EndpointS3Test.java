package com.br.stream.processamentoDeVideo.infra.processarMidias.S3;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
public class EndpointS3Test {

	@Autowired
	EndpointS3 endpointS3;

	String nomeDaMidia = "midia-teste.mp4";

	@Test
	void deveCriarEndpointDeEntradaParaBuscarAMidiaNoBucketS3() {
		//Arrange
		this.endpointS3.setNomeDaMidiaOriginal(nomeDaMidia);

		//Act
		String endpoint = this.endpointS3.criarEndpointDeEntrada();

		//Assert
		assertThat(endpoint).isEqualTo("s3://midias-nao-processadas-testes/midia-teste.mp4");
	}

	@Test
	void deveCriarEndpointParaSalvarMidiaNoBucketAposElaSerProcessada() {
		//Arrange
		this.endpointS3.setNomeDaMidiaOriginal(nomeDaMidia);

		//Act
		String endpoint = this.endpointS3.criarEndpointDeSaida();

		//Assert
		assertThat(endpoint).contains("s3://midias-processadas-testes/midia-teste.mp4");
	}

	@Test
	void deveRetornarONomeDaMidia() {
		assertThat(this.endpointS3.getNomeDaMidiaOriginal()).isEqualTo("midia-teste.mp4");
	}

}
