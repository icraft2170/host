package com.host.host.service;


import com.host.host.api.request.CreateHostRequest;
import com.host.host.api.request.DeleteHostRequest;
import com.host.host.api.request.ModifyHostRequest;

public interface HostService {

    boolean isSizeGreaterThanMaxCount();

    void joinHost(CreateHostRequest createHostRequest);

    String updateHost(ModifyHostRequest modifyHostRequest);

    void deleteHostByDeleteHostRequest(DeleteHostRequest deleteHostRequest);

    boolean isDuplicateHost(String name, String address);

}
