package com.br.stream.processamentoDeVideo.infra.processarMidias.mediaconvert;

import com.br.stream.processamentoDeVideo.infra.processarMidias.S3.EndpointS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.DescribeEndpointsRequest;
import software.amazon.awssdk.services.mediaconvert.model.DescribeEndpointsResponse;
import software.amazon.awssdk.services.mediaconvert.model.MediaConvertException;

import java.net.URI;

public class MediaConvert {

	private static final Region regiao = Region.US_EAST_1;

	public MediaConvertClient criar() {

		return MediaConvertClient.builder()
			.region(regiao)
			.endpointOverride(URI.create(this.criarEndpointMediaConvert().endpoints().get(0).url()))
			.build();
	}

	private DescribeEndpointsResponse criarEndpointMediaConvert() throws MediaConvertException {
		MediaConvertClient mediaConvertClient = MediaConvertClient.builder()
			.region(regiao)
			.credentialsProvider(ProfileCredentialsProvider.create())
			.build();

		return mediaConvertClient
			.describeEndpoints(DescribeEndpointsRequest.builder().maxResults(20).build());

	}

}
