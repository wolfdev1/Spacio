package net.redsierra.Spacio.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.redsierra.Spacio.config.BotConfig;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class Database {

    BotConfig config = new BotConfig();
    MongoClient client = MongoClients.create(config.getDatabaseUrl());
    MongoDatabase database = client.getDatabase("test");

    public Database() throws IOException, ParseException, URISyntaxException {
    }



    public MongoDatabase getDatabase() {
        return database;
    }


}
