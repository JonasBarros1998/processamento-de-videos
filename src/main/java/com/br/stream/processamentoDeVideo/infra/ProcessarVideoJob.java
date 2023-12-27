package com.br.stream.processamentoDeVideo.infra;

import com.br.stream.processamentoDeVideo.infra.processarMidias.BitRate;
import com.br.stream.processamentoDeVideo.infra.processarMidias.fabrica.HLSJob;
import com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida.AppleHLSH264;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.*;

import java.net.URI;

@Component
public class ProcessarVideoJob {


	HLSJob appleHLSContainer;

	@Autowired
	ProcessarVideoJob(HLSJob appleHLSContainer) {
		this.appleHLSContainer = appleHLSContainer;
	}
	void main() {}

	public String createMediaJob() {
		Region region = Region.US_EAST_1;
		MediaConvertClient mc = MediaConvertClient.builder()
			.region(region)
			.credentialsProvider(ProfileCredentialsProvider.create())
			.build();

		String mcRoleARN = "arn:aws:iam::700552527916:role/processamento_de_videos_mediaconverter";
		String fileInput = "s3://videos-nao-processados-v2/video-for-tests.mp4";

		String s3path = fileInput;
		String fileOutput = "s3://videos-processados-v2/sdk-v2/";

		try {
			DescribeEndpointsResponse res = mc
				.describeEndpoints(DescribeEndpointsRequest.builder().maxResults(20).build());

			if (res.endpoints().size() <= 0) {
				System.out.println("Cannot find MediaConvert service endpoint URL!");
				System.exit(1);
			}

			String endpointURL = res.endpoints().get(0).url();
			System.out.println("MediaConvert service URL: " + endpointURL);
			System.out.println("MediaConvert role arn: " + mcRoleARN);
			System.out.println("MediaConvert input file: " + fileInput);
			System.out.println("MediaConvert output path: " + s3path);


			MediaConvertClient emc = MediaConvertClient.builder()
				.region(region)
				.endpointOverride(URI.create(endpointURL))
				.build();




			// output group Preset HLS media profile
			/*
			Output hlsMedium = createOutput(
				"hls_medium",
				"_medium",
				"_$dt$",
				6000000,
				7,
				720,
				1280);*/

			/*
			// output group Preset HLS high profole
			Output hlsHigh = createOutput(
				"hls_high",
				"_high",
				"_$dt$",
				10000000,
				8,
				1080,
				1920);*/

			var appleHLSH265 = new AppleHLSH264(
				"_264_720p",
				"_$dt$",
				1280,
				720,
				new BitRate(6000000));
			var container720P = appleHLSH265.criar();



			//this.appleHLSContainer.setNomeDaMidiaDeEntrada("video-for-tests");
			this.appleHLSContainer.criar("container720P");


			/**
			OutputGroup appleHLS = OutputGroup.builder().name("Apple HLS").customName("Example")
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
						.timedMetadataId3Frame(HlsTimedMetadataId3Frame.PRIV).timedMetadataId3Period(10)
						.destination(fileOutput).segmentControl(HlsSegmentControl.SEGMENTED_FILES)
						.minFinalSegmentLength((double) 0).segmentLength(10).minSegmentLength(0).build())
					.build())
				.outputs(hlsMedium).build();*/

			/*
			Map<String, AudioSelector> audioSelectors = new HashMap<>();
			audioSelectors.put("Audio Selector 1",
				AudioSelector.builder().defaultSelection(AudioDefaultSelection.DEFAULT).offset(0).build());*/


			this.appleHLSContainer.criar("video-for-tests.mp4");

			var appleHLS = this.appleHLSContainer.criarCodecs();
			var audioSelectors = this.appleHLSContainer.criarTrilhasDeAudio();
			var jobSettings = this.appleHLSContainer.configurarJob(appleHLS, audioSelectors);

			/*
			JobSettings jobSettings = JobSettings.builder().inputs(Input.builder().audioSelectors(audioSelectors)
					.videoSelector(VideoSelector.builder().colorSpace(ColorSpace.FOLLOW).rotate(InputRotate.DEGREE_0).build())
					.filterEnable(InputFilterEnable.AUTO).filterStrength(0).deblockFilter(InputDeblockFilter.DISABLED)
					.denoiseFilter(InputDenoiseFilter.DISABLED).psiControl(InputPsiControl.USE_PSI)
					.timecodeSource(InputTimecodeSource.EMBEDDED).fileInput("s3://videos-nao-processados-v2/video-for-tests.mp4").build())
				.outputGroups(appleHLS).build();*/


			CreateJobRequest createJobRequest = CreateJobRequest.builder().role(mcRoleARN).settings(jobSettings)
				.build();

			CreateJobResponse createJobResponse = emc.createJob(createJobRequest);
			return createJobResponse.job().id();

		} catch (MediaConvertException e) {
			System.out.println(e.toString());
			System.exit(0);
		}
		return "";
	}

	private static Output createOutput(String customName,
	                                   String nameModifier,
	                                   String segmentModifier,
	                                   int qvbrMaxBitrate,
	                                   int qvbrQualityLevel,
	                                   int originHeight,
	                                   int targetWidth) {

		var appleHLSH265 = new AppleHLSH264(
			"_264_720p",
			"_$dt$",
			1280,
			720,
			new BitRate(6000000));

		return appleHLSH265.criar();

	}




























	private static Output createOutputV1(String customName,
	                                   String nameModifier,
	                                   String segmentModifier,
	                                   int qvbrMaxBitrate,
	                                   int qvbrQualityLevel,
	                                   int originHeight,
	                                   int targetWidth) {

		Output output = null;

		try {
			output = Output.builder().nameModifier(nameModifier).outputSettings(OutputSettings.builder()
					.hlsSettings(HlsSettings.builder().segmentModifier(segmentModifier).audioGroupId("program_audio")
						.iFrameOnlyManifest(HlsIFrameOnlyManifest.EXCLUDE).build())
					.build())

				.containerSettings(ContainerSettings.builder().container(ContainerType.M3_U8)
					.m3u8Settings(M3u8Settings.builder().audioFramesPerPes(4)
						.pcrControl(M3u8PcrControl.PCR_EVERY_PES_PACKET).pmtPid(480)
						.programNumber(1).patInterval(0).pmtInterval(0).scte35Source(M3u8Scte35Source.NONE)
						.scte35Pid(500).nielsenId3(M3u8NielsenId3.NONE).timedMetadata(TimedMetadata.NONE)
						.timedMetadataPid(502).videoPid(481)
						.audioPids(482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492).build())
					.build())

				.videoDescription(
					VideoDescription.builder().width(targetWidth).height(originHeight)
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
								.maxBitrate(qvbrMaxBitrate)
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
						.build())


				.audioDescriptions(AudioDescription.builder().audioTypeControl(AudioTypeControl.FOLLOW_INPUT)
					.languageCodeControl(AudioLanguageCodeControl.FOLLOW_INPUT)
					.codecSettings(AudioCodecSettings.builder().codec(AudioCodec.AAC).aacSettings(AacSettings
							.builder().codecProfile(AacCodecProfile.LC).rateControlMode(AacRateControlMode.CBR)
							.codingMode(AacCodingMode.CODING_MODE_2_0).sampleRate(48000).bitrate(96000)
							.rawFormat(AacRawFormat.NONE).specification(AacSpecification.MPEG4)
							.audioDescriptionBroadcasterMix(AacAudioDescriptionBroadcasterMix.NORMAL).build())
						.build())
					.build())
				.build();


		} catch (MediaConvertException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return output;
	}
}
