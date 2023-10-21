package de.base2code.discord.oauth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.base2code.discord.oauth.model.DiscordOauthAuthorizeResponse;
import de.base2code.discord.oauth.properties.PropertyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class DiscordOAuth {
    private final PropertyManager propertyManager = new PropertyManager();
    private static final Gson gson = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();
    private final String clientID;
    private final String clientSecret;
    private final String redirectUri;
    private final String[] scope;
    private final OkHttpClient client = new OkHttpClient();

    public String getAuthorizationURL() throws IOException {
        return getAuthorizationURL(null);
    }

    public String getAuthorizationURL(String state) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(propertyManager.getBaseUri() + "/oauth2/authorize").newBuilder();
        urlBuilder.addQueryParameter("response_type", "code");
        urlBuilder.addQueryParameter("client_id", clientID);
        urlBuilder.addQueryParameter("redirect_uri", redirectUri);
        if (state != null && !state.isEmpty()) {
            urlBuilder.addQueryParameter("state", state);
        }
        urlBuilder.addQueryParameter("scope", String.join(" ", scope));
        return urlBuilder.build().toString();
    }

    public DiscordOauthAuthorizeResponse requestTokens(String code) throws IOException {
        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .add("redirect_uri", redirectUri)
                .build();
        Request request = new Request.Builder()
                .url(propertyManager.getBaseUri() + "/v10/oauth2/token")
                .header("Authorization", Credentials.basic(clientID, clientSecret))
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + "; " + response.body().string());
            }
            return gson.fromJson(response.body().string(), DiscordOauthAuthorizeResponse.class);
        }
    }
}
