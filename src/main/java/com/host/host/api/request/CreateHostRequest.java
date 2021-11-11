package com.host.host.api.request;

import com.host.host.domain.host.Host;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor
public class CreateHostRequest {
    @NotNull
    private String name;
    @NotNull
    private String address;
}
