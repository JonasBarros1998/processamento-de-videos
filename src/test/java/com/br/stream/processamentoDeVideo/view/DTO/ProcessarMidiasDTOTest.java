package com.br.stream.processamentoDeVideo.view.DTO;

import com.br.stream.processamentoDeVideo.view.form.ProcessarMidiasForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
public class ProcessarMidiasDTOTest {

	@Test
	public void deveConverterProcessarMidiasFormParaProcessarMidiasDTO() {

		//Arrange
		var processarMidasForm = new ProcessarMidiasForm("video/video-test", "video-test.mp4");

		//Act
		var processarMidias = ProcessarMidiasDTO
			.converterProcessarMidiasFormParaProcessarMidiasDTO(processarMidasForm);

		//Assert
		assertThat(processarMidias.getLocalizacao()).isEqualTo("video/video-test");
		assertThat(processarMidias).isInstanceOf(ProcessarMidiasDTO.class);

	}
}
