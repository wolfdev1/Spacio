package net.redsierra.Spacio.database.objects;

import net.redsierra.Spacio.database.Database;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;


public class WarnObject
{
    public void addInfraction(String userId, String reason, String modId, String warnId, String chId) throws IOException, ParseException, URISyntaxException {
        Database db = new Database();
        Document warn = new Document("userId", userId)
                .append("reason", reason)
                .append("warnId", warnId)
                .append("channelId", chId)
                .append("modId", modId);

        db.getDatabase().getCollection("warns").insertOne(warn);
    }
}



