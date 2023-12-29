package com.br.stream.processamentoDeVideo.useCase;

import com.br.stream.processamentoDeVideo.adapters.Job;
import com.br.stream.processamentoDeVideo.infra.processarMidias.S3.EndpointS3;
import com.br.stream.processamentoDeVideo.infra.processarMidias.fabrica.HLSJob;
import com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida.AppleHLSContainer;
import com.br.stream.processamentoDeVideo.view.DTO.ProcessarMidiasDTO;
import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.mediaconvert.model.Output;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroup;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles(value = "test")
class CriarJobParaProcessamentoDeMidiasTest {

	CriarJobParaProcessamentoDeMidias criarJobParaProcessamentoDeMidias;

	@Mock
	Job midiaJob;

	AutoCloseable mock;

	@BeforeEach
	void setup() {
		mock = MockitoAnnotations.openMocks(this);
		criarJobParaProcessamentoDeMidias = new CriarJobParaProcessamentoDeMidias(midiaJob);
	}

	@AfterEach
	void fecharMock() throws Exception {
		mock.close();
	}

	@Test
	public void deveCriarUmNovoJobParaIniciarOProcessamentoDaMidia() {
		//Arrange
		var processarMidasForm = new ProcessarMidiasForm("video-test.mp4");

		//Act
		this.criarJobParaProcessamentoDeMidias.criarNovoJob(processarMidasForm);

		//Assert
		verify(midiaJob, times(1)).criar("video-test.mp4");

	}

}
