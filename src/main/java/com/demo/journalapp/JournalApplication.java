package com.demo.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //We have to write this to enable transaction management in Spring boot and use @Transaction annotation for a method to be handled, Also we need to define a bean for PlatformTransactionManager whose implementation should be MongoTransactionManager
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager manager(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory); //MongoTransactionManager takes up a dbFactory which is of type MongoDatabaseFactory, It is the MongoDatabaseFactory which is helping us in executing all the queries that we are performing, session that is getting created.

    }

}
