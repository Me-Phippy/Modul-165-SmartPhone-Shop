package org;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class MongoDBConfig {

    static MongoDatabase databaseName;

    public static MongoClient createMongoClient() {
        String connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient;

    }
    public static String getDatabaseName() {
        String databaseName = "smartphone_shop";
        return databaseName;
    }
}
