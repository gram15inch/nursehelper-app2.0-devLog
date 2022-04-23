# nursehelper-app2.0-devLog
nursehelper-app 2.0 Development log


## 1. 비전수립
> * **목표고객** 
>   * 외부에 나가있는 간호사
  
  
> * **고객이 무엇을 필요로 하는가**
>   * 일이 생겼을때 바로 처리할 수 있는것
>   * 현장에서 문서 작성후 다시하지않기
>   * 빠른 업무처리
   
   
> * **이앱이 어떤 가치를 전달할수 있는가**
>   * 경과지를 키워드를 선택하는 방식으로 작성
>   * 문서들을 관리하는기능( 검색, 추가, 삭제)
>   * 문서들을 인쇄, 내보내기 기능
>   * 환자초기등록 문서들을 템플릿으로 만든후 변하는 부분을 여러가지 상호작용으로 작성 
 
 
> * **기존제품과의 차별성**
>   * 문서작성이 단순히 키보드나 필기를 사용하는것을 넘어 여러가지 상호작용을 통해  
>     더빠르고 간편하게 작성할 수 있어 더빠른 업무처리로 현장에서 대부분 끝낼 수 있게 해줌


## 2. impact map

| **Why** | **Who** | **How** | **What** |
| -------- | :------: | -------- | -------- |
| 현장에서 업무 끝내기  | 간호사들  | 빠른문서작성 | 키워드 조합 작성|
| | | | 문서템플릿 제공|
| | |빠른 문서접근 | 다양한 상호작용 입력|
| | | | 다양한 검색 카테고리|
| | | | 직관적 검색결과|
| | | 빠른 업무처리 | 시간대별 작업 추천|



## 3.제품 백로그

### 업무목록

| **업무 분류** | **백로그 업무 항목** |
| :--------: | :------ |
| 기능  | 문서작성시 템플릿사용   |
|   | 문서 작성시 키워드 조합으로 입력  |
|   | 다양한 상호작용으로 입력 |
|   | 작성중 문서가 임시 저장 |
|   | 하나의 문자열로 여러 카테고리 검색  |
|   | 다양한 검색 카테고리 |
|   | 문서 검색, 삭제, 추가 |
|   |  저장된 문서의 인쇄, 외부파일로 내보내기 |
|   |   |
|   | 시간대별로 필요할만한 작업 추천 |
|   |   |
|   | 사용자별 계정 생성 |
|   | 앱시작시 계정으로 사용자 인증 |
|   |   |
| 비기능| 환자별로 기록지를 매일 작성함에 따른 기록지 혼란제거 | 
|   |  저장된 정보가 서버에 백업 |
|   |  |
| 기술적 관리적 업무| mvvm 패턴 학습 | 
|   | jetpack compose 학습 |
|   |  firebase 학습 |
|   | spring 과 firebase로 서버 구현 |
| 오류 수정| |

### 스토리 목록
| **스토리** | **스토리 점수** |
| :-------- | :------: |
| 간호사는 빠른 문서작성을 위해 키보드나 펜을 쓰지않고 문서를 작성할 수 있어야 한다 | **7** |
| 간호사는 빠른 문서접근을 위해 이름, 생년월일, 주소, 나이, 특징, 기록지 내용등으로 문서를 검색할 수 있어야 한다 | **5** |
| 간호사는 문서관리를 위해 문서를 수정, 삭제 할수 있어야 한다   | **7** |
| 간호사는 빠른 업무처리를 위해 앱시작시 그 시간대에 할 작업을 추천받을수 있어야 한다  | **7** |
| 간호사는 저장된 문서의 사용을 위해 인쇄, pdf, png 형태로 문서 내보내기를할 수 있어야 한다  | **7** |
| 간호사는 여러 기기의 사용을 위해 어느 기기에서나 저장된 정보를 불러올 수 있어야 한다 | **10** |
| 간호사는 등록된 정보의 유출방지를 위해 인증된 사용자만 앱을 접근할 수 있어야한다  | **2** |
|   |  |
| **총합**  | **45** |

