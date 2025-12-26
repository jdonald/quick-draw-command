package com.quickdraw;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

/**
 * Configuration screen for Quickdraw Commands.
 * Provides UI for configuring the mod settings via ModMenu.
 */
public class QuickdrawConfigScreen extends Screen {
    private final Screen parent;

    // Widgets
    private ButtonWidget enabledButton;
    private TextFieldWidget[] commandFields = new TextFieldWidget[5];
    private CooldownSlider cooldownSlider;

    // Layout constants
    private static final int FIELD_WIDTH = 250;
    private static final int FIELD_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 120;
    private static final int SPACING = 24;

    public QuickdrawConfigScreen(Screen parent) {
        super(Text.literal("Quickdraw Commands Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 40;

        // Enable/Disable toggle button
        enabledButton = ButtonWidget.builder(
                        getEnabledButtonText(),
                        button -> {
                            QuickdrawConfig.setEnabled(!QuickdrawConfig.isEnabled());
                            button.setMessage(getEnabledButtonText());
                        })
                .dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, FIELD_HEIGHT)
                .build();
        this.addDrawableChild(enabledButton);
        y += SPACING + 10;

        // Command input fields
        String[] commands = {
                QuickdrawConfig.getCommand1(),
                QuickdrawConfig.getCommand2(),
                QuickdrawConfig.getCommand3(),
                QuickdrawConfig.getCommand4(),
                QuickdrawConfig.getCommand5()
        };

        for (int i = 0; i < 5; i++) {
            TextFieldWidget field = new TextFieldWidget(
                    this.textRenderer,
                    centerX - FIELD_WIDTH / 2,
                    y,
                    FIELD_WIDTH,
                    FIELD_HEIGHT,
                    Text.literal("Command " + (i + 1))
            );
            field.setMaxLength(256);
            field.setText(commands[i] != null ? commands[i] : "");
            commandFields[i] = field;
            this.addDrawableChild(field);
            y += SPACING;
        }

        y += 10;

        // Cooldown slider
        cooldownSlider = new CooldownSlider(
                centerX - FIELD_WIDTH / 2,
                y,
                FIELD_WIDTH,
                FIELD_HEIGHT,
                QuickdrawConfig.getCooldownSeconds()
        );
        this.addDrawableChild(cooldownSlider);
        y += SPACING + 10;

        // Done button
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> this.close())
                .dimensions(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, FIELD_HEIGHT)
                .build());
    }

    private Text getEnabledButtonText() {
        return Text.literal("Quickdraw: " + (QuickdrawConfig.isEnabled() ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Draw title
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                15,
                0xFFFFFF
        );

        // Draw labels for command fields
        int labelX = this.width / 2 - FIELD_WIDTH / 2;
        int y = 40 + SPACING + 10;

        for (int i = 0; i < 5; i++) {
            context.drawTextWithShadow(
                    this.textRenderer,
                    Text.literal("Command " + (i + 1) + ":"),
                    labelX - 80,
                    y + 5,
                    0xAAAAAA
            );
            y += SPACING;
        }

        // Draw cooldown label
        y += 10;
        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Cooldown:"),
                labelX - 80,
                y + 5,
                0xAAAAAA
        );
    }

    @Override
    public void close() {
        // Save all settings
        QuickdrawConfig.setCommand1(commandFields[0].getText());
        QuickdrawConfig.setCommand2(commandFields[1].getText());
        QuickdrawConfig.setCommand3(commandFields[2].getText());
        QuickdrawConfig.setCommand4(commandFields[3].getText());
        QuickdrawConfig.setCommand5(commandFields[4].getText());
        QuickdrawConfig.setCooldownSeconds(cooldownSlider.getCooldownValue());
        QuickdrawConfig.save();

        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }

    /**
     * Custom slider widget for cooldown configuration.
     * Ranges from 0 to 10 seconds.
     */
    private static class CooldownSlider extends SliderWidget {
        private static final int MAX_COOLDOWN = 10;

        public CooldownSlider(int x, int y, int width, int height, int currentValue) {
            super(x, y, width, height, Text.literal("Cooldown: " + currentValue + "s"), currentValue / (double) MAX_COOLDOWN);
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Text.literal("Cooldown: " + getCooldownValue() + "s"));
        }

        @Override
        protected void applyValue() {
            // Value will be applied when screen closes
        }

        public int getCooldownValue() {
            return (int) Math.round(this.value * MAX_COOLDOWN);
        }
    }
}
