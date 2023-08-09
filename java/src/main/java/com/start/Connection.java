package com.start;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;

public class Connection {

    public static void main(String[] args) {
        MongoClient mongoClient;
        mongoClient = MongoClients.create(args[args.length-1]);

        MongoDatabase database = mongoClient.getDatabase("test");

        Document command = new Document("ping", 1);
        Document res = database.runCommand(command);

        assert res == new Document("ok", 1.0) : "ping failed";

        command = new Document("dropDatabase", 1);
        res = database.runCommand(command);

        assert res == new Document("ok", 1.0) : "dropDatabase failed";

        List<Document> docList = new ArrayList<Document>(4);
        docList.add(new Document("_id", 1).append("a", 1));
        docList.add(new Document("_id", 2).append("a", 2));
        docList.add(new Document("_id", 3).append("a", 3));
        docList.add(new Document("_id", 4).append("a", 4));

        MongoCollection<Document> collection = database.getCollection("foo");
        collection.insertMany(docList);

        Document doc = collection.find(eq("a", 4)).first();
        assert res == doc : "Value should be 4";

        mongoClient.close();
    }
}
