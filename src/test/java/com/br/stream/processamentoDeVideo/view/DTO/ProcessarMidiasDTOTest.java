package com.br.stream.processamentoDeVideo.view.DTO;

import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
class ProcessarMidiasDTOTest {

	@Test
	void deveConverterProcessarMidiasFormParaProcessarMidiasDTO() {

		//Arrange
		var processarMidasForm = new ProcessarMidiasForm("video-test.mp4");

		//Act
		var processarMidias = ProcessarMidiasDTO
			.converterProcessarMidiasFormParaProcessarMidiasDTO(processarMidasForm);

		//Assert
		assertThat(processarMidias.getProcessarMidias()).isEqualTo("video-test.mp4");
		assertThat(processarMidias).isInstanceOf(ProcessarMidiasDTO.class);

	}
}
