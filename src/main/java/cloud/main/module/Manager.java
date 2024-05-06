package cloud.main.module;

import cloud.Cloud;
import cloud.main.events.Event;
import cloud.main.events.impl.EventChat;
import cloud.main.events.impl.EventUpdate;
import cloud.main.module.impl.combat.AntiBot;
import cloud.main.module.impl.combat.Killaura;
import cloud.main.module.impl.misc.AutoRespawn;
import cloud.main.module.impl.movement.Flight;
import cloud.main.module.impl.movement.Speed;
import cloud.main.module.impl.movement.Sprint;
import cloud.main.module.impl.render.*;
import cloud.main.module.impl.world.Scaffold;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manager {
    public static ArrayList<Module> modules;

    public Manager() {
        modules = new ArrayList<>();
        modules.add(new Sprint());
        modules.add(new Speed());
        modules.add(new HUD());
        modules.add(new ItemPhysics());
        modules.add(new ClickGUI());
        modules.add(new Killaura());
        modules.add(new Flight());
        modules.add(new NoRotate());
        //modules.add(new BowAimBot()); very buggy
        modules.add(new FullBright());
        modules.add(new Scaffold());
        modules.add(new AutoRespawn());
        modules.add(new AntiBot()); // killaura might not work if this is enabled
        modules.add(new ChestESP());
        modules.add(new ESP());
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }
    public static void onKey(int k) {
        for (Module m : modules) { // for every module
            if (k == m.getKey()) { // if the module's key is pressed
                m.toggle(); // toggle the module
            }
        }
        chatKey(k);
    }
    public static void chatKey(int key) {
        if (key == Keyboard.KEY_PERIOD) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiChat("."));
        }
    }
    public static void onEvent(Event e) {
        for (Module m : modules) { // for every module
            if (m.isToggled()) { // if the module is toggled
                m.onEvent(e); // update the event with a specific event
            }
        }
        if (e instanceof EventChat) {
            Cloud.commandManager.handleChat((EventChat) e); // Pass the event chat to the commandbase
        }
    }
    public static void addClientMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public static List<Module> getModuleInCategory(Category category) { // gets all the modules in a specific category
        return modules.stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
    }
    public static Module getModule(String name) { // gets a module by name
        return modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Module getModuleByClass(Class<? extends Module> clazz) { // gets a module by class
        return modules.stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
    }
}
