## ‘호스트들의 Alive 상태 모니터링 서버 개발'

[ 요구 사항 ]
- 호스트 등록 관리
    - 호스트 등록 관리 REST API를 제공해야 한다. (조회/등록/수정/삭제)
    - 호스트 등록 필드는 name, address 이다.
    - name, address 는 중복되면 안 된다.
    - 조회 결과 필드에는 호스트 등록/수정 시간을 포함해야 한다.
    - 서버가 재시작되어도 등록된 호스트의 리스트는 유지되어야 한다.
    - 호스트 등록은 100개로 제한한다.
등록된 호스트 Alive 상태 확인
    - Alive 상태 확인은 InetAddress.isReachable() 사용을 권장한다. (다른 방법도 가능)
    - 호스트들의 Alive 상태를 조회하는 REST API를 제공해야 한다.
    - 조회 결과 필드에는 현재 상태와 마지막 Alive 시간을 포함해야 한다.
    - 조회의 단위는 한 호스트, 그리고 전체 호스트도 가능해야 한다.



## 설계
- Client Ip , Host name 획득 혹은 Alive 여부 확인 관련된 내용은 ApiUtil에 static method로 구성
- 예외처리 1 : 언체크드 예외인 `CustomException` 으로 예외 전환하였다.
- 예외 처리 2 : `CustomHandlerExceptionResolver`를 만들어 예외에 따른 반환값 제어.
- Domain(Host Entity)에 해당 내용과 관련된 비즈니스 로직을 넣어 Domain Driven Design 형태를 맞춤
- 조회시 Page(Spring) or Result(Custom)으로 감싸서 API 스펙 변경이나 페이징처리에 대비



## 진행 방법
- JPA , Spring Data JPA , Querydsl 
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

