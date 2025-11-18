# OOT-Batch

[OOT-Batch는 OOT(Outfit Of Today) 서비스의 백엔드 배치 처리 서버입니다.] EC2 환경에서 Spring Batch를 통해 주요 데이터 집계, 스케줄링, 대량 처리 작업을 수행합니다.

## 주요 기능
- Spring Batch 기반 대량 데이터 처리 및 정기 집계
- RDS/MySQL 연동, 대용량 쿼리 및 집계
- 정기 스케줄링(Cron, EC2 스케줄러 등) 지원
- AWS EC2에서 운영
- 로그 관리 및 에러 핸들링

## 프로젝트 구조
````
src
├── main
│   ├── java
│   │   └── com
│   │       └── ootbatch
│   │           ├── OotBatchApplication.java
│   │           ├── common
│   │           │   ├── config
│   │           │   │   ├── BatchConfig.java
│   │           │   │   ├── ClockConfig.java
│   │           │   │   ├── QuerydslConfig.java
│   │           │   │   ├── RedisConfig.java
│   │           │   │   ├── RedissonConfig.java
│   │           │   │   ├── RestTemplateConfig.java
│   │           │   │   └── SchedulerConfig.java
│   │           │   ├── entity
│   │           │   │   └── BaseEntity.java
│   │           │   └── exception
│   │           │       ├── CommonErrorCode.java
│   │           │       ├── ErrorCode.java
│   │           │       ├── GlobalException.java
│   │           │       └── SuccessCode.java
│   │           ├── domain
│   │           │   ├── auth
│   │           │   │   └── enums
│   │           │   │       ├── LoginType.java
│   │           │   │       └── SocialProvider.java
│   │           │   ├── category
│   │           │   │   └── entity
│   │           │   │       └── Category.java
│   │           │   ├── chatparticipatinguser
│   │           │   │   └── entity
│   │           │   │       ├── ChatParticipatingUser.java
│   │           │   │       └── ChatParticipatingUserId.java
│   │           │   ├── chatroom
│   │           │   │   └── entity
│   │           │   │       └── Chatroom.java
│   │           │   ├── closet
│   │           │   │   └── entity
│   │           │   │       └── Closet.java
│   │           │   ├── closetclotheslink
│   │           │   │   └── entity
│   │           │   │       └── ClosetClothesLink.java
│   │           │   ├── closetimage
│   │           │   │   └── entity
│   │           │   │       └── ClosetImage.java
│   │           │   ├── clothes
│   │           │   │   ├── entity
│   │           │   │   │   └── Clothes.java
│   │           │   │   ├── repository
│   │           │   │   │   ├── ClothesCustomRepository.java
│   │           │   │   │   ├── ClothesCustomRepositoryImpl.java
│   │           │   │   │   └── ClothesRepository.java
│   │           │   │   └── service
│   │           │   │       └── query
│   │           │   │           ├── ClothesQueryService.java
│   │           │   │           └── ClothesQueryServiceImpl.java
│   │           │   ├── clothesImage
│   │           │   │   └── entity
│   │           │   │       └── ClothesImage.java
│   │           │   ├── dashboard
│   │           │   │   ├── batch
│   │           │   │   │   └── DashboardUserBatchConfig.java
│   │           │   │   ├── dto
│   │           │   │   │   └── DashboardUserStatPair.java
│   │           │   │   ├── listener
│   │           │   │   │   ├── DashboardUserBatchChunkListener.java
│   │           │   │   │   ├── DashboardUserBatchJobListener.java
│   │           │   │   │   ├── DashboardUserBatchProcessListener.java
│   │           │   │   │   ├── DashboardUserBatchReadListener.java
│   │           │   │   │   ├── DashboardUserBatchStepListener.java
│   │           │   │   │   └── DashboardUserBatchWriteListener.java
│   │           │   │   ├── scheduler
│   │           │   │   │   ├── DashboardAdminBatchScheduler.java
│   │           │   │   │   └── DashboardUserBatchScheduler.java
│   │           │   │   ├── service
│   │           │   │   │   └── query
│   │           │   │   │       ├── admin
│   │           │   │   │       │   ├── DashboardAdminQueryService.java
│   │           │   │   │       │   └── DashboardAdminQueryServiceImpl.java
│   │           │   │   │       └── user
│   │           │   │   │           ├── DashboardUserQueryService.java
│   │           │   │   │           └── DashboardUserQueryServiceImpl.java
│   │           │   │   └── step
│   │           │   │       ├── DashboardUserBatchProcessor.java
│   │           │   │       └── DashboardUserBatchWriter.java
│   │           │   ├── image
│   │           │   │   ├── entity
│   │           │   │   │   ├── Image.java
│   │           │   │   │   └── ImageType.java
│   │           │   │   └── exception
│   │           │   │       ├── ImageErrorCode.java
│   │           │   │       └── ImageException.java
│   │           │   ├── recommendation
│   │           │   │   ├── batch
│   │           │   │   │   └── RecommendationBatchJobConfig.java
│   │           │   │   ├── client
│   │           │   │   │   └── RecommendationApiClient.java
│   │           │   │   ├── dto
│   │           │   │   │   └── RecommendationBatchResult.java
│   │           │   │   ├── entity
│   │           │   │   │   └── Recommendation.java
│   │           │   │   ├── exception
│   │           │   │   │   ├── BatchErrorCode.java
│   │           │   │   │   └── BatchException.java
│   │           │   │   ├── listener
│   │           │   │   │   ├── RecommendationJobExecutionListener.java
│   │           │   │   │   └── RecommendationStepExecutionListener.java
│   │           │   │   ├── repository
│   │           │   │   │   ├── RecommendationBatchHistoryRepository.java
│   │           │   │   │   └── RecommendationRepository.java
│   │           │   │   ├── scheduler
│   │           │   │   │   └── RecommendationScheduler.java
│   │           │   │   ├── service
│   │           │   │   │   ├── command
│   │           │   │   │   │   ├── RecommendationBatchHistoryCommandService.java
│   │           │   │   │   │   └── RecommendationBatchHistoryCommandServiceImpl.java
│   │           │   │   │   └── query
│   │           │   │   │       ├── RecommendationBatchHistoryQueryService.java
│   │           │   │   │       └── RecommendationBatchHistoryQueryServiceImpl.java
│   │           │   │   └── step
│   │           │   │       ├── RecommendationItemProcessor.java
│   │           │   │       └── RecommendationItemWriter.java
│   │           │   ├── salepost
│   │           │   │   ├── entity
│   │           │   │   │   ├── SalePost.java
│   │           │   │   │   └── SalePostImage.java
│   │           │   │   ├── repository
│   │           │   │   │   └── SalePostRepository.java
│   │           │   │   └── service
│   │           │   │       └── query
│   │           │   │           ├── SalePostQueryService.java
│   │           │   │           └── SalePostQueryServiceImpl.java
│   │           │   ├── user
│   │           │   │   ├── entity
│   │           │   │   │   └── User.java
│   │           │   │   ├── enums
│   │           │   │   │   └── UserRole.java
│   │           │   │   ├── repository
│   │           │   │   │   └── UserRepository.java
│   │           │   │   └── service
│   │           │   │       └── query
│   │           │   │           ├── UserQueryService.java
│   │           │   │           └── UserQueryServiceImpl.java
│   │           │   ├── userimage
│   │           │   │   └── entity
│   │           │   │       └── UserImage.java
│   │           │   └── wearrecord
│   │           │       ├── entity
│   │           │       │   └── WearRecord.java
│   │           │       ├── repository
│   │           │       │   ├── WearCustomRepository.java
│   │           │       │   ├── WearCustomRepositoryImpl.java
│   │           │       │   └── WearRecordRepository.java
│   │           │       └── service
│   │           │           └── query
│   │           │               ├── WearRecordQueryService.java
│   │           │               └── WearRecordQueryServiceImpl.java
│   │           └── test
│   │               ├── DbCheckRunner.java
│   │               └── RedisCheckRunner.java
│   └── resources
│       ├── application-dev.yml
│       ├── application-local.yml
│       ├── application.yml
│       └── schema.sql
└── test
    └── java
        └── com
            └── ootbatch
                └── OotBatchApplicationTests.java
````

## 실행/배포 방법

### 1. 개발 환경 실행(로컬)
````
./gradlew build
````

- 필요시 docker-compose로 RDS/Redis 등 로컬 인프라 실행

### 2. EC2 서버 배포
- Github Actions 또는 수동 배포(EC2 SSH 접속)
- 환경 변수, yml 파일 등을 서버에 맞게 배포 필요

## 환경 변수/설정파일

| 변수명               | 파일설명                           |
|-------------------|--------------------------------|
| application.yml   | DB/RDS, Redis 등 연결 정보             |
| BATCH_CRON        | 배치 스케줄 설정(cron 표현식)          |

## AWS 연동 가이드
- EC2 인스턴스에서 GitHub 코드 pull 후 빌드
- RDS(MySQL) 연결 권한 및 시크릿 입력 필요
