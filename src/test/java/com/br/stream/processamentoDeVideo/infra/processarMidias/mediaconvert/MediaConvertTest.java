package com.br.stream.processamentoDeVideo.infra.processarMidias.mediaconvert;

import com.br.stream.processamentoDeVideo.infra.processarMidias.mediaconvert.MediaConvert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class MediaConvertTest {

	MediaConvert mediaConvert;

	AutoCloseable mock;

	@BeforeEach
	void setup() {
		mock = MockitoAnnotations.openMocks(this);
		mediaConvert = new MediaConvert();
	}

	@AfterEach
	void fecharMock() throws Exception {
		mock.close();
	}

	@Test
	void deveCriarOAmbienteInicialDoMediaConvertParaIniciarOProcessamentoDasMidias() {
		//Act
		MediaConvertClient mediaConvertClient = mediaConvert.criar();

		//Assert
		assertThat(mediaConvertClient).isInstanceOf(MediaConvertClient.class);
	}

}
