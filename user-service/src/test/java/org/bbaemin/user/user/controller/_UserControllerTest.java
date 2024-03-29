package org.bbaemin.user.user.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = UserController.class,
        properties = {"spring.config.location=classpath:application-test.yml"})
class _UserControllerTest {

    private final String BASE_URL = "/api/v1/users";
/*
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listUser() throws Exception {
        // given
//        doReturn(Collections.emptyList()).when(userService).getUserList();
        User user = User.builder()
                .userId(1L)
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        doReturn(List.of(user))
                .when(userService).getUserList();
        // when
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result").value(Matchers.hasSize(1)));
        // then
        verify(userService).getUserList();
    }

    @Test
    void getUser() throws Exception {
        // given
        User user = User.builder()
                .userId(1L)
                .email("user@email.com")
                .nickname("user")
                .password("password")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        doReturn(user)
                .when(userService).getUser(1L);
        // when
        mockMvc.perform(get(BASE_URL + "/{userId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.userId").value(1L))
                .andExpect(jsonPath("$.result.email").value(user.getEmail()))
                .andExpect(jsonPath("$.result.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.result.image").value(user.getImage()))
                .andExpect(jsonPath("$.result.phoneNumber").value(user.getPhoneNumber()));
        // then
        verify(userService).getUser(1L);
    }

    @Test
    void join() throws Exception {
        // given
        // when
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .passwordConfirm("passwordConfirm")
                .phoneNumber("010-1234-5678")
                .build();
        User user = User.builder()
                .userId(1L)
                .email(joinRequest.getEmail())
                .nickname(joinRequest.getNickname())
                .password(joinRequest.getPassword())
                .image(joinRequest.getImage())
                .phoneNumber(joinRequest.getPhoneNumber())
                .build();
        doReturn(user)
                .when(userService).createUser(any(User.class));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.userId").value(1L))
                .andExpect(jsonPath("$.result.email").value(user.getEmail()))
                .andExpect(jsonPath("$.result.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.result.image").value(user.getImage()))
                .andExpect(jsonPath("$.result.phoneNumber").value(user.getPhoneNumber()));
        // then
        verify(userService).createUser(any(User.class));
    }

    @Test
    void join_throws_MethodArgumentNotValidException_when_nickname_is_blank() throws Exception {
        // given
        // when
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user@email.com")
                .nickname("")
                .image(null)
                .password("password")
                .passwordConfirm("passwordConfirm")
                .phoneNumber("010-1234-5678")
                .build();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("FAIL"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.result.cause").exists());
        // then
        verify(userService, times(0)).createUser(any(User.class));
    }

    @Test
    void updateUserInfo() throws Exception {
        // given
        UpdateUserInfoRequest updateUserInfoRequest = UpdateUserInfoRequest.builder()
                .nickname("user")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        User user = User.builder()
                .userId(1L)
                .email("user@email.com")
                .nickname(updateUserInfoRequest.getNickname())
                .password("password")
                .image(updateUserInfoRequest.getImage())
                .phoneNumber(updateUserInfoRequest.getPhoneNumber())
                .build();
        doReturn(user)
                .when(userService).updateUserInfo(1L, "user", null, "010-1234-5678");
        // when
        mockMvc.perform(patch(BASE_URL + "/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserInfoRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.userId").value(1L))
                .andExpect(jsonPath("$.result.email").value(user.getEmail()))
                .andExpect(jsonPath("$.result.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.result.image").value(user.getImage()))
                .andExpect(jsonPath("$.result.phoneNumber").value(user.getPhoneNumber()));
        // then
        verify(userService).updateUserInfo(1L,
                updateUserInfoRequest.getNickname(), updateUserInfoRequest.getImage(), updateUserInfoRequest.getPhoneNumber());
    }

    @Test
    void quit() throws Exception {
        // given
        doNothing()
                .when(userService).quit(1L);
        // when
        // then
        mockMvc.perform(patch(BASE_URL + "/{userId}/quit", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").doesNotExist());
    }*/
}
