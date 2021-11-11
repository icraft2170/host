package com.host.host.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ModifyHostRequest {
    private String name;
    private String newName;
    private String newAddress;
}
