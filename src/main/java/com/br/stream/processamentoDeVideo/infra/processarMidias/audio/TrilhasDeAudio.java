package com.br.stream.processamentoDeVideo.infra.processarMidias.audio;

import software.amazon.awssdk.services.mediaconvert.model.AudioDefaultSelection;
import software.amazon.awssdk.services.mediaconvert.model.AudioSelector;

import java.util.HashMap;
import java.util.Map;

public class TrilhasDeAudio {


	public Map<String, AudioSelector> criar() {
		Map<String, AudioSelector> seletoresDeAudio = new HashMap<>();

		seletoresDeAudio.put("Audio Selector 1",
			AudioSelector.builder().defaultSelection(AudioDefaultSelection.DEFAULT).offset(0).build());

		return seletoresDeAudio;
	}

}
