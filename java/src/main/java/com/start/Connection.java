package com.start;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public static void main(String[] args) {
        // if set in the YAML file
        if (args[args.length-1] == "StableAPI") {
            uri = args[args.length-2];

            MongoClient client = MongoClients.create(
            MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri))
            .serverApi(
                ServerApi.builder()
                .version(ServerApiVersion.V1)
                .strict(false)
                .build()
                ).build()
            );
        }
        try (MongoClient mongoClient = MongoClients.create(args[0])) {
            MongoDatabase database = mongoClient.getDatabase("test");

            Document command = new Document("ping", 1);
            Document res = database.runCommand(command);

            System.out.println(res.toJson());

            command = new Document("dropDatabase", "test");
            res = database.runCommand(command);

            System.out.println(res);

            ArrayList docList = new ArrayList();
            for (var i = 1; i < 5; i++) {
                BasicDBObject doc = new BasicDBObject("_id", i);
                doc.append("a", i);
                docList.append(doc);
            }

            command = new Document("insert", docList);
            res = database.runCommand(command);

            System.out.println(res);

            doc = new BasicDBObject("a", 4);
            command = new Document("find", doc);
            res = database.runCommand(command);

            assert res == doc;

            mongoClient.close();
        }
    }
}
