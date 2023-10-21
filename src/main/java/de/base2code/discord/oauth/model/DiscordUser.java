package de.base2code.discord.oauth.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DiscordUser
{
    private String id;
    private String username;
    private String avatar;
    @Deprecated
    private String discriminator;
    private Boolean system;
    @SerializedName("mfa_enabled")
    private Boolean mfaEnabled;
    private String locale;
    private Boolean verified;
    private String email;
    private Long flags;
    @SerializedName("premium_type")
    private Integer premiumType;
}
