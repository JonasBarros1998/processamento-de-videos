package com.br.stream.processamentoDeVideo.infra.processarMidias.audio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.mediaconvert.model.AudioDefaultSelection;
import software.amazon.awssdk.services.mediaconvert.model.AudioSelector;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
public class TrilhasDeAudioTest {

	TrilhasDeAudio trilhasDeAudio = new TrilhasDeAudio();

	@Test
	void deveCriarAsTrilhasDeAudio() {
		//Arrange
		var audio = new HashMap<String, AudioSelector>();
		audio.put("Audio Selector 1",
			AudioSelector.builder().defaultSelection(AudioDefaultSelection.DEFAULT).offset(0).build());

		//Act
		var trilhas = this.trilhasDeAudio.criar();

		//Assert
		assertThat(trilhas).isEqualTo(audio);
		assertThat(trilhas.size()).isEqualTo(1);
	}
}
