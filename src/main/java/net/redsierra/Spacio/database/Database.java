package net.redsierra.Spacio.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.redsierra.Spacio.config.BotConfig;


public class Database {

    private final MongoDatabase database;

    public Database(BotConfig config) {
        MongoClient client = MongoClients.create(config.getDatabaseUrl());
        database = client.getDatabase("test");
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}

