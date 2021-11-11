package com.host.host.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor
public class DeleteHostRequest {
    @NotNull
    private String name;
}
