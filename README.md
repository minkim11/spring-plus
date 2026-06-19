# SPRING PLUS
## 코드 개선 과제

### 1. @Transactional의 이해 
기존: todo를 저장해야하는 api가 readonly = true로 설정되어있어 오류 발생

readonly로 되어있던 옵션 수정

### 2. JWT의 이해
user 정보에 nickname 추가, JWT claims에 추가

### 3. JPA의 이해
JPQL 사용하여 검색 조건 추가  
수정일 기준 기간 검색, 날씨 검색

### 4. 컨트롤러 테스트의 이해
기존: 실제로는 httpStatus 400이지만 200으로 테스트 코드가 작성되어 테스트 실패

### 5. AOP의 이해
기존: AOP 동작 위치와 실행 시점이 잘못 작성

@After -> @Before 및 동작 위치 수정

### 6. JPA Cascade
cascade = CascadeType.All을 사용하여 할 일 생성 시 매니저 자동 등록, 삭제 시 자동 삭제되도록 수정

### 7. N+1 문제
JOIN FETCH 사용하여 해결

### 8. QueryDSL
JPQL -> QueryDsl 수정

### 9. Spring Security
적용

### 10. QueryDsl을 사용하여 검색 기능 만들기
페이지네이션, builder 사용하여 다중 조건, 서브쿼리로 매니저 닉네임 검색

### 11. Transaction 심화
log 테이블 생성 및 @Transactional(propagation = REQUIRES_NEW) 옵션으로 매니저 등록 요청 시 로그 항상 저장
