package com.hardcourse.hardcoursecheckpoints.Utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;

public class WarningSender {
    static WebhookClient cleint = WebhookClient.withUrl("webhook-url-here");

    public static void sendWarning(Integer oldLevel, Integer newLevel, String playerName){
        cleint.send(playerName + " might be cheating! They have skipped from level " + oldLevel + " to " + newLevel + ".");
    }
}
