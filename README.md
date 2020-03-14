## Spring-AOP
Spring-AOP의 개념과 사용방법에 대한 레포지토리

### 참고 자료

jojoldu님의 [AOP 정리](https://jojoldu.tistory.com/69)를 읽고 작성

### AOP란?

Spring의 핵심 기술 중 하나로 어플리케이션에 걸쳐 사용되는 기능을 재사용할 수 있도록 지원하는 기능이다.

즉, 핵심기능 이외에 **공통되는 부가기능을 추출하는 것**이다.

- 객체 지향 프로그래밍(OOP)는 핵심 기능의 공통된 기능을 재사용하기 위해 상속 및 Composition을 사용
- 관점 지향 프로그래밍(AOP)는 부가 기능의 공통된 기능을 재사용하기 위한 방법 (부가 기능은 어플리케이션의 여러 부분에서 재사용되기 떄문에 상속 및 Composition을 사용하기 힘들다.)

**AOP의 장점은 다음과 같다.**

- 어플리케이션의 전체에 흩어진 부분들을 한 곳에서 관리 가능
- 비즈니스 로직에 집중할 수 있도록 만든다.

**AOP 용어**

- **Target**

    부가 기능을 부여할 대상

- **Aspect**

    부가 기능 모듈 (OOP의 모듈은 Object)로써 부가될 기능을 정의한 **Advise**와 부가 기능을 어디에 적용할지 결정하는 **PointCut**으로 구성되어 있다.

    - **Advise**

        부가 기능을 구현한 구현체로 **무엇**을 **언제** 할지를 나타낸다. target에 종속되지 않으므로 부가 기능에 집중할 수 있다.

    - **JoinPoint**

        Advise가 적용될 수 있는 위치를 의미한다. **Spring에서는 Method만 JoinPoint로 정의하고 있다.** *(다른 프레임워크에서는 예외가 발생할 경우, 필드값이 수정될 경우 등도 지원)*

    - **PointCut**

        Advise를 적용할 JoinPoint를 선별하는 기능을 정의

- **Proxy**

    타켓을 감싸서 타켓의 요청을 받아 선처리 → 메소드 수행 → 후처리를 실행시키도록 구성된 객체

- **Introduction**

    타켓 클래스에 코드 변경없이 신규 메서드나 멤버 변수를 추가할 수 있도록 한다.

- **Weaving**

    지정된 객체에 Aspect를 적용하여 새로운 Proxy 객체를 

    Spring AOP는 런타임에 Proxy 객체가 생성된다.

### 구현

**의존성 추가**

`spring-boot-starter-aop`를 추가하여야 하지만, `spring-boot-starter-data-jpa`에 추가가 되어있으므로 따로 수정하지 않았다.

먼저, 수행시간을 측정하는 Aspect를 작성한다.

**Performance**

    @Aspect
    public class Performance {
        @Around("execution(* com.sphong.aop_example.service.BoardService.getBoards(..))")
        public Object calculateExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
            Object result = null;
            try {
                long start = System.currentTimeMillis();
                result = proceedingJoinPoint.proceed();
                long end = System.currentTimeMillis();
    
                System.out.println("Execution Time : " + (end - start));
            } catch (Throwable throwable) {
                System.out.println("Exception!");
            }
            return result;
        }
    }

위에서 정의한 Aspect를 Bean으로 등록한다. (아래의 두 방법 모두 가능)

    @Configuration
    public class AspectConf {
        @Bean
        public Performance performance() {
            return new Performance();
        }
    }
    ---
    
    @Componenet
    @Aspect
    public class Performance() {
    	.
    	.
    }

빈으로 등록된 Aspect의 Proxy를 생성하고, annotation을 해석할 수 있도록 설정을 추가한다.

    **@EnableAspectJAutoProxy**
    @SpringBootApplication
    public class AopExampleApplication implements CommandLineRunner {
    
        private final BoardRepository boardRepository;
        private final UserRepository userRepository;
    
        public AopExampleApplication(BoardRepository boardRepository, UserRepository userRepository) {
            this.boardRepository = boardRepository;
            this.userRepository = userRepository;
        }
    
        public static void main(String[] args) {
            SpringApplication.run(AopExampleApplication.class, args);
        }
    
        @Override
        public void run(String... args) throws Exception {
            for(int i=1;i<=100;i++){
                boardRepository.save(new Board(i+"번째 게시글의 제목", i+"번째 게시글의 내용"));
                userRepository.save(new User(i+"@email.com", i+"번째 사용자"));
            }
        }
    }

### 코드 설명

- `@Around("execution(* com.sphong.aop_example.service.BoardService.getBoards(..))")`
    - `@Around`: Advise으로 무엇을 언제할지를 나타낸다.
        - 무엇 : **calculatePerformanceTime()**
        - 언제 : **@Around (Around의 경우 반드시 타켓 메소드를 지칭하는 Proceed()가 호출되어야 한다.)**
            - 시점을 나타내는 Annotation
                - @Before : 타켓 메소드가 호출되기 이전에 수행
                - @After : 타겟 메소드의 결과에 관계없이 완료되면 수행
                - @AfterReturning : 타겟 메소드가 정상적인 값을 반환하면 수행
                - @AfterThrowing : 타겟 메소드가 수행 중 예외를 던지게 되면 수행
                - @Around : 타켓 메소드의 호출 전/후에 수행

    - `execution(* com.sphong.aop_example.service.BoardService.getBoards(..))`

        이 문자열을 **포인트컷 표현식**이라고 한다. 포인트컷 표현식은 2가지로 구성되는데

        - 지정자 : JoinPoint를 어떤 기준으로 선별할지에 대한 정의
            - args() : 메소드 인자가 타겟 명세에 포함된 타입일 경우
            - @args() : 메소드의 인자가 타겟 명세에 포함된 어노테이션 타입을 가지는 경우
            - execution() : 메소드명, 클래스/인터페이스, 인자 타입, 리턴 타입, 예외 타입 등 전부 조합가능한 지정자 (**가장 많이 사용**)
            - within() : execution 지정자에서 클래스/인터페이스까지만 범위 지정이 가능한 지정자
            - @within() : 주어진 어노테이션을 사용하는 타입으로 선언된 메소드
            - this() : 타겟 메소드가 지정된 빈 타입의 인스턴스인 경우
            - target() : 타겟 메소드가 지정된 타입의 인스턴스인 경우 (빈이 아닌)
            - @annotation : 타겟 메소드에 특정 어노테이션이 지정된 경우

        - 타겟 명세

            지정자에게 전달한 타겟 메소드의 명세를 표현한다.

        **포인트컷 표현식은 변수처럼 메소드에 담고, 논리 연산자를 사용하여 여러 표현식을 나타낼 수 있다.**

            @Pointcut("execution(* com.sphong.aop_example.service.BoardService.getBoards(..))")
            public void getBoards() {}
            
            @Pointcut("execution(* com.sphong.aop_example.service.UserService.getUsers(..))")
            public void getUsers() {}
            
            @Around("getBoards() || getUsers()")
            public Object calculateExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
                Object result = null;
                try {
                    long start = System.currentTimeMillis();
                    result = proceedingJoinPoint.proceed();
                    long end = System.currentTimeMillis();
            
                    System.out.println("Execution Time : " + (end - start));
                } catch (Throwable throwable) {
                    System.out.println("Exception!");
                }
                return result;
              }

---

**인자가 존재하는 Aspect를 사용하여 Aspect에서 타켓 메소드에 전달된 인자값을 사용하는 방법**

    @Component
    @Aspect
    public class UpdateHistory {
        @Autowired
        private HistoryRepository historyRepository;
    
        @Pointcut("execution (* com.sphong.aop_example.service.UserService.update(*)) && args(user)")
        public void updateUser(User user) {}
    
        @AfterReturning("updateUser(user)")
        public void saveHistory(User user) {
            Date updateDate = new Date();
            historyRepository.save(History.builder().userId(user.getIdx()).updateDate(updateDate).build();
        }
    }

위의 코드에서 arg(user)는 인자로 들어온 user를 advice에서 사용할 수 있도록 해준다.

