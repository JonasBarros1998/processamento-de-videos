package com.br.stream.processamentoDeVideo.controllers;

import com.br.stream.processamentoDeVideo.useCase.CriarJobParaProcessamentoDeMidias;
import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles(value = "test")
public class ProcessarVideosControllerTest {

	AutoCloseable mock;

	private ProcessarMidiasController processarMidiasController;

	@Mock
	CriarJobParaProcessamentoDeMidias criarJobParaProcessamentoDeMidias;

	@BeforeEach
	void setup() {
		mock = MockitoAnnotations.openMocks(this);
		processarMidiasController = new ProcessarMidiasController(criarJobParaProcessamentoDeMidias);
	}

	@AfterEach
	void fecharMock() throws Exception {
		mock.close();
	}

	@Test
	public void deveChamarOMetodoCriarNovoJob() {
		//Arrange
		var destino = "%s/midia-test.mp4".formatted(UUID.randomUUID().toString());
		var processarMidiasForm = new ProcessarMidiasForm(destino, "midia-test.mp4");

		//Act
		this.processarMidiasController.processarVideos(processarMidiasForm);

		//Assert
		verify(criarJobParaProcessamentoDeMidias, times(1))
			.criarNovoJob(any(ProcessarMidiasForm.class));
	}


}
