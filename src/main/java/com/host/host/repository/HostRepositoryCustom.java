package com.host.host.repository;

public interface HostRepositoryCustom {
    boolean isCountByHostGreaterThanOrEqualToMaxCount();

    boolean isDuplicateHost(String name, String address);
}
