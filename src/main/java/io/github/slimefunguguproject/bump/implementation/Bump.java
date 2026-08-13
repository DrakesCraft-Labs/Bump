package io.github.slimefunguguproject.bump.implementation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import io.github.slimefunguguproject.bump.core.BumpRegistry;
import io.github.slimefunguguproject.bump.core.services.ConfigUpdateService;
import io.github.slimefunguguproject.bump.core.services.LocalizationService;
import io.github.slimefunguguproject.bump.core.services.sounds.SoundService;
import io.github.slimefunguguproject.bump.implementation.setup.AppraiseSetup;
import io.github.slimefunguguproject.bump.implementation.setup.ItemGroupsSetup;
import io.github.slimefunguguproject.bump.implementation.setup.ItemsSetup;
import io.github.slimefunguguproject.bump.implementation.setup.ListenerSetup;
import io.github.slimefunguguproject.bump.implementation.setup.ResearchSetup;
import io.github.slimefunguguproject.bump.implementation.tasks.WeaponProjectileTask;
import io.github.slimefunguguproject.bump.utils.WikiUtils;
import io.github.slimefunguguproject.bump.utils.tags.BumpTag;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;

import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon;
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig;


/**
 * Main class for {@link Bump}.
 *
 * @author ybw0014
 */
public final class Bump extends AbstractAddon {

    private static final String DEFAULT_LANG = "en-US";

    // localization
    private LocalizationService localization;

    // registry
    private BumpRegistry registry;

    // services
    private SoundService soundService;

    public Bump() {
        super("DrakesCraft-Labs", "Bump", "main", "options.auto-update");
    }

    @Nonnull
    private static Bump inst() {
        return getInstance();
    }

    @Nonnull
    public static LocalizationService getLocalization() {
        return inst().localization;
    }

    @Nonnull
    public static BumpRegistry getRegistry() {
        return inst().registry;
    }

    @Nonnull
    public static SoundService getSoundService() {
        return inst().soundService;
    }

    @Override
    public void enable() {
        sendConsole("&6&l  _____");
        sendConsole("&6&l /\\   _`\\             ");
        sendConsole("&6&l \\ \\ \\L\\ \\  __  __    ___ ___   _____   ");
        sendConsole("&6&l  \\ \\  _ <'/\\ \\/\\ \\ /' __` __`\\/\\ '__`\\ ");
        sendConsole("&6&l   \\ \\ \\L\\ \\ \\ \\_\\ \\/\\ \\/\\ \\/\\ \\ \\ \\L\\ \\");
        sendConsole("&6&l    \\ \\____/\\ \\____/\\ \\_\\ \\_\\ \\_\\ \\ ,__/");
        sendConsole("&6&l     \\/___/  \\/___/  \\/_/\\/_/\\/_/\\ \\ \\/ ");
        sendConsole("&6&l                                  \\ \\_\\ ");
        sendConsole("&6&l                                   \\/_/ ");
        sendConsole("&a&l  Bump 2 for Slimefun4 RC-30+");
        sendConsole("&a&l  Powered By bxx2004, SlimefunGuguProject");
        sendConsole("&a&l  GitHub: https://github.com/DrakesCraft-Labs/Bump");
        sendConsole("&a&l  Guide: https://web.drakescraft.cl/guia-slimefun.html#bump");

        // config
        AddonConfig config = getAddonConfig();
        new ConfigUpdateService(config);

        // registry
        registry = new BumpRegistry(this, config);

        // localization
        log(Level.INFO, "Loading language...");
        String lang = config.getString("options.lang", DEFAULT_LANG);
        localization = new LocalizationService(this);
        localization.addLanguage(lang);
        getRegistry().setLanguage(lang);
        if (!lang.equals(DEFAULT_LANG)) {
            localization.addLanguage(DEFAULT_LANG);
        }
        log(Level.INFO, "Loaded language {0}", lang);

        // check slimefun version
        Slimefun slimefun = Slimefun.instance();
        if (slimefun != null && isSCSlimefun(slimefun.getPluginVersion())
            && lang.equalsIgnoreCase(DEFAULT_LANG) && !lang.startsWith("zh-")
        ) {
            log(Level.WARNING, "你似乎正在使用汉化版粘液科技，但未设置Bump的语言。");
            log(Level.WARNING, "Bump是一个支持多语言的粘液附属，默认语言为英文。");
            log(Level.WARNING, "你需要在 /plugins/Bump/config.yml 中，");
            log(Level.WARNING, "设置 options.lang 为 zh-CN 来将Bump的语言改为简体中文。");
        }

        // tags
        BumpTag.reloadAll();

        // sound service
        soundService = new SoundService(new AddonConfig("sounds.yml"));
        soundService.load(true);

        // appraise setup
        AppraiseSetup.setupTypes();
        AppraiseSetup.setupStars();

        // item groups setup
        ItemGroupsSetup.setup(this);

        // items setup
        ItemsSetup.setup(this);

        // researches setup
        boolean enableResearch = config.getBoolean("options.enable-research", true);
        if (enableResearch) {
            ResearchSetup.setup();
        }

        // wiki setup
        WikiUtils.setupJson();

        // listeners
        ListenerSetup.setup(this);

        // tasks
        WeaponProjectileTask.start();

    }

    @Override
    public void disable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    /**
     * El autoactualizador queda desarmado a proposito en DrakesCraft.
     *
     * Este jar esta recompilado contra el Slimefun repaquetado del servidor. El que hay en el
     * GitHub de upstream apunta a los paquetes originales, asi que si el actualizador se lo
     * trajera encima, el addon dejaria de cargar. Se despliega por SFTP, como el resto.
     *
     * Lo llamaba por reflexion, con el nombre de la clase metido en una cadena, asi que no
     * aparecia en los imports ni lo veia una busqueda del nombre de la clase.
     */
    @Override
    protected void autoUpdate() {
        // Sin cuerpo a proposito.
    }

    @Nonnull
    public String getWikiURL() {
        return "https://web.drakescraft.cl/guia-slimefun.html#bump";
    }

    private boolean isSCSlimefun(@Nonnull String sfVersion) {
        return sfVersion.endsWith("-canary") || sfVersion.endsWith("-release") || sfVersion.endsWith("-Beta") || sfVersion.endsWith("-Insider");
    }
}
