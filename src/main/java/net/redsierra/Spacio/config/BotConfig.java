package net.redsierra.Spacio.config;

import net.redsierra.Spacio.Spacio;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BotConfig {

    public BotConfig() throws IOException, ParseException {
    }

    public JSONObject getConfigFile() throws IOException, ParseException {

        InputStream inputStream = Spacio.class.getResourceAsStream("/config.json" );
        assert inputStream != null;
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    JSONObject file = getConfigFile();
    public String getBotToken(){
        return  System.getenv("BOT_TOKEN");

    }

    public String getReportsChannel(){
        return System.getenv("REPORTS_CHANNEL_ID");
    }

    public String getFactsApiKey(){
        return System.getenv("FACTS_API_KEY");
    }


    public String getProjectVersion(){
        JSONObject a = file;
        JSONObject version = (JSONObject) (a).get("version");
        String identifier = (String) version.get("identifier");
        return (version.get("major") + "." + version.get("minor") + "." +version.get("patch") + "-" + identifier +"."+ version.get("build"));

    }

    public String getDefaultGuildId(){
       return System.getenv("DEFAULT_GUILD_ID");
    }

    public String getLogChannelId(){
        return System.getenv("LOG_CHANNEL_ID");
    }

    public String getWelcomeChannelId(){
        return System.getenv("WELCOME_CHANNEL_ID");
    }

    public String getCommandsChannelId(){
        return System.getenv("COMMANDS_CHANNEL_ID");
    }

    public String getDatabaseUrl() {
        return System.getenv("MONGO_URI");
    }

    public List<String> getXPChannels(){
        List<String> xpChannels = new ArrayList<>();
        Map<String, String> env = System.getenv();

        for (String key : env.keySet()) {
            if (key.startsWith("XP_")) {
                String value = env.get(key);
                xpChannels.add(value);
            }
        }
        return xpChannels;
    }


}
