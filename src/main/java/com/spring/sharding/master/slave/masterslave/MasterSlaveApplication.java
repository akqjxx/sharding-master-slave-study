package com.spring.sharding.master.slave.masterslave;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.spring.sharding.master.slave.masterslave.mapper")
public class MasterSlaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterSlaveApplication.class, args);
    }

}
