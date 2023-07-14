# NBC_week8

* 작성한 API 명세 (수정 중)

|API 명|Method|URL|Request Header|Request|Response|Response Header|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|회원가입|POST|api/user/signup||{"username": "part", "password": "partial123", "role": "USER"}|{"msg": "SUCCESS_SIGN_UP", "statusCode": 200}||
|로그인|POST|api/user/login||{"username": "part", "password": "partial123"}|{"msg": "SUCCESS_LOGIN", "statusCode": 200}|Header : {"Authorization" : "Bearer... "}|
|전체 게시글 조회|GET|api/post|||[ {"title": "title from part", "username": "part", "contents": "content from part", "createdAt": "2023-06-28T10:00:04.032185", "modifiedAt": "2023-06-28T10:00:04.032185", "commentList": [] },  {"title": "good", "username": "part", "contents": "no, bad", "createdAt": "2023-06-28T09:59:48.081672", "modifiedAt": "2023-06-28T10:00:56.025748", "commentList": [] } ]||
|선택 게시글 조회|GET|api/post/{id}|||{"title": "title from part", "username": "part", "contents": "content from part", "createdAt": "2023-06-28T10:00:04.032185", "modifiedAt": "2023-06-28T10:00:04.032185", "commentList": [] }||
|게시글 생성|POST|api/post|Header : {"Authorization" : "Bearer... "}|{"title": "title from part", "contents": "content from part"}|{"title": "title from part", "username": "part", "contents": "content from part", "createdAt": "2023-06-28T10:00:04.0321854", "modifiedAt": "2023-06-28T10:00:04.0321854", "commentList": [] }||
|게시글 수정|PUT|api/post/{id}|Header : {"Authorization" : "Bearer... "}|{"title": "good", "contents": "no, bad" }|{"title": "good", "username": "part", "contents": "no, bad", "createdAt": "2023-06-28T09:59:48.081672", "modifiedAt": "2023-06-28T10:00:56.0170722", "commentList": [] }||
|게시글 삭제|DELETE|api/post/{id}|Header : {"Authorization" : "Bearer... "}||{"msg": "SUCCESS_DELETE_POST", "statusCode": 200}||
|댓글 생성|POST|api/comment|Header : {"Authorization" : "Bearer... "}|{"postId": 2, "content": "nice post"}|{"commentId": 1, "username": "part", "content": "nice post"}||
|댓글 수정|PUT|api/comment/{id}|Header : {"Authorization" : "Bearer... "}|{"content": "YKW? it was actually bad.."}|{"commentId": 1, "username": "part", "content": "YKW? it was actually bad.."}||
|댓글 삭제|DELETE|api/comment/{id}|Header : {"Authorization" : "Bearer... "}||{"msg": "SUCCESS_DELETE_COMMENT", "statusCode": 200}||

====================================== 

* `구현되지 않은 점`

1. 카테고리와 게시글 간의 N:M 연관관계 구현 (진행 중)
2. RefreshToken 적용
3. Swagger 적용
