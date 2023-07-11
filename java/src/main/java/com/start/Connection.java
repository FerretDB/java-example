package com.start;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(args[0])) {
            MongoDatabase database = mongoClient.getDatabase("test");

            Document command = new Document("ping", 1);
            Document res = database.runCommand(command);

            System.out.println(res.toJson());
            mongoClient.close();
        }
    }
}
