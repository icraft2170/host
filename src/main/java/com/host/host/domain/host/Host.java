package com.host.host.domain.host;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;


@NoArgsConstructor
@Entity @Getter
public class Host {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "address", unique = true)
    private String address;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AliveStatus aliveStatus;

    public static Host createHost(String name, String address){
        return new Host(null, name, address, LocalDateTime.now(), null, AliveStatus.ALIVE_STATUS);
    }

    private Host(Long id, String name, String address, LocalDateTime registrationTime, LocalDateTime modifiedTime, AliveStatus aliveStatus) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.registrationTime = registrationTime;
        this.modifiedTime = modifiedTime;
        this.aliveStatus = aliveStatus;
    }

    public void updateByHostRequest(String name,String address) {
        if(name != null) this.name = name;
        if(address != null) this.address = address;
        this.modifiedTime = LocalDateTime.now();
    }

    public void checkAliveStatus(boolean alive) {
        if(alive){
            this.aliveStatus = AliveStatus.ALIVE_STATUS;
        }else{
            this.aliveStatus = AliveStatus.DISCONNECT_STATUS;
        }
    }
}
