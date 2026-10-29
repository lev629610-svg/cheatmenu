package com.example.cheat;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class CheatMod implements ClientModInitializer {
    public static boolean fly = false;
    public static boolean nightVision = false;
    public static int speedIndex = 0;
    public static final float[] SPEEDS = {1f, 2f, 3f, 5f};

    private static boolean wasFly = false;
    private static boolean wasNight = false;
    private static int wasSpeed = 0;
    private static KeyBinding openKey;

    @Override
    public void onInitializeClient() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Cheat Menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "Cheat Menu"));

        ScreenEvents.AFTER_INIT.register((client, screen, w, h) -> {
            if (screen instanceof GameMenuScreen) {
                Screens.getButtons(screen).add(new ButtonWidget(5, 5, 70, 20,
                        new LiteralText("Cheats"), b -> client.openScreen(new CheatScreen())));
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(CheatMod::tick);
    }

    private static void tick(MinecraftClient client) {
        while (openKey.wasPressed()) {
            client.openScreen(new CheatScreen());
        }
        PlayerEntity p = client.player;
        if (p == null) return;

        if (fly) {
            p.abilities.allowFlying = true;
        } else if (wasFly && !p.isCreative() && !p.isSpectator()) {
            p.abilities.allowFlying = false;
            p.abilities.flying = false;
        }
        wasFly = fly;

        if (nightVision) {
            p.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false));
        } else if (wasNight) {
            p.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        wasNight = nightVision;

        if (speedIndex != wasSpeed || speedIndex != 0) {
            float m = SPEEDS[speedIndex];
            p.abilities.setFlySpeed(0.05f * m);
            p.abilities.setWalkSpeed(0.1f * m);
            wasSpeed = speedIndex;
        }
    }
}
