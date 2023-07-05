package net.redsierra.Spacio.config;

import net.redsierra.Spacio.Spacio;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;

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


        JSONObject a = file;


        JSONObject secrets = (JSONObject) (a).get("secrets");


        return (String) secrets.get("token");

    }

    public String getReportsChannel(){


        JSONObject a = file;


        JSONObject secrets = (JSONObject) (a).get("channels");


        return (String) secrets.get("reports");

    }

    public String getFactsApiKey(){


        JSONObject a = file;


        JSONObject api = (JSONObject) (a).get("api");


        return (String) api.get("factsKey");

    }


    public String getProjectVersion(){
        JSONObject a = file;


        JSONObject version = (JSONObject) (a).get("version");
        String identifier = (String) version.get("identifier");

        return (version.get("major") + "." + version.get("minor") + "." +version.get("patch") + "-" + identifier +"."+ version.get("build"));


    }

    public String getProjectClassifier() {
        JSONObject a = file;
        JSONObject version = (JSONObject) (a).get("version");
        return version.get("classifier").toString();
    }

    public String getDefaultGuildId(){

        JSONObject a = file;


        return (String) (a).get("guildId");
    }

    public String getLogChannelId(){
        JSONObject a = file;

        JSONObject channels = (JSONObject) (a).get("channels");

        return (String) (channels).get("logs");

    }

    public String getWelcomeChannelId(){

        JSONObject a = file;

        JSONObject channels = (JSONObject) (a).get("channels");

        return (String) (channels).get("welcome");

    }

    public String getCommandsChannel(){

        JSONObject a = file;

        JSONObject channels = (JSONObject) (a).get("channels");

        return (String) (channels).get("commands");

    }

    public String getDatabaseUrl() {

        JSONObject a = file ;

        JSONObject database = (JSONObject) (a).get("secrets");

        return (String) (database).get("dbUrl");

    }

    public JSONObject getXPChannels(){

        JSONObject a = file;

        JSONObject channels = (JSONObject) (a).get("channels");

        return (JSONObject) (channels).get("xpChannels");

    }


}
