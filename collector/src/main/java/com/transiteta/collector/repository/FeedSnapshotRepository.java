package com.transiteta.collector.repository;

import com.transiteta.collector.model.FeedSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedSnapshotRepository extends JpaRepository<FeedSnapshot, Long> {
}
