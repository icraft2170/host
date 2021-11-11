package com.host.host.repository;

import com.host.host.domain.host.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host,Long>,HostRepositoryCustom{

    Host findByName(String name);
    void deleteByName(String name);
}
