
# null, 넌 누구니?  
  
"선배, 어디가 이상한건지 한 번만 봐주시면 안돼요?"

영민은 어떤 문제 때문에 한시간 째 하나도 일을 하지 못하고 있었다.

"무슨 일인데? 같이 보자."
"아니, 제가 어떤 테이블에서 A라는 컬럼의 값이 "Apple"이 아닌 것만 가져오려고 하거든요. 그러면 당연히 컬럼 값이 없는 것도 가져와야 하는것 아닌가요?"
"아, 그러니까 null 값을 가진 row도 가져와야 할 것 같은데, 안 그런가보구나? 그럴땐 이렇게 테스트를 해보는 게 이해하기엔 직관적이라 좋지. 같이 해볼까?"
 
선배는 로컬에 구동 중인 MySQL 서버에 새로운 데이터베이스를 생성하고, 예시 테이블을 만들고 데이터를 넣었다.
 
```sql
select * from `hobbies` where `secret_hobby` != 'SPANKING'
 ```
 
"그러니까 이런 쿼리를 실행하면 secret_hobby가 null인 row가 나오지 않는게 이상하다는 거지?"
"네. null도 당연히 나오는게 맞지 않나 싶어서요."

"음, 일단 MySQL에서 null이 어떤 취급을 받는지 한 번 살펴보는게 좋겠다. 이런 문제는 검색해 보면 정답을 찾을 수 있어. 그 중 [공식 문서]([https://dev.mysql.com/doc/refman/8.0/en/working-with-null.html](https://dev.mysql.com/doc/refman/8.0/en/working-with-null.html))의 설명을 함께 볼까?"
  
```
Conceptually, NULL means “a missing unknown value” and it is treated somewhat differently from other values.  
```  
  
"그러니까, 개념적으로 NULL은 식별되지 않은 값으로, 일반적인 값과는 다르게 다뤄진다는 말이네요."
"맞아. 실제로 Arithmetic operator인 <, >, =, <> 을 이용해 column 값과 null을 비교하면 모든 결과 값은 null로 나온단다. 예를 들어, "

```sql
select * from hobbies where secret_hobby = null;  
select * from hobbies where secret_hobby >= null;
```
  
"이런 비교는 모두 유효하지 않아. null 값을 비교할때는 항상 IS 연산자를 사용해야하는 이유란다."
"아하, 그러니까 애초에 column을 정의할 때 **꼭 nullable 해야하는 지**에 대해 고민을 먼저 하고, null 값이 포함되는 경우 **쿼리할 때 null 값을 또 적절히 고려**해야 하는거겠네요!"
"맞아. 그럼 시간도 남으니까 스프링 부트 서버도 하나 띄워서 예상대로 동작하는 지 한 번 확인해보자."

선배는 새로운 Spring boot 프로젝트를 시작하고, 테스트에 필요한 의존성 모듈을 설치한 뒤 빠르게 Entity, Repository 그리고 Controller를 만들어냈다. Mockito나 Junit5를 이용해 간단히 테스트 할 수도 있었지만, API에 대한 개념도 환기시켜줄 겸 MVC 구조로 테스트 세트를 구성했다.

서버를 구동하고 API를 요청해 보니 SQL 요청 결과와 같은 결과를 반환했다.

"이렇게 쿼리로 확인하고, 어플리케이션에서도 예상대로 동작한다는 걸 확인하니 잊어버리지 않고 오래 기억할 수 있을것 같아요."
"맞아. 앞으로 모르는게 있으면 검색하고 또 정리하는 습관을 들이면 많은 도움이 될거야."

영민은 가장 효율적이고 빠르게 문제 해결하는 방법에 대해 익혔다(심지어 페어 프로그래밍도 경험했다). 아마 오늘의 경험이 영민의 개발자 생활에 많은 도움이 되리란 사실을

오늘의 결론.
  
- Nullable 한 컬럼에 대해 검색할 때 NULL인 결과를 포함하고 싶거나 포함하고 싶지 않을 때에는 IS NULL, IS NOT NULL 조건을 함께 넣어주자.  
- value = NULL 등의 조건은 의미가 없다. 왜냐면 value가 무엇이든지 결과는 NULL 일테니까. NULL을 비교하고 싶을 때는 IS NULL, IS NOT NULL 을 사용하자.  
  
뱀다리 1) 공식문서에 따르면 GROUP BY 절에서 **NULL은 모두 같은 그룹으로** 묶인다고 한다.  
뱀다리 2) 공식문서에 따르면 ORDER BY 절에서 NULL은 **ASC인 경우 맨 앞에, DESC인 경우 맨 뒤에** 위치한다고 한다.  
뱀다리 3) Aggregation 함수들, 예를 들어 count, min, sum 등의 함수에서 컬럼을 nullable한 것으로 지정하면, 해당 항목이 null인 row들을 제외하고 계산한다고 한다. 이의 유일한 반례는 count(*)..

뱀다리 4) 테이블 및 데이터 세팅 SQL
```sql  
create table hobbies  
(  
  id bigint auto_increment,  
  name varchar(30) null,  
  secret_hobby varchar(30) null
);  
  
INSERT INTO `hobbies` (name, secret_hobby)  
VALUES 
('maru', 'SPANKING'),  
('politician', 'LYING'), 
('jesus', null)
```  
