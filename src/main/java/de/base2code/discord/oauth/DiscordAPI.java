package de.base2code.discord.oauth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.base2code.discord.oauth.model.DiscordUser;
import de.base2code.discord.oauth.properties.PropertyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class DiscordAPI {
    private static final Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
    private final String accessToken;
    private final PropertyManager propertyManager = new PropertyManager();
    private final OkHttpClient client = new OkHttpClient();

    private String executeGet(String path) throws IOException {
        Request.Builder request = new Request.Builder()
                .url(propertyManager.getBaseUri() + path)
                .header("Authorization", "Bearer " + accessToken)
                .header("User-Agent",
                String.format("Discord-OAuth2-Java, version %s", propertyManager.getVersion()));

        try (Response response = client.newCall(request.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + "; " + response.body().string());
            }
            return response.body().string();
        }
    }

    public DiscordUser fetchUser() throws IOException {
        try {
            return gson.fromJson(executeGet("/users/@me"), DiscordUser.class);
        } catch (IOException e) {
            log.error("Error while fetching user", e);
            throw e;
        }
    }
}
