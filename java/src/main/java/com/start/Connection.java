package com.start;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public static void main(String[] args) {
        for (String connectionUri : args) {
            try (MongoClient mongoClient = MongoClients.create(connectionUri)) {
                MongoDatabase database = mongoClient.getDatabase("test");

                try {
                    Document command = new Document("ping", 1);
                    Document res = database.runCommand(command);
                    System.out.println(res);
                } catch (MongoException e) {
                    System.err.println("An error occurred: " + e);
                }

            }
        }
    }
}
