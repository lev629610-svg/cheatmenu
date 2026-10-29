package com.example.cheat;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class CheatScreen extends Screen {
    public CheatScreen() {
        super(new LiteralText("Cheat Menu"));
    }

    private static String onOff(boolean v) {
        return v ? "ВКЛ" : "ВЫКЛ";
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 50;

        this.addButton(new ButtonWidget(x, y, 200, 20,
                new LiteralText("Полёт: " + onOff(CheatMod.fly)), b -> {
            CheatMod.fly = !CheatMod.fly;
            b.setMessage(new LiteralText("Полёт: " + onOff(CheatMod.fly)));
        }));

        this.addButton(new ButtonWidget(x, y + 25, 200, 20,
                new LiteralText("Ночное зрение: " + onOff(CheatMod.nightVision)), b -> {
            CheatMod.nightVision = !CheatMod.nightVision;
            b.setMessage(new LiteralText("Ночное зрение: " + onOff(CheatMod.nightVision)));
        }));

        this.addButton(new ButtonWidget(x, y + 50, 200, 20,
                new LiteralText("Скорость: x" + (int) CheatMod.SPEEDS[CheatMod.speedIndex]), b -> {
            CheatMod.speedIndex = (CheatMod.speedIndex + 1) % CheatMod.SPEEDS.length;
            b.setMessage(new LiteralText("Скорость: x" + (int) CheatMod.SPEEDS[CheatMod.speedIndex]));
        }));

        this.addButton(new ButtonWidget(x, y + 85, 200, 20,
                new LiteralText("Закрыть"), b -> this.onClose()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.height / 2 - 80, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
