# Java 로 외부 api 호출연습 repository

### localhost:8080/auth/GOOGLE 을 주소창에 입력하였을때

1. OauthController 에 매핑되어 'GOOGLE' 은 query string 에서 소셜 로그인 타입 인자로 받게 된다.
2. 소셜 로그인 타입을 OauthService 의 request 메소드로 넘긴다.
3. OauthService 에서 만들어둔 findSocialOauthByType 메소드에서 받을 수 있는 소셜 로그인 타입인지 확인한다.
    - 예외상황) 존재하지 않는 소셜 로그인 타입일 경우 예외 처리
4. GoogleOauth 클래스 선택하여 반환하고 getOauthRedirectURL 메소드를 통해 URL 을 조립하여 SocialLogin 을 할 수 있는 페이지로 이동한다.
5. LoginPage 에서 구글 아이디로 로그인 할 수 있다.
6. 로그인이 성공 했을 때 callback URL 을 통해 구글에서 보내준 데이터를 json 형태로 받을 수 있다.
7. requestAccessToken 메소드를 통해 소셜 로그인 타입과 code 를 인자로 넘긴다.
8. 다시 한 번 GoogleOauth 클래스를 찾아서 requestAccessToken 메소드에 code 인자를 넘긴다.
9. Map 을 만들어 필요한 키, 벨류를 담고 post 로 넘긴다.
10. HttpStatus.OK 이면 정상적인 로그인 완료되어 getBody 를 할 수 있고, 아니라면 에러 발생
11. 최종 화면은 getBody 의 결과 혹은 에러 메세지

### TODO List

- Spring Security 적용(blog : https://mangkyu.tistory.com/76) -> 따로 공부 후 적용
- SNS Login 후 Logout 추가(취소)
- Naver Login - NCP 에 아이디만 추가하면 됨(완료 - 개발자 ID는 따로 추가할 필요 없음)
- DB 저장(소셜 로그인 시 어떤식으로 db에 저장할 것인지 고민) -> (현재 google, naver, kakao, github) - 완료
- Facebook 대신 github 로 변경(로그인 구현 예정 - 완료)
- aws route 53 도입하여 dns 으로 sns 로그인 가능하도록 예정
- 각 소셜별 dto 를 만들어 받을 수 있도록 추가 예정(현재 구글만 가능) -> response 로 받을 수 있도록 완료
- domain name 획득 예정(aws route 53과 연동) -> 현재 free domain name 획득하지 못함
- 이미 저장소에 올라간 yml history 에서도 지우기 (blog : https://gmlwjd9405.github.io/2018/05/17/git-delete-incorrect-files.html)
- 형변환 깔끔하게 처리하기 (naver, kakao, github) - 완료
- 회원가입 성공 시 Slack 채널로 메시지 보내기 - 완료(추가 사항 - body 에 넣어서 넘겨보기)
- swagger 에서 깔끔하게 볼 수 있도록 수정(현재는 페이지 이동으로 처리되어 있음)

### 해결해야할 사항

- google api 에서 userinfo 를 얻어야 함 -> userinfo 를 얻는 문서를 못 찾는중 (https://nect2r.tistory.com/9) - 완료
- github api 에서 userinfo 를 얻어야 함 -> 아직 시도 안해봄 - 완료
- 무료 도메인을 얻어야 함 -> freenom 이란 사이트에서 무료 도메인을 얻을 수 있으나 현재 not available 인 상황

### Linux api setting

- brew install curl (curl 을 설치할 수 있음)
- curl -X GET http://127.0.0.1:8080/auth/GOOGLE (Rest API 요청)

### request api

- localhost:8080/login
    1. google -> localhost:8080/auth/GOOGLE
    2. kakao -> localhost:8080/auth/KAKAO
    3. naver -> localhost:8080/auth/NAVER
    4. github -> localhost:8080/auth/GITHUB

- login 완료 후
    - slack api 로 해당 채널에서 social login type 과 email 확인가능

### swagger

- localhost:8080/swagger-ui.html

### 프로젝트

- 4가지의 social login 이 가능
- login 과 동시에 db에 저장되며 회원가입 처리
- 회원가입되면 slack 채널에 정보 전송

### 느낀점

- 해당 프로젝트를 진행하며 구조에 대해 더 신경을 써야겠다고 느낌
- REST API 에 대해 더 신경을 써야겠음 
