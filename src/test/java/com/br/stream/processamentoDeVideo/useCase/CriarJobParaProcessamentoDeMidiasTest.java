package com.br.stream.processamentoDeVideo.useCase;

import com.br.stream.processamentoDeVideo.adapters.Job;
import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasFormTest;
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
public class CriarJobParaProcessamentoDeMidiasTest {

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
		var destino = "%s/midia-test.mp4".formatted(UUID.randomUUID().toString());
		var processarMidiasForm = new ProcessarMidiasForm(destino, "midia-test.mp4");

		//Act
		this.criarJobParaProcessamentoDeMidias.criarNovoJob(processarMidiasForm);

		//Assert
		verify(midiaJob, times(1)).criar(any(String.class));

	}

}
