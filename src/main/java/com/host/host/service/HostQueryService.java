package com.host.host.service;

import com.host.host.api.request.GetHostRequest;
import com.host.host.api.response.HostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostQueryService {
    HostResponse findHost(GetHostRequest getHostRequest);
    Page<HostResponse> findHosts(Pageable pageable);
}
