package com.br.stream.processamentoDeVideo.infra.processarMidias.fabrica;

import com.br.stream.processamentoDeVideo.adapters.Job;
import com.br.stream.processamentoDeVideo.infra.processarMidias.BitRate;
import com.br.stream.processamentoDeVideo.infra.processarMidias.MediaConvert;
import com.br.stream.processamentoDeVideo.infra.processarMidias.S3.EndpointS3;
import com.br.stream.processamentoDeVideo.infra.processarMidias.audio.TrilhasDeAudio;
import com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida.AppleHLSContainer;
import com.br.stream.processamentoDeVideo.infra.processarMidias.gruposDeSaida.AppleHLSH264;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.*;

import java.util.Map;

@Component
public class HLSJob implements Job {

	private AppleHLSContainer appleHLSContainer;

	private EndpointS3 endpointS3;

	@Value("${aplicacao.iam.arn}")
	private String ARN;

	private MediaConvert mediaConvert = new MediaConvert();

	private MediaConvertClient mediaConvertClient;

	@Autowired
	public HLSJob(AppleHLSContainer appleHLSContainer, EndpointS3 endpointS3) {
		this.appleHLSContainer = appleHLSContainer;
		this.endpointS3 = endpointS3;
	}

	public void criar(String midiaDeEntrada) {

		try {
			this.mediaConvertClient = this.mediaConvert.criar();

			this.endpointS3.setNomeDaMidiaOriginal(midiaDeEntrada);

			Map<String, AudioSelector> trilhasDeAudio = this.criarTrilhasDeAudio();

			OutputGroup codecs = this.criarCodecs();

			JobSettings configurarJOB = this.configurarJob(codecs, trilhasDeAudio);

			this.construirJob(configurarJOB);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public JobSettings configurarJob(OutputGroup gruposDeSaida, Map<String, AudioSelector> trilhaDeAudio) {

		return JobSettings.builder().inputs(Input.builder().audioSelectors(trilhaDeAudio)
				.videoSelector(VideoSelector.builder().colorSpace(ColorSpace.FOLLOW).rotate(InputRotate.DEGREE_0).build())
				.filterEnable(InputFilterEnable.AUTO).filterStrength(0).deblockFilter(InputDeblockFilter.DISABLED)
				.denoiseFilter(InputDenoiseFilter.DISABLED).psiControl(InputPsiControl.USE_PSI)
				.timecodeSource(InputTimecodeSource.EMBEDDED).fileInput(this.endpointS3.criarEndpointDeEntrada()).build())
			.outputGroups(gruposDeSaida).build();
	}

	public OutputGroup criarCodecs() {

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


		var resolucao1080P = new AppleHLSH264(
			"_264_1080p",
			"_$dt$",
			1920,
			1080,
			new BitRate(10000000));
		var container1080P = resolucao1080P.criar();

		return this.appleHLSContainer.criar(container480P, container720P, container1080P);
	}

	public Map<String, AudioSelector> criarTrilhasDeAudio() {
		var trilhasDeAudio = new TrilhasDeAudio();
		return trilhasDeAudio.criar();
	}

	public CreateJobResponse construirJob(JobSettings job) {
		CreateJobRequest criarJob = CreateJobRequest.builder().role(ARN).settings(job).build();
		return this.mediaConvertClient.createJob(criarJob);
	}

}
