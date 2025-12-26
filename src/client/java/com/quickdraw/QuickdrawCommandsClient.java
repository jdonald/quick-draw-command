package com.quickdraw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Client-side initialization for Quickdraw Commands.
 * Registers the server join event to execute configured commands.
 */
public class QuickdrawCommandsClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuickdrawCommands.MOD_ID);
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Quickdraw Commands client initialized");

        // Load config
        QuickdrawConfig.load();

        // Register server join event
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!QuickdrawConfig.isEnabled()) {
                LOGGER.info("Quickdraw Commands is disabled, skipping command execution");
                return;
            }

            LOGGER.info("Joined server, executing quickdraw commands...");
            executeCommands(client);
        });
    }

    /**
     * Execute all configured commands with the specified cooldown between them.
     */
    private void executeCommands(MinecraftClient client) {
        String[] commands = QuickdrawConfig.getCommands();
        int cooldownMs = QuickdrawConfig.getCooldownMs();

        int delay = 0;
        int commandIndex = 0;

        for (String command : commands) {
            if (command == null || command.trim().isEmpty()) {
                continue;
            }

            final String cmdToExecute = command.trim();
            final int index = commandIndex++;

            scheduler.schedule(() -> {
                client.execute(() -> {
                    if (client.player != null && client.getNetworkHandler() != null) {
                        LOGGER.info("Executing command {}: {}", index + 1, cmdToExecute);
                        sendCommand(client, cmdToExecute);
                    }
                });
            }, delay, TimeUnit.MILLISECONDS);

            delay += cooldownMs;
        }
    }

    /**
     * Send a command or chat message to the server.
     * If the message starts with '/', it's sent as a command.
     * Otherwise, it's sent as a regular chat message.
     */
    private void sendCommand(MinecraftClient client, String message) {
        if (client.player == null || client.getNetworkHandler() == null) {
            return;
        }

        if (message.startsWith("/")) {
            // It's a command - send without the leading slash
            String command = message.substring(1);
            client.getNetworkHandler().sendChatCommand(command);
        } else {
            // It's a regular chat message
            client.getNetworkHandler().sendChatMessage(message);
        }
    }
}
