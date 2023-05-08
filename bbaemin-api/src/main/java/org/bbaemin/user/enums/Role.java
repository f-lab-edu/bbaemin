package org.bbaemin.user.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
