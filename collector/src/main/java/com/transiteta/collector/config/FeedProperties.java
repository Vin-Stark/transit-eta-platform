package com.transiteta.collector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "transit.gtfs-rt")
public class FeedProperties {

	private long pollIntervalMs = 30000;
	private List<FeedConfig> feeds = new ArrayList<>();

	public long getPollIntervalMs() {
		return pollIntervalMs;
	}

	public void setPollIntervalMs(long pollIntervalMs) {
		this.pollIntervalMs = pollIntervalMs;
	}

	public List<FeedConfig> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<FeedConfig> feeds) {
		this.feeds = feeds;
	}

	public static class FeedConfig {
		private String name;
		private String url;
		private String apiKeyHeader;
		private String apiKeyValue;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getApiKeyHeader() {
			return apiKeyHeader;
		}

		public void setApiKeyHeader(String apiKeyHeader) {
			this.apiKeyHeader = apiKeyHeader;
		}

		public String getApiKeyValue() {
			return apiKeyValue;
		}

		public void setApiKeyValue(String apiKeyValue) {
			this.apiKeyValue = apiKeyValue;
		}
	}
}
