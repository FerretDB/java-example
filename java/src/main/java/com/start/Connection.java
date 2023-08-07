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

        System.out.println(res.toJson());

        command = new Document("dropDatabase", 1);
        res = database.runCommand(command);

        System.out.println(res.toJson());

        List<Document> docList = new ArrayList<Document>(4);
        for (int i = 1; i < 5; i++) {
            Document doc = new Document("_id", i);
            doc.append("a", i);
            docList.add(doc);
        }

        MongoCollection<Document> collection = database.getCollection("foo");
        InsertManyResult imres = collection.insertMany(docList);

        System.out.println(imres.toString());

        Document doc = collection.find(eq("a", 4)).first();

        assert res == doc;

        mongoClient.close();
    }
}
