# DB연결방법

## JNDI외 DBCP

### JNDI(Java Naming and Directory Interface) 란?
* JNDI의 정의를 보면 디렉터리 서비스에서 제공하는 데이터 및 객체를 발견하고 참고(lookup)하기 위한 자바 API라고 되어있다. 
* 쉽게 말하면 외부에 있는 객체를 가져오기 위한 기술이다. Tomcat와 같은 WAS를 보면 특정 폴더에 필요한 데이터 소스(라이브러리)가 있는데 그것을 우리가 사용하기 위해 JNDI를 이용해서 가져오는 것이다.

### DBCP(Database Connection Pool, 커넥션 풀) 란?
* 데이터베이스와 연결된 커넥션을 미리 만들어서 저장해두고 있다가 필요할 때 저장된 공간(pool)에서 가져다 쓰고 반환하는 기법을 말한다. 
* 커넥션 풀을 이용하면 커넥션을 미리 만들어두고 사용하기 때문에 매번 사용자가 요청할 경우 드라이버를 로드하고, 커넥션 객체를 생성해 연결하고 종료하는 비효율적인 작업을 하지 않아도 된다. 
* 즉 데이터베이스의 부하를 줄일 수 있고 자원을 효율적으로 관리할 수 있다.

### JNDI와 DBCP의 전체적인 구성
![JNDI와DBCP 구성](https://user-images.githubusercontent.com/65409092/95290824-306ee000-08a9-11eb-8e10-b6f2686be84d.PNG)

### 1. API 관련 jar 파일 설치 - lib폴더
![1 API관련 jar파일 설치](https://user-images.githubusercontent.com/65409092/95290965-79bf2f80-08a9-11eb-9dec-82ce9ebec6ca.PNG)

### 2. DBCP 정보설정 - context.xml
![2 DBCP정보설정 context](https://user-images.githubusercontent.com/65409092/95291065-aecb8200-08a9-11eb-8932-59f6688b3790.PNG)

### 3. Server -> context.xml
```
<Resource auth="Container"
    		name="jdbc/orcl"
    		driverClassName="oracle.jdbc.driver.OracleDriver"
    		type="javax.sql.DataSource"
    		url="jdbc:oracle:thin:@localhost:1521:orcl"
    		username="scott"
    		password="1234"
    		loginTimeout="10"
    		maxActivce="50"
    		maxIdle="20"
    		maxWait="5000"
    		testOnBorrow="true"/>
```

### 4. JNDI 리소스 사용 설정 - web.xml
![3 JNDI리소스설정  web](https://user-images.githubusercontent.com/65409092/95291258-15e93680-08aa-11eb-9c45-ab40983f9dc6.PNG)

### 5. [WebContent] - [WEB-INF]에 web.xml
```
<resource-ref> 
    <description>basicjsp db</description> 
    <res-ref-name>jdbc/orcl</res-ref-name> 
    <res-type>javax.sql.DataSource</res-type> 
    <res-auth>Container</res-auth> 
  </resource-ref>
```

###  6. 자바 DAO 코드
```
private static UserDAO instance = new UserDAO();
    //.jsp페이지에서 DB연동빈인 UserDAO클래스의 메소드에 접근시 필요
    public static UserDAO getInstance() {
        return instance;
    }
	
    //커넥션풀로부터 Connection객체를 얻어냄
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/orcl");
        return ds.getConnection();
    }
```

[공부한 출처](https://all-record.tistory.com/104)
