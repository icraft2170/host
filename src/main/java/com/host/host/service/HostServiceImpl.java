package com.host.host.service;

import com.host.host.api.response.HostResponse;
import com.host.host.domain.host.Host;
import com.host.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.host.host.api.ApiUtil.isAlive;

@Slf4j
@RequiredArgsConstructor
@Service @Transactional
public class HostServiceImpl implements HostService{
    private final HostRepository hostRepository;

    @Override
    public boolean isSizeGreaterThanMaxCount() {
        return hostRepository.isCountByHostGreaterThanOrEqualToMaxCount();
    }

    @Override
    public void joinHost(String address, String name) {
        hostRepository.save(Host.createHost(name,address));
    }

    @Override
    public void updateHost(String address, String name) {
        log.info("===========================");
        log.info("name : {} ip : {}",name,address);
        Host findHost = hostRepository.findByName(name);
        log.info("findHost is null {}",(findHost!=null));
        findHost.updateByHostRequest(name,address);
    }

    @Override
    public void deleteHostByDeleteHostRequest(String address, String name) {
        hostRepository.deleteByName(name);
    }

    @Override
    public HostResponse findHost(String hostName) {
        Host findHost = hostRepository.findByName(hostName);
        findHost.checkAliveStatus(isAlive(findHost.getAddress()));
        return new HostResponse(findHost);
    }

    @Override
    public Page<HostResponse> findHosts(Pageable pageable) {
        Page<Host> hosts = hostRepository.findAll(pageable);
        hosts.stream().forEach(host -> host.checkAliveStatus(isAlive(host.getAddress())));
        return hosts.map(HostResponse::new);
    }



    @Override
    public boolean isDuplicateHost(String name, String address) {
        return hostRepository.isDuplicateHost(name, address);
    }


}
