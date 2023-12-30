package com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida;

import com.br.stream.processamentoDeVideo.infra.processarMidias.BitRate;
import com.br.stream.processamentoDeVideo.infra.processarMidias.S3.EndpointS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.mediaconvert.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class AppleHLSContainerTest {

	@Mock
	EndpointS3 endpointS3;

	AppleHLSContainer appleHLSContainer;

	@BeforeEach
	void setup() {
		this.endpointS3.setNomeDaMidiaOriginal("midia-teste.mp4");
		this.appleHLSContainer = new AppleHLSContainer(this.endpointS3);
	}

	@Test
	void deveCriarUmNovoContainerParaProcessamentoDeMidiasDoTipoAppleHLS() {
		//Arrange
		var resolucao480P = new AppleHLSH264(
			"_264_480p",
			"_$dt$",
			854,
			480,
			new BitRate(4000000));
		var container480P = resolucao480P.criar();

		var resolucao720P = new AppleHLSH264(
			"_264_720p",
			"_$dt$",
			1280,
			720,
			new BitRate(6000000));
		var container720P = resolucao720P.criar();

		//Act
		var container = this.appleHLSContainer.criar(container480P, container720P);

		//Assert
		assertThat(container).isInstanceOf(OutputGroup.class);
		assertThat(container.customName()).isEqualTo("HLS Container");
		assertThat(container.name()).isEqualTo("Apple HLS");

		assertThat(container.outputGroupSettings().hlsGroupSettings().directoryStructure())
			.isEqualTo(HlsDirectoryStructure.SINGLE_DIRECTORY);

		assertThat(container.outputGroupSettings().hlsGroupSettings().streamInfResolution())
			.isEqualTo(HlsStreamInfResolution.INCLUDE);

		assertThat(container.outputGroupSettings().hlsGroupSettings().clientCache())
			.isEqualTo(HlsClientCache.ENABLED);

		assertThat(container.outputGroupSettings().hlsGroupSettings().captionLanguageSetting())
			.isEqualTo(HlsCaptionLanguageSetting.NONE);

		assertThat(container.outputGroupSettings().hlsGroupSettings().manifestCompression())
			.isEqualTo(HlsManifestCompression.NONE);

		assertThat(container.outputGroupSettings().hlsGroupSettings().codecSpecification())
			.isEqualTo(HlsCodecSpecification.RFC_4281);

		assertThat(container.outputGroupSettings().hlsGroupSettings().outputSelection())
			.isEqualTo(HlsOutputSelection.MANIFESTS_AND_SEGMENTS);

		assertThat(container.outputGroupSettings().hlsGroupSettings().programDateTime())
			.isEqualTo(HlsProgramDateTime.EXCLUDE);

		assertThat(container.outputGroupSettings().hlsGroupSettings().timedMetadataId3Frame())
			.isEqualTo(HlsTimedMetadataId3Frame.PRIV);

		assertThat(container.outputGroupSettings().hlsGroupSettings().timedMetadataId3Period())
			.isEqualTo(10);

		assertThat(container.outputGroupSettings().hlsGroupSettings().segmentControl())
			.isEqualTo(HlsSegmentControl.SEGMENTED_FILES);

		assertThat(container.outputGroupSettings().hlsGroupSettings().minFinalSegmentLength())
			.isZero();

		assertThat(container.outputGroupSettings().hlsGroupSettings().segmentLength())
			.isEqualTo(10);

		assertThat(container.outputGroupSettings().hlsGroupSettings().minSegmentLength())
			.isZero();

		assertThat(container).isInstanceOf(OutputGroup.class);
	}

}
