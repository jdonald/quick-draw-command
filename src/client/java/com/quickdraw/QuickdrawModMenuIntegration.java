package com.quickdraw;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * ModMenu API integration.
 * Provides a config screen factory for the mod menu.
 */
public class QuickdrawModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return QuickdrawConfigScreen::new;
    }
}
