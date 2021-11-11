package com.host.host.service;


import com.host.host.api.response.HostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostService {

    boolean isSizeGreaterThanMaxCount();

    void joinHost(String address, String name);

    void updateHost(String address, String name);

    void deleteHostByDeleteHostRequest(String address, String name);

    boolean isDuplicateHost(String name, String address);

    HostResponse findHost(String hostName);

    Page<HostResponse> findHosts(Pageable pageable);

}
