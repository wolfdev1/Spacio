package net.redsierra.Spacio.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.database.Database;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BotConfig {

    private final Database database;
    private final JSONObject file;
    private final MongoCollection<Document> textChannels;

    public BotConfig() {
        this.database = new Database(this);
        this.file = getConfigFile();
        this.textChannels = database.getDatabase().getCollection("text_channels");
    }

    public JSONObject getConfigFile() {
        try (InputStream inputStream = Spacio.class.getResourceAsStream("/config.json")) {
            Objects.requireNonNull(inputStream, "config.json not found");
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public MongoDatabase getDatabase() {
        return database.getDatabase();
    }
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    public String getProjectVersion() {
        JSONObject version = (JSONObject) file.get("version");
        String identifier = (String) version.get("identifier");
        return String.format("%s.%s.%s-%s.%s",
                version.get("major"), version.get("minor"), version.get("patch"), identifier, version.get("build"));

    }

    public String getDefaultGuildId() {
        return System.getenv("DEFAULT_GUILD_ID");
    }

    private String getChannelIdByName(String channelName) {
        try {
            return Objects.requireNonNull(textChannels.find(new Document("name", channelName)).first()).getString("id");
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLogChannelId() {
        return getChannelIdByName("logs");
    }

    public String getWelcomeChannelId() {
        return getChannelIdByName("welcome");
    }

    public String getReportsChannel() {
        return getChannelIdByName("reports");
    }

    public String getCommandsChannelId() {
            return getChannelIdByName("commands");

    }

    public String getDatabaseUrl() {
        return System.getenv("MONGO_URI");
    }

    public Color getSystemColor() {
        return Color.decode(file.get("systemColor").toString());
    }

    public MongoCollection<Document> getXPChannels() {
        return database.getDatabase().getCollection("xp_channels");
    }
}
