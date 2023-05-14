package com.thinktank.pts.agileservice.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;

/**
 * @author chemkhih
 * @since 02-17-2023
 */

@Configuration
public class RestServicesConfig {

	@Value("${pts.pts-core.base-url}")
	String baseUrlPts;

	@Bean
	public StateApi customPtsCoreStateApi() {
		return new StateApi(configurePtsApiClient());
	}

	private com.thinktank.pts.agileservice.api.rest.client.pts.invoker.ApiClient configurePtsApiClient() {
		com.thinktank.pts.agileservice.api.rest.client.pts.invoker.ApiClient apiClient = new com.thinktank.pts.agileservice.api.rest.client.pts.invoker.ApiClient(
				configureRestTemplate());
		apiClient.setBasePath(this.baseUrlPts);
		return apiClient;
	}

	private RestTemplate configureRestTemplate() {

		RestTemplate result = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		HttpMessageConverter<?> msgConverter = new MappingJackson2HttpMessageConverter(objMapper);
		result.setMessageConverters(Collections.singletonList(msgConverter));
		result.setRequestFactory(createRequestFactory());
		return result;
	}

	private static HttpComponentsClientHttpRequestFactory createRequestFactory() {
		try {
			SSLContextBuilder sslContext = new SSLContextBuilder();
			sslContext.loadTrustMaterial(null, new TrustAllStrategy());
			CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext.build())
					.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(client);
			return requestFactory;
		} catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException var3) {
			throw new IllegalStateException("Couldn't create HTTP Request factory ignore SSL cert validity: ", var3);
		}
	}
}