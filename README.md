


## 진행 방법
- application.properties -> DataSource  
- inteliJ 기준 Querydsl Build(QType 생성)
    1. 오른쪽 상단 Gradle 창
    2. Tasks -> build -> clean
    3. other -> compileQuerydsl
    4. build - generated - querydsl 생성여부 확인
    

## DDL
```sql
CREATE TABLE host
(
    host_id           BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(50)          NULL,
    address           VARCHAR(50)          NULL,
    registration_time datetime              NULL,
    modified_time     datetime              NULL,
    status            VARCHAR(20)          NULL,
    CONSTRAINT pk_host PRIMARY KEY (host_id)
);

ALTER TABLE host
    ADD CONSTRAINT uc_host_address UNIQUE (address);

ALTER TABLE host
    ADD CONSTRAINT uc_host_name UNIQUE (name);
```


## Rest

|return|method name|parameter|url|
|-----|-------|-------|-----|
|ResponseEntity<Map<String,Object>>|createHost|HttpServletRequest request|/host/post|
|ResponseEntity<Map<String,Object>>|deleteHost|HttpServletRequest request|/host/delete|
|ResponseEntity<Map<String,Object>>|modifyHost|HttpServletRequest request|/host/patch|
|Result\<HostResponse\>|host|HttpServletRequest request|/host/get|
|Page\<HostResponse\>|hosts|Pageable pageable|/host/gets|

### 요청 결과 예시

- createHost
    - 호스트 등록 성공 
    ```json
    {
      "msg": "sonhyeonhoui-MacBookAir.local를 가입하였습니다.",
      "hostName": "sonhyeonhoui-MacBookAir.local",
      "clientIp": "192.168.35.8"
    }
    ```
    - 중복 호스트 예외
    ```json
    {
      "ex": "com.host.host.exception.DuplicateHostException",
      "message": "중복된 호스트 등록 예외"
    }
    ```
- deleteHost
    - 호스트 삭제 성공
    ```json
    {
        "msg": "sonhyeonhoui-MacBookAir.local가 삭제하였습니다.",
        "hostName": "sonhyeonhoui-MacBookAir.local",
        "clientIp": "192.168.35.8"
    }
    ```
    
- modifyHost
  - 호스트 수정 성공
  ```json
    {
      "msg": "sonhyeonhoui-MacBookAir.local를 수정하였습니다.",
      "hostName": "sonhyeonhoui-MacBookAir.local",
      "clientIp": "192.168.35.8"
    }  
  ```
  
- host
    - 호스트 단 건 검색
  ```json
    {
        "data": {
          "name": "sonhyeonhoui-MacBookAir.local",
          "address": "192.168.35.8",
          "lastAliveTime": "2021-11-12T06:57:22.70843",
          "aliveStatus": "ALIVE_STATUS"
        }
    }
  ```
- hosts
  - 호스트 전체 검색
  ```json
    {
    "content": [
        {
            "name": "sonhyeonhoui-MacBookAir.local",
            "address": "192.168.35.8",
            "lastAliveTime": "2021-11-12T06:57:22.70843",
            "aliveStatus": "ALIVE_STATUS"
        }
    ],
    "pageable": {
        "sort": {
            "unsorted": true,
            "sorted": false,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 100,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 100,
    "first": true,
    "number": 0,
    "numberOfElements": 1,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "empty": false
    }    
  ```
