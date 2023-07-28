package com.start;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.ConnectionString;

public class Connection {

    public static void main(String[] args) {
        MongoClient mongoClient;

        // if set in the YAML file
        if (args[args.length-1] == "StableAPI") {
            String uri = args[args.length-2];

            mongoClient = MongoClients.create(
            MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri))
            .serverApi(
                ServerApi.builder()
                .version(ServerApiVersion.V1)
                .strict(false)
                .build()
                ).build()
            );
        } else {
            mongoClient = MongoClients.create(args[args.length-1]);
        }
        try {
            MongoDatabase database = mongoClient.getDatabase("test");

            Document command = new Document("ping", 1);
            Document res = database.runCommand(command);

            System.out.println(res.toJson());

            command = new Document("dropDatabase", "test");
            res = database.runCommand(command);

            System.out.println(res);

            ArrayList docList = new ArrayList();
            for (int i = 1; i < 5; i++) {
                BasicDBObject doc = new BasicDBObject("_id", i);
                doc.append("a", i);
                docList.add(doc);
            }

            command = new Document("insert", docList);
            res = database.runCommand(command);

            System.out.println(res);

            Document doc = new Document("a", 4);
            command = new Document("find", doc);
            res = database.runCommand(command);

            assert res == doc;

            mongoClient.close();
        } finally {
            // ...
        }
    }
}
