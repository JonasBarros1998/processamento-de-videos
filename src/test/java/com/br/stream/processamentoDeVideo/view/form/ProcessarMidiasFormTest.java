package com.br.stream.processamentoDeVideo.view.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProcessarMidiasFormTest {

	@Test
	public void deveRetornarONomeDaMidia() {
		var processarMidias = new ProcessarMidiasForm("video/video-test", "video-test.mp4");

		assertThat(processarMidias.nomeDaMidia()).isEqualTo("video-test.mp4");
	}

}
