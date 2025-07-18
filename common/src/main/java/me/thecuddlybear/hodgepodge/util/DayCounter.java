package me.thecuddlybear.hodgepodge.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class DayCounter {

    private static int animationTick = 0;
    private static int currentDay = 0;
    private static boolean isAnimatingNormal = false;
    private static boolean isAnimatingSpecial = false;

    public static void startAnimation(MinecraftServer server, int dayCounter, boolean special) {
        currentDay = dayCounter;
        animationTick = 0;


        // Start the animation sequence
        if(!special){isAnimatingNormal = true; updateAnimation(server); }
        if(special) {isAnimatingSpecial = true; updateSpecialAnimation(server);}
    }

    public static void updateSpecialAnimation(MinecraftServer server) {
        if (!isAnimatingSpecial) return;
        if (isAnimatingNormal) { isAnimatingNormal = false;}

        Component message = null;

        // Play sounds at specified times
        switch (animationTick) {
            case 40:
                message = Component.literal("—").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 45:
                message = Component.literal("——").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 70:
                message = Component.literal("— —").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 75:
                message = Component.literal("— D —").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 80:
                message = Component.literal("— DA —").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 85:
                message = Component.literal("— DAY —").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 100:
                message = Component.literal("— DAY " + currentDay + " —").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                // Play multiple sounds at tick 100
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 1.5f);
                playSound(server, SoundEvents.COPPER_BREAK, 1.0f, 0.6f);
                playSound(server, SoundEvents.VAULT_PLACE, 1.0f, 0.6f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_STEP, 1.0f, .63f);
                break;
            case 112:
                playSound(server, SoundEvents.AMETHYST_BLOCK_STEP, 1.0f, .63f);
                break;
            case 124:
                playSound(server, SoundEvents.AMETHYST_BLOCK_STEP, 1.0f, .63f);
                break;
            case 140:
                playSound(server, SoundEvents.AMETHYST_BLOCK_STEP, 1.0f, .63f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, .2f, .8f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, .5f, .63f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, 1.0f, .5f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, 1.0f, .5f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, 1.0f, .7f);
                break;
            case 150:
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, 1.0f, 1.5f);
                playSound(server, SoundEvents.AMETHYST_BLOCK_CHIME, 1.0f, .6f);
                break;
            default:
                if (animationTick >= 100 && animationTick <= 140) {
                    message = Component.literal("— DAY " + currentDay + " —").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                } else if (animationTick > 140) {
                    // Animation finished
                    isAnimatingSpecial = false;
                    return;
                }
                break;
        }

        // Send the message to all players if we have one
        if (message != null) {
            ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(message);
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                player.connection.send(packet);
            }
        }

        animationTick++;
    }

    public static void updateAnimation(MinecraftServer server) {
        if (!isAnimatingNormal) return;

        Component message = null;

        // Play sounds at specified times
        switch (animationTick) {
            case 40:
                message = Component.literal("—");
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 45:
                message = Component.literal("——");
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 70:
                message = Component.literal("— —");
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 75:
                message = Component.literal("— D —");
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 80:
                message = Component.literal("— DA —");
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 85:
                message = Component.literal("— DAY —");
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                break;
            case 100:
                message = Component.literal("— DAY " + currentDay + " —");
                // Play multiple sounds at tick 100
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 2.0f);
                playSound(server, SoundEvents.UI_BUTTON_CLICK.value(), 0.4f, 1.5f);
                playSound(server, SoundEvents.COPPER_BREAK, 1.0f, 0.6f);
                break;
            default:
                if (animationTick >= 100 && animationTick <= 140) {
                    message = Component.literal("— DAY " + currentDay + " —");
                } else if (animationTick > 140) {
                    // Animation finished
                    isAnimatingNormal = false;
                    return;
                }
                break;
        }

        // Send the message to all players if we have one
        if (message != null) {
            ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(message);
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                player.connection.send(packet);
            }
        }

        animationTick++;
    }

    private static void playSound(MinecraftServer server, net.minecraft.sounds.SoundEvent soundEvent, float volume, float pitch) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.playNotifySound(soundEvent, SoundSource.MASTER, volume, pitch);
        }
    }
}