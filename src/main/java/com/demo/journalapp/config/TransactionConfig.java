package com.demo.journalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //We have to write this to enable transaction management in Spring boot and use @Transaction annotation for a method to be handled, Also we need to define a bean for PlatformTransactionManager whose implementation should be MongoTransactionManager
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager manager(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory); //MongoTransactionManager takes up a dbFactory which is of type MongoDatabaseFactory, It is the MongoDatabaseFactory which is helping us in executing all the queries that we are performing, session that is getting created.

    }
}
