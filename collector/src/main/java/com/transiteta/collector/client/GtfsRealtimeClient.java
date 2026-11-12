package com.transiteta.collector.client;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.transit.realtime.GtfsRealtime;
import com.transiteta.collector.config.FeedProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class GtfsRealtimeClient {

	private final HttpClient httpClient = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	public byte[] fetch(FeedProperties.FeedConfig feed) throws IOException, InterruptedException {
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(feed.getUrl()))
				.timeout(Duration.ofSeconds(15))
				.GET();

		if (feed.getApiKeyHeader() != null && !feed.getApiKeyHeader().isBlank()) {
			requestBuilder.header(feed.getApiKeyHeader(), feed.getApiKeyValue());
		}

		HttpResponse<byte[]> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());

		if (response.statusCode() != 200) {
			throw new IOException("Feed '" + feed.getName() + "' returned HTTP " + response.statusCode());
		}

		return response.body();
	}

	public int countEntities(byte[] payload) {
		try {
			return GtfsRealtime.FeedMessage.parseFrom(payload).getEntityCount();
		} catch (InvalidProtocolBufferException e) {
			return -1;
		}
	}
}
