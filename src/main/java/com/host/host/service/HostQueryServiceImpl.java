package com.host.host.service;

import com.host.host.api.request.GetHostRequest;
import com.host.host.api.response.HostResponse;
import com.host.host.domain.host.Host;
import com.host.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HostQueryServiceImpl implements HostQueryService{

    private final HostRepository hostRepository;

    @Override
    public HostResponse findHost(GetHostRequest getHostRequest) {
        Host findHost = hostRepository.findByName(getHostRequest.getName());
        return new HostResponse(findHost);

    }

    @Override
    public Page<HostResponse> findHosts(Pageable pageable) {
        return hostRepository.findAll(pageable).map(HostResponse::new);
    }
}
