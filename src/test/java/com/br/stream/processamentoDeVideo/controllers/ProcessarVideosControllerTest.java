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

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles(value = "test")
class ProcessarVideosControllerTest {

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
	void deveChamarOMetodoCriarNovoJob() {
		//Arrange
		var processarMidiasForm = new ProcessarMidiasForm("midia-test.mp4");

		//Act
		this.processarMidiasController.processarVideos(processarMidiasForm);

		//Assert
		verify(criarJobParaProcessamentoDeMidias, times(1))
			.criarNovoJob(any(ProcessarMidiasForm.class));
	}


}
