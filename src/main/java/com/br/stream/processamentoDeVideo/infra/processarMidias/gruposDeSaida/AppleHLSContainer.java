package com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida;

import com.br.stream.processamentoDeVideo.infra.processarMidias.S3.EndpointS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.mediaconvert.model.*;

@Component
public class AppleHLSContainer {

	private final EndpointS3 endpointS3;

	@Autowired
	AppleHLSContainer(EndpointS3 endpointS3) {
		this.endpointS3 = endpointS3;
	}

	public OutputGroup criar(Output... gruposDeSaida) {
		return this.appleHLSContainer(gruposDeSaida);
	}

	private OutputGroup appleHLSContainer(Output... gruposDeSaida) {

		return OutputGroup.builder().name("Apple HLS").customName("HLS Container")
			.outputGroupSettings(OutputGroupSettings.builder().type(OutputGroupType.HLS_GROUP_SETTINGS)
				.hlsGroupSettings(HlsGroupSettings.builder()
					.directoryStructure(HlsDirectoryStructure.SINGLE_DIRECTORY)
					.manifestDurationFormat(HlsManifestDurationFormat.INTEGER)
					.streamInfResolution(HlsStreamInfResolution.INCLUDE)
					.clientCache(HlsClientCache.ENABLED)
					.captionLanguageSetting(HlsCaptionLanguageSetting.NONE)
					.manifestCompression(HlsManifestCompression.NONE)
					.codecSpecification(HlsCodecSpecification.RFC_4281)
					.outputSelection(HlsOutputSelection.MANIFESTS_AND_SEGMENTS)
					.programDateTime(HlsProgramDateTime.EXCLUDE)
					.timedMetadataId3Frame(HlsTimedMetadataId3Frame.PRIV)
					.timedMetadataId3Period(10)
					.destination(endpointS3.criarEndpointDeSaida())
					.segmentControl(HlsSegmentControl.SEGMENTED_FILES)
					.minFinalSegmentLength((double) 0)
					.segmentLength(10)
					.minSegmentLength(0).build())
				.build())
			.outputs(gruposDeSaida).build();

	}

}
