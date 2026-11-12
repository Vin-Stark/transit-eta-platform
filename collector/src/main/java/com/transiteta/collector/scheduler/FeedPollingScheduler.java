package com.transiteta.collector.scheduler;

import com.transiteta.collector.client.GtfsRealtimeClient;
import com.transiteta.collector.config.FeedProperties;
import com.transiteta.collector.model.FeedSnapshot;
import com.transiteta.collector.repository.FeedSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class FeedPollingScheduler {

	private static final Logger log = LoggerFactory.getLogger(FeedPollingScheduler.class);

	private final FeedProperties feedProperties;
	private final GtfsRealtimeClient client;
	private final FeedSnapshotRepository repository;

	public FeedPollingScheduler(FeedProperties feedProperties, GtfsRealtimeClient client,
			FeedSnapshotRepository repository) {
		this.feedProperties = feedProperties;
		this.client = client;
		this.repository = repository;
	}

	@Scheduled(fixedDelayString = "${transit.gtfs-rt.poll-interval-ms:30000}")
	public void pollFeeds() {
		if (feedProperties.getFeeds().isEmpty()) {
			log.warn("No feeds configured under transit.gtfs-rt.feeds — skipping poll");
			return;
		}

		for (FeedProperties.FeedConfig feed : feedProperties.getFeeds()) {
			try {
				byte[] payload = client.fetch(feed);
				int entityCount = client.countEntities(payload);

				FeedSnapshot snapshot = new FeedSnapshot();
				snapshot.setFeedName(feed.getName());
				snapshot.setFetchedAt(Instant.now());
				snapshot.setEntityCount(entityCount);
				snapshot.setRawPayload(payload);
				repository.save(snapshot);

				log.info("Polled feed '{}': {} entities, {} bytes", feed.getName(), entityCount, payload.length);
			} catch (Exception e) {
				log.error("Failed to poll feed '{}': {}", feed.getName(), e.getMessage());
			}
		}
	}
}
