package org.bbaemin.user.user.service;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@DataJpaTest(properties = {"spring.config.location=classpath:application-test.yml"})
class _UserServiceTest {
/*
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    void init() {
        userService = new UserService(userRepository);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void getUserList() {
        // given
        User user = User.builder()
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        User saved = userRepository.save(user);

        // when
        List<User> userList = userService.getUserList();
        // then
        assertThat(userList.size()).isEqualTo(1);
    }

    @Test
    void getUser() {
        // given
        User user = User.builder()
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        User saved = userRepository.save(user);

        // when
        User findUser = userService.getUser(saved.getUserId());
        // then
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            assertEquals(ReflectionTestUtils.getField(findUser, field.getName()),
                    ReflectionTestUtils.getField(user, field.getName()));
        }
    }

    @Test
    void getUser_throws_NoSuchElementException() {
        // given
        // when
        // then
        assertThrows(NoSuchElementException.class, () -> {
            userService.getUser(10L);
        }, "userId : " + 10L);
    }

    @Test
    void createUser() {

        // given
        // when
        User user = User.builder()
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        User saved = userService.createUser(user);

        // then
        User findUser = userRepository.findById(saved.getUserId())
                .orElseThrow(NoSuchElementException::new);
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            assertEquals(ReflectionTestUtils.getField(findUser, field.getName()),
                    ReflectionTestUtils.getField(user, field.getName()));
        }
    }

    @Test
    void updateUserInfo() {
        // given
        User user = User.builder()
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        User saved = userRepository.save(user);

        // when
        userService.updateUserInfo(saved.getUserId(), "updated_nickname", "image", "010-1111-2222");
        // then
        User findUser = userRepository.findById(saved.getUserId())
                .orElseThrow(NoSuchElementException::new);
        assertEquals("user@email.com", findUser.getEmail());
        assertEquals("updated_nickname", findUser.getNickname());
        assertEquals("password", findUser.getPassword());
        assertEquals("image", findUser.getImage());
        assertEquals("010-1111-2222", findUser.getPhoneNumber());
    }

    @Test
    void quit() {

        // given
        User user = User.builder()
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        User saved = userRepository.save(user);

        // when
        userService.quit(saved.getUserId());
        // then
        User findUser = userRepository.findById(saved.getUserId())
                .orElseThrow(NoSuchElementException::new);
        assertTrue(findUser.isDeleted());
        assertNotNull(findUser.getDeletedAt());
    }*/
}
