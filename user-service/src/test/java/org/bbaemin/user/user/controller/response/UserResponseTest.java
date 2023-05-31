package org.bbaemin.user.user.controller.response;

import org.bbaemin.user.user.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseTest {

    @Test
    public void test() {

        User user = User.builder()
                .userId(1L)
                .email("user1@email.com")
                .nickname("user1")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
        UserResponse response = UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(user.getImage())
                .phoneNumber(user.getPhoneNumber())
                .build();
        Field[] userClassFields = User.class.getDeclaredFields();
        Field[] fields = UserResponse.class.getDeclaredFields();
        // TODO - 필드를 어떻게 빠짐없이 체크할까
        // + 필드 이름이 다를 때는?
        for (Field field : fields) {
/*
            field.setAccessible(true);
            Field userClassField = Arrays.stream(userClassFields)
                    .filter(f -> f.getName().equals(field.getName()))
                    .findAny().orElseThrow(RuntimeException::new);
            userClassField.setAccessible(true);
            assertEquals(field.get(response), userClassField.get(user));*/

            assertEquals(ReflectionTestUtils.getField(response, field.getName()),
                    ReflectionTestUtils.getField(user, field.getName()));
        }

    }


}
