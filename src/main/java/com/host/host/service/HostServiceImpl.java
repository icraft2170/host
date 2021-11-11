package com.host.host.service;

import com.host.host.api.request.CreateHostRequest;
import com.host.host.api.request.DeleteHostRequest;
import com.host.host.api.request.ModifyHostRequest;
import com.host.host.domain.host.Host;
import com.host.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service @Transactional
public class HostServiceImpl implements HostService{
    private final HostRepository hostRepository;

    @Override
    public boolean isSizeGreaterThanMaxCount() {
        return hostRepository.isCountByHostGreaterThanOrEqualToMaxCount();
    }

    @Override
    public void joinHost(CreateHostRequest createHostRequest) {
        hostRepository.save(Host.createHost(createHostRequest.getName(),createHostRequest.getAddress()));
    }

    @Override
    public String updateHost(ModifyHostRequest modifyHostRequest) {
        Host findHost = hostRepository.findByName(modifyHostRequest.getName());
        findHost.updateByHostRequest(modifyHostRequest.getNewName(),modifyHostRequest.getNewAddress());
        return findHost.getName();
    }

    @Override
    public void deleteHostByDeleteHostRequest(DeleteHostRequest deleteHostRequest) {
        hostRepository.deleteByName(deleteHostRequest.getName());
    }

    @Override
    public boolean isDuplicateHost(String name, String address) {
        return hostRepository.isDuplicateHost(name, address);

    }
}
