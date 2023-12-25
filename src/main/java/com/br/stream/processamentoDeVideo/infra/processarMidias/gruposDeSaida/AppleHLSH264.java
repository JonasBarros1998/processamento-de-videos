package com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida;

import com.br.stream.processamentoDeVideo.infra.processarMidias.BitRate;
import software.amazon.awssdk.services.mediaconvert.model.*;

public class AppleHLSH264 {

	private final String nome;
	private final String segmento;

	private final Integer resolucaoX;

	private final Integer resolucaoY;

	private final BitRate bitRate;

	public AppleHLSH264(String nome, String segmento, Integer resolucaoX, Integer resolucaoY, BitRate bitRate) {
		this.nome = nome;
		this.segmento = segmento;
		this.resolucaoX = resolucaoX;
		this.resolucaoY = resolucaoY;
		this.bitRate = bitRate;
	}

	public Output criar() {
		var configuracao = this.iniciarConfiguracao();

		var estruturaDeProcessamento =
			this.criarContainer(configuracao)
				.criarContainerDeVideo(configuracao)
				.criarContainerDeAudio(configuracao);

		return estruturaDeProcessamento.build();

	}


	Output.Builder iniciarConfiguracao() {
		return Output.builder().nameModifier(this.nome).outputSettings(OutputSettings.builder()
			.hlsSettings(HlsSettings.builder().segmentModifier(this.segmento).audioGroupId("program_audio")
				.iFrameOnlyManifest(HlsIFrameOnlyManifest.EXCLUDE).build())
			.build());

	}


	AppleHLSH264 criarContainer(Output.Builder container) {
		container
			.containerSettings(ContainerSettings.builder().container(ContainerType.M3_U8)
				.m3u8Settings(M3u8Settings.builder().audioFramesPerPes(4)
					.pcrControl(M3u8PcrControl.PCR_EVERY_PES_PACKET).pmtPid(480)
					.programNumber(1).patInterval(0).pmtInterval(0).scte35Source(M3u8Scte35Source.NONE)
					.scte35Pid(500).nielsenId3(M3u8NielsenId3.NONE).timedMetadata(TimedMetadata.NONE)
					.timedMetadataPid(502).videoPid(481)
					.audioPids(482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492).build())
				.build());

		return this;
	}

	AppleHLSH264 criarContainerDeVideo(Output.Builder container) {

		container
			.videoDescription(
				VideoDescription.builder().width(this.resolucaoX).height(resolucaoY)
					.scalingBehavior(ScalingBehavior.DEFAULT).sharpness(50)
					.antiAlias(AntiAlias.ENABLED).respondToAfd(RespondToAfd.NONE).afdSignaling(AfdSignaling.NONE)
					.timecodeInsertion(VideoTimecodeInsertion.DISABLED)

					.codecSettings(VideoCodecSettings.builder().codec(VideoCodec.H_264)
						.h264Settings(H264Settings.builder()
							.rateControlMode(H264RateControlMode.QVBR)
							.parControl(H264ParControl.INITIALIZE_FROM_SOURCE)
							.qualityTuningLevel(H264QualityTuningLevel.SINGLE_PASS)
							.qvbrSettings(H264QvbrSettings.builder()
								.qvbrQualityLevel(7)
								.qvbrQualityLevelFineTune(0.33).build())
							.codecLevel(H264CodecLevel.AUTO)
							.codecProfile(H264CodecProfile.HIGH)
							.maxBitrate(this.bitRate.getMaxBitRate())
							.framerateControl(H264FramerateControl.INITIALIZE_FROM_SOURCE)
							.gopSize(3.0).gopSizeUnits(H264GopSizeUnits.SECONDS)
							.numberBFramesBetweenReferenceFrames(2).gopClosedCadence(1)
							.gopBReference(H264GopBReference.DISABLED)
							.slowPal(H264SlowPal.DISABLED).syntax(H264Syntax.DEFAULT)
							.numberReferenceFrames(3).dynamicSubGop(H264DynamicSubGop.ADAPTIVE)
							.fieldEncoding(H264FieldEncoding.PAFF)
							.sceneChangeDetect(H264SceneChangeDetect.ENABLED).minIInterval(0)
							.telecine(H264Telecine.NONE)
							.framerateConversionAlgorithm(H264FramerateConversionAlgorithm.DUPLICATE_DROP)
							.entropyEncoding(H264EntropyEncoding.CABAC).slices(1)
							.unregisteredSeiTimecode(H264UnregisteredSeiTimecode.DISABLED)
							.repeatPps(H264RepeatPps.DISABLED)
							.adaptiveQuantization(H264AdaptiveQuantization.AUTO)
							.softness(0).interlaceMode(H264InterlaceMode.PROGRESSIVE).build())
						.build())
					.build());

		return this;
	}


	Output.Builder criarContainerDeAudio(Output.Builder container) {
		return container
			.audioDescriptions(AudioDescription.builder().audioTypeControl(AudioTypeControl.FOLLOW_INPUT)
				.languageCodeControl(AudioLanguageCodeControl.FOLLOW_INPUT)
				.codecSettings(AudioCodecSettings.builder().codec(AudioCodec.AAC).aacSettings(AacSettings
						.builder().codecProfile(AacCodecProfile.LC).rateControlMode(AacRateControlMode.CBR)
						.codingMode(AacCodingMode.CODING_MODE_2_0).sampleRate(48000).bitrate(96000)
						.rawFormat(AacRawFormat.NONE).specification(AacSpecification.MPEG4)
						.audioDescriptionBroadcasterMix(AacAudioDescriptionBroadcasterMix.NORMAL).build())
					.build())
				.build());
	}

}
