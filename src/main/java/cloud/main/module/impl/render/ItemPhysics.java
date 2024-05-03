package cloud.main.module.impl.render;

import cloud.main.module.Category;
import cloud.main.module.Module;
import org.lwjgl.input.Keyboard;

public class ItemPhysics extends Module {
    public static boolean rendering = false;
    public ItemPhysics() {
        super("ItemPhysics", "Gives items physics", Keyboard.KEY_V, Category.RENDER);
    }

    @Override
    public void onEnable() {
        rendering = true;
    }

    @Override
    public void onDisable() {
        rendering = false;
    }
}
