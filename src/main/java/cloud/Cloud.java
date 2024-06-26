package cloud;

import cloud.main.ConfigManager;
import cloud.main.commandbase.CommandManager;
import cloud.main.extra.DiscordRP;
import cloud.main.module.Manager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

public class Cloud {
    protected static Minecraft mc = Minecraft.getMinecraft();
    public static CommandManager commandManager = new CommandManager();
    public static ConfigManager configManager = new ConfigManager();
    public static String name = "CloudFlow", version = "1.0.4";
    public static DiscordRP discordRP = new DiscordRP();

    public static void init() {
        discordRP.start();
        Manager manager = new Manager();
        commandManager = new CommandManager();
        Display.setTitle(name + " " + version);
        configManager.init();
    }
}
