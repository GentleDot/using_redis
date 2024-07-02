# using_redis

# 개요

- Redis 활용 전략 정리
- Spring, nest 사용

# 전략 정리

## cache aside

### Spring (Jedis, Lettuce)

- client_spring > user package 내 구현
- redis 설정 : RedisConfig
- redisHash 사용 : RedisHashUser
- spring boot caching 사용 : CacheConfig
  - https://docs.spring.io/spring-boot/reference/io/caching.html#io.caching.provider.redis

```yaml
├── java
│   └── net
│       └── gentledot
│           └── client_spring
│               ├── ClientSpringApplication.java
│               ├── common
│               │   └── config
│               │       ├── CacheConfig.java
│               │       ├── CacheProperty.java
│               │       ├── ObjectMapperConfig.java
│               │       └── RedisConfig.java
│               └── user
│                   ├── controller
│                   │   └── UserController.java
│                   ├── model
│                   │   └── domain
│                   │       ├── RedisHashUser.java
│                   │       └── User.java
│                   ├── repository
│                   │   ├── RedisHashUserRepository.java
│                   │   └── UserRepository.java
│                   └── service
│                       └── UserService.java
└── resources
    └── application.yml
```

```bash
1719815895.983623 [0 172.17.0.1:59138] "GET" "users:1"
1719815896.053465 [0 172.17.0.1:59138] "SETEX" "users:1" "30" "{\"id\":1,\"email\":\"tester1@example.com\",\"name\":\"tester1\",\"createdAt\":[2024,7,1,15,38,13,630846000],\"updatedAt\":[2024,7,1,15,38,13,630846000]}"
1719816027.731835 [0 172.17.0.1:59138] "GET" "users:1"
1719816027.755212 [0 172.17.0.1:59138] "SETEX" "users:1" "30" "{\"id\":1,\"email\":\"tester1@example.com\",\"name\":\"tester1\",\"createdAt\":[2024,7,1,15,38,13,630846000],\"updatedAt\":[2024,7,1,15,38,13,630846000]}"
1719881312.279954 [0 172.17.0.1:59280] "HELLO" "3"
1719881312.288597 [0 172.17.0.1:59280] "CLIENT" "SETINFO" "lib-name" "Lettuce"
1719881312.288610 [0 172.17.0.1:59280] "CLIENT" "SETINFO" "lib-ver" "6.3.2.RELEASE/8941aea"
1719881312.301460 [0 172.17.0.1:59280] "GET" "users:1"
1719881312.356867 [0 172.17.0.1:59280] "SETEX" "users:1" "30" "[\"net.gentledot.client_spring.user.model.domain.User\",{\"id\":1,\"email\":\"tester1@example.com\",\"name\":\"tester1\",\"createdAt\":[2024,7,2,9,48,23,444973000],\"updatedAt\":[2024,7,2,9,48,23,444973000]}]"
1719881323.694394 [0 172.17.0.1:59280] "GET" "users:1"
1719881324.615475 [0 172.17.0.1:59280] "GET" "users:1"
1719881325.309101 [0 172.17.0.1:59280] "GET" "users:1"
1719881325.966945 [0 172.17.0.1:59280] "GET" "users:1"
1719883457.847903 [0 172.17.0.1:59442] "HELLO" "3"
1719883457.863210 [0 172.17.0.1:59442] "CLIENT" "SETINFO" "lib-name" "Lettuce"
1719883457.863231 [0 172.17.0.1:59442] "CLIENT" "SETINFO" "lib-ver" "6.3.2.RELEASE/8941aea"
1719883457.889641 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719883457.932674 [0 172.17.0.1:59442] "DEL" "redis-hash-user:1"
1719883457.937599 [0 172.17.0.1:59442] "HMSET" "redis-hash-user:1" "_class" "net.gentledot.client_spring.user.model.domain.RedisHashUser" "createdAt" "2024-07-02T10:23:23.903592" "email" "tester1@example.com" "id" "1" "name" "tester1" "updatedAt" "2024-07-02T10:23:23.903592"
1719883457.941110 [0 172.17.0.1:59442] "SADD" "redis-hash-user" "1"
1719883457.943222 [0 172.17.0.1:59442] "EXPIRE" "redis-hash-user:1" "30"
1719883457.945533 [0 172.17.0.1:59442] "SADD" "redis-hash-user:email:tester1@example.com" "1"
1719883457.946945 [0 172.17.0.1:59442] "SADD" "redis-hash-user:1:idx" "redis-hash-user:email:tester1@example.com"
1719883473.437352 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719883474.607217 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719883488.111659 [0 127.0.0.1:39496] "ttl" "redis-hash-user:1"
1719883489.980093 [0 127.0.0.1:39496] "ttl" "redis-hash-user:1"
1719883491.110659 [0 127.0.0.1:39496] "ttl" "redis-hash-user:1"
1719883658.229211 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719883658.255049 [0 172.17.0.1:59442] "DEL" "redis-hash-user:1"
1719883658.256604 [0 172.17.0.1:59442] "HMSET" "redis-hash-user:1" "_class" "net.gentledot.client_spring.user.model.domain.RedisHashUser" "createdAt" "2024-07-02T10:23:23.903592" "email" "tester1@example.com" "id" "1" "name" "tester1" "updatedAt" "2024-07-02T10:23:23.903592"
1719883658.258585 [0 172.17.0.1:59442] "SADD" "redis-hash-user" "1"
1719883658.260609 [0 172.17.0.1:59442] "EXPIRE" "redis-hash-user:1" "30"
1719883658.262232 [0 172.17.0.1:59442] "SADD" "redis-hash-user:email:tester1@example.com" "1"
1719883658.264166 [0 172.17.0.1:59442] "SADD" "redis-hash-user:1:idx" "redis-hash-user:email:tester1@example.com"
1719883661.761025 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719883662.322387 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719883662.833946 [0 172.17.0.1:59442] "HGETALL" "redis-hash-user:1"
1719886598.688987 [0 172.17.0.1:59606] "HELLO" "3"
1719886598.707960 [0 172.17.0.1:59606] "CLIENT" "SETINFO" "lib-name" "Lettuce"
1719886598.707974 [0 172.17.0.1:59606] "CLIENT" "SETINFO" "lib-ver" "6.3.2.RELEASE/8941aea"
1719886598.722551 [0 172.17.0.1:59606] "GET" "cache1::users:1"
1719886598.774268 [0 172.17.0.1:59606] "SET" "cache1::users:1" "[\"net.gentledot.client_spring.user.model.domain.User\",{\"id\":1,\"email\":\"tester1@example.com\",\"name\":\"tester1\",\"createdAt\":[2024,7,2,11,16,27,356182000],\"updatedAt\":[2024,7,2,11,16,27,356182000]}]" "PX" "300000"
1719886601.853174 [0 172.17.0.1:59606] "GET" "cache1::users:1"
1719886602.581782 [0 172.17.0.1:59606] "GET" "cache1::users:1"
1719886603.201460 [0 172.17.0.1:59606] "GET" "cache1::users:1"
```