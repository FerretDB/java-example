package com.start;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("uri", true, "MongoDB connection string.");
        options.addOption("strict", false, "Use strict stable API mode.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        MongoClient mongoClient;
        String uri = cmd.getOptionValue("uri");

        if (cmd.hasOption("strict")) {
            ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .strict(true)
                .build();
            
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

            mongoClient = MongoClients.create(settings);
        } else {
            mongoClient = MongoClients.create(uri);
        }

        MongoDatabase database = mongoClient.getDatabase("test");

        Document command = new Document("ping", 1);
        Document res = database.runCommand(command);

        assert res.getDouble("ok").equals(1.0) : "ping failed";

        command = new Document("dropDatabase", 1);
        res = database.runCommand(command);

        assert res.getDouble("ok").equals(1.0) : "dropDatabase failed";

        List<Document> docList = new ArrayList<Document>(4);
        docList.add(new Document("_id", 1).append("a", 1));
        docList.add(new Document("_id", 2).append("a", 2));
        docList.add(new Document("_id", 3).append("a", 3));
        docList.add(new Document("_id", 4).append("a", 4));

        MongoCollection<Document> collection = database.getCollection("foo");
        collection.insertMany(docList);

        Document actual = collection.find(eq("a", 4)).first();
        assert actual.equals(new Document("_id", 4).append("a", 4)) : "Value should be 4";

        mongoClient.close();
    }
}
