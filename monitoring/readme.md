# 개요

redis 상태 확인을 위한 시스템 구축

- Prometheus
- Grafana
    - https://grafana.com/oss/prometheus/exporters/redis-exporter/
- Redis exporter
    - https://github.com/oliver006/redis_exporter
- Redis

## 동작 요약

- Redis exporter는 매 주기마다 Redis의 상태를 metric으로 산출
- 실행 중인 Prometheus가 Redis exporter에서 필요 지표들을 scrap
- 조회된 데이터를 Grafana에서 확인

# 실행

```
cd ./using_redis/monitoring
docker compose -d up
```