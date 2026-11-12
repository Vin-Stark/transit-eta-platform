package com.transiteta.collector.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "feed_snapshot")
public class FeedSnapshot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String feedName;

	@Column(nullable = false)
	private Instant fetchedAt;

	@Column(nullable = false)
	private int entityCount;

	@Lob
	@Column(columnDefinition = "bytea", nullable = false)
	private byte[] rawPayload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public Instant getFetchedAt() {
		return fetchedAt;
	}

	public void setFetchedAt(Instant fetchedAt) {
		this.fetchedAt = fetchedAt;
	}

	public int getEntityCount() {
		return entityCount;
	}

	public void setEntityCount(int entityCount) {
		this.entityCount = entityCount;
	}

	public byte[] getRawPayload() {
		return rawPayload;
	}

	public void setRawPayload(byte[] rawPayload) {
		this.rawPayload = rawPayload;
	}
}
