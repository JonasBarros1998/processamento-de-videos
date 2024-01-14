package com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida;

import com.br.stream.processamentoDeVideo.infra.processarMidias.BitRate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.mediaconvert.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class AppleHLSH264Test {

	static AppleHLSH264 appleHLSH264;

	@BeforeAll
	static void setup() {
		appleHLSH264 = new AppleHLSH264(
			"_264_720p",
			"_$dt$",
			1280,
			720,
			new BitRate(6000000));
	}

	@Test
	void deveCriarUmEncoderHLS264ComResolucao720p() {
		//Act
		var appleHLS = appleHLSH264.criar();

		//Assert
		assertThat(appleHLS.nameModifier()).isEqualTo("_264_720p");

		assertThat(appleHLS.videoDescription().scalingBehavior()).isEqualTo(ScalingBehavior.DEFAULT);

		assertThat(appleHLS.videoDescription().sharpness()).isEqualTo(50);

		assertThat(appleHLS.videoDescription().antiAlias()).isEqualTo(AntiAlias.ENABLED);

		assertThat(appleHLS.videoDescription().respondToAfd()).isEqualTo(RespondToAfd.NONE);

		assertThat(appleHLS.videoDescription().afdSignaling()).isEqualTo(AfdSignaling.NONE);

		assertThat(appleHLS.videoDescription().timecodeInsertion()).isEqualTo(VideoTimecodeInsertion.DISABLED);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().rateControlMode())
			.isEqualTo(H264RateControlMode.QVBR);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().parControl())
			.isEqualTo(H264ParControl.INITIALIZE_FROM_SOURCE);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().qualityTuningLevel())
			.isEqualTo(H264QualityTuningLevel.SINGLE_PASS);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().qvbrSettings().qvbrQualityLevel())
			.isEqualTo(7);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().qvbrSettings().qvbrQualityLevelFineTune())
			.isEqualTo(0.33);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().codecLevel())
			.isEqualTo(H264CodecLevel.AUTO);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().codecProfile())
			.isEqualTo(H264CodecProfile.HIGH);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().maxBitrate())
			.isEqualTo(6000000);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().framerateControl())
			.isEqualTo(H264FramerateControl.INITIALIZE_FROM_SOURCE);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().framerateControl())
			.isEqualTo(H264FramerateControl.INITIALIZE_FROM_SOURCE);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().gopSize())
			.isEqualTo(3.0);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().gopSizeUnits())
			.isEqualTo(H264GopSizeUnits.SECONDS);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().gopClosedCadence())
			.isEqualTo(1);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().gopSize())
			.isEqualTo(3.0);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().gopBReference())
			.isEqualTo(H264GopBReference.DISABLED);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().slowPal())
			.isEqualTo(H264SlowPal.DISABLED);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().syntax())
			.isEqualTo(H264Syntax.DEFAULT);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().numberReferenceFrames())
			.isEqualTo(3);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().dynamicSubGop())
			.isEqualTo(H264DynamicSubGop.ADAPTIVE);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().sceneChangeDetect())
			.isEqualTo(H264SceneChangeDetect.ENABLED);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().minIInterval())
			.isZero();

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().telecine())
			.isEqualTo(H264Telecine.NONE);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().framerateConversionAlgorithm())
			.isEqualTo(H264FramerateConversionAlgorithm.DUPLICATE_DROP);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().entropyEncoding())
			.isEqualTo(H264EntropyEncoding.CABAC);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().slices())
			.isEqualTo(1);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().unregisteredSeiTimecode())
			.isEqualTo(H264UnregisteredSeiTimecode.DISABLED);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().repeatPps())
			.isEqualTo(H264RepeatPps.DISABLED);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().adaptiveQuantization())
			.isEqualTo(H264AdaptiveQuantization.AUTO);

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().softness())
			.isZero();

		assertThat(appleHLS.videoDescription().codecSettings().h264Settings().interlaceMode())
			.isEqualTo(H264InterlaceMode.PROGRESSIVE);

	}


	@Test
	void deveCriarUmEncoderDeAudio() {
		//Act
		var streamAppleHLS = appleHLSH264.criar();
		var appleHLSAudio = streamAppleHLS.audioDescriptions().get(0);

		//Assert
		assertThat(appleHLSAudio.audioTypeControl()).isEqualTo(AudioTypeControl.FOLLOW_INPUT);

		assertThat(appleHLSAudio.languageCodeControl()).isEqualTo(AudioLanguageCodeControl.FOLLOW_INPUT);

		assertThat(appleHLSAudio.codecSettings().codec()).isEqualTo(AudioCodec.AAC);

		assertThat(appleHLSAudio.codecSettings().aacSettings().codecProfile()).isEqualTo(AacCodecProfile.LC);

		assertThat(appleHLSAudio.codecSettings().aacSettings().rateControlMode()).isEqualTo(AacRateControlMode.CBR);

		assertThat(appleHLSAudio.codecSettings().aacSettings().codingMode()).isEqualTo(AacCodingMode.CODING_MODE_2_0);

		assertThat(appleHLSAudio.codecSettings().aacSettings().sampleRate()).isEqualTo(48000);

		assertThat(appleHLSAudio.codecSettings().aacSettings().bitrate()).isEqualTo(96000);

		assertThat(appleHLSAudio.codecSettings().aacSettings().rawFormat()).isEqualTo(AacRawFormat.NONE);

		assertThat(appleHLSAudio.codecSettings().aacSettings().specification()).isEqualTo(AacSpecification.MPEG4);

		assertThat(appleHLSAudio.codecSettings().aacSettings().audioDescriptionBroadcasterMix())
			.isEqualTo(AacAudioDescriptionBroadcasterMix.NORMAL);
	}




}
