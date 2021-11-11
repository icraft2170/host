package com.host.host.repository;

import com.host.host.consts.HostConst;
import com.host.host.domain.host.QHost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import static com.host.host.consts.HostConst.MAX_HOST_COUNT;
import static com.host.host.domain.host.QHost.host;

import javax.persistence.EntityManager;

@Slf4j
public class HostRepositoryImpl implements HostRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    public HostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean isCountByHostGreaterThanOrEqualToMaxCount() {
        long count = queryFactory.selectFrom(host)
                .fetchCount();
        return count <= MAX_HOST_COUNT - 1;

    }

    @Override
    public boolean isDuplicateHost(String name, String address) {
        long count = queryFactory.selectFrom(host)
                .where(host.name.eq(name).or(host.address.eq(address)))
                .fetchCount();
        return (count != 0);
    }
}
