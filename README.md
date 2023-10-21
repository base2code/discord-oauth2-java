# discord-oauth2-java

A small oauth2 java wrapper for the discord oauth2 api to retrieve basic user information.

## Usage

### Maven
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.base2code</groupId>
    <artifactId>discord-oauth2-java</artifactId>
    <version>1.0</version>
</dependency>
```

## Using the library (example)

### Get Authorization URL
```java
    DiscordOAuth discordOAuth = new DiscordOAuth(
        "CLIENT_ID",
        "CLIENT_SECRET",
        "REDIRECT_URI",
        new String[]{
            "identify",
            "email"
        });
    String authorizationURL = discordOAuth.getAuthorizationURL();
```

### Get the access token
```java
    String code = "code"; // This is the code which is returned by the discord oauth2 api
    
    DiscordOAuth discordOAuth = new DiscordOAuth(
        "CLIENT_ID",
        "CLIENT_SECRET",
        "REDIRECT_URI",
        new String[]{
            "identify",
            "email"
        });
        
    DiscordOauthAuthorizeResponse tokens = discordOAuth.requestTokens(code);
    
    String accessToken = tokens.getAccessToken();
```
    

### Get User information
```java
    DiscordAPI discordAPI = new DiscordAPI(tokens.getAccessToken());
    DiscordUser discordUser = discordAPI.fetchUser();
    String email = discordUser.getEmail();
```
