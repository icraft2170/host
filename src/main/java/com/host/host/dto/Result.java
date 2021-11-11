package com.host.host.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Result<T> {
    private T data;
}
