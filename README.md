# Java 로 외부 api 호출연습 repository

### localhost:8080/auth/GOOGLE 을 주소창에 입력하였을때

1. OauthController 에 매핑되어 'GOOGLE' 은 query string 에서 소셜 로그인 타입 인자로 받게 된다.
2. 소셜 로그인 타입을 OauthService 의 request 메소드로 넘긴다.
3. OauthService 에서 만들어둔 findSocialOauthByType 메소드에서 받을 수 있는 소셜 로그인 타입인지 확인한다.
   - 예외상황) 존재하지 않는 소셜 로그인 타입일 경우 예외 처리
4. GoogleOauth 클래스 선택하여 반환하고 getOauthRedirectURL 메소드를 통해 URL 을 조립하여 
   response.sendRedirect 를 통해 query string 을 보낸다.
   
5. callback URL 을 통해 구글에서 보내준 데이터를 json 형태로 받을 수 있다.
6. requestAccessToken 메소드를 통해 소셜 로그인 타입과 code 를 인자로 넘긴다.
7. 다시 한 번 GoogleOauth 클래스를 찾아서 requestAccessToken 메소드에 code 인자를 넘긴다.
8. Map 을 만들어 필요한 키, 벨류를 담고 post 로 넘긴다.
9. HttpStatus.OK 이면 정상적인 로그인 완료되어 getBody 를 할 수 있고, 아니라면 에러 발생