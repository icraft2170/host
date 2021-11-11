package com.host.host.api.response;

import com.host.host.domain.host.AliveStatus;
import com.host.host.domain.host.Host;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data @NoArgsConstructor
public class HostResponse {
    private String name;
    private String address;
    private LocalDateTime lastAliveTime;
    private AliveStatus aliveStatus;

    public HostResponse(Host host) {
        this.name = host.getName();
        this.address = host.getAddress();
        this.lastAliveTime = host.getLastAliveTime();
        this.aliveStatus = host.getAliveStatus();
    }
}
