# Java로 외부 api 호출연습 repository

### localhost:8080/auth/GOOGLE 을 주소창에 입력하였을때

1. OauthController에 매핑되어 'GOOGLE'은 query string에서 소셜 로그인 타입 인자로 받게 된다.
2. 소셜 로그인 타입을 OauthService의 request 메소드로 넘긴다.
3. OauthService에서 만들어둔 findSocialOauthByType 메소드에서 받을 수 있는 소셜 로그인 타입인지 확인한다.
   - 예외상황) 존재하지 않는 소셜 로그인 타입일 경우 예외 처리
4. 