package org.bbaemin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemImageResponse {

    private String url;
    private String type;
}
