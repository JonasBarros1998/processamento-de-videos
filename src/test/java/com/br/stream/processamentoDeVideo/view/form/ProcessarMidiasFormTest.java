package com.br.stream.processamentoDeVideo.view.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProcessarMidiasFormTest {

	@Test
	void deveRetornarONomeDaMidia() {
		var processarMidias = new ProcessarMidiasForm("midia-test.mp4");

		assertThat(processarMidias.nomeDaMidia()).isEqualTo("midia-test.mp4");
	}

}
