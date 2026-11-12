# Transit ETA Platform

A multi-stage project to predict transit vehicle arrival times from historical
real-time feed data.

## Roadmap

1. **`collector`** (this stage) — a Spring Boot service that polls one or more
   GTFS-Realtime feeds on a schedule and archives every snapshot to Postgres,
   building up a week+ of historical data to train/evaluate against later.
2. **ETA model** — not started. Will consume the archived snapshots to learn
   arrival-time patterns per route/stop.
3. **Serving API** — not started. Will expose predicted ETAs.
4. **Frontend** — not started.

## Stage 1: `collector`

### What it does

On a fixed interval it fetches each configured GTFS-Realtime feed (protobuf),
counts the entities in it, and stores the raw payload + a timestamp in the
`feed_snapshot` table. Parsing into per-trip/per-stop fields is deferred to
the ETA model stage — this stage's only job is to not lose data.

### Setup

1. Start Postgres:
   ```
   docker compose up -d
   ```
2. Pick a real GTFS-Realtime feed. Many transit agencies publish one; some
   require an API key. [transitfeeds.com](https://transitfeeds.com) and
   each agency's developer portal are good places to find one.
3. Edit `collector/src/main/resources/application.yml` and fill in
   `transit.gtfs-rt.feeds` with the feed(s) you want to poll (see the
   commented-out example in that file).
4. Run the service:
   ```
   cd collector
   ./mvnw spring-boot:run
   ```
5. Check it's polling:
   ```
   curl http://localhost:8080/actuator/health
   ```
   and watch the logs for `Polled feed '...'` lines.

### Running it for a full week

This is meant to run continuously for several days to build a useful
dataset, so don't run it on a laptop that will sleep or lose network. A
small always-on VM (or a Raspberry Pi, or a scheduled cloud job) works —
deployment target is still TBD for this project.
