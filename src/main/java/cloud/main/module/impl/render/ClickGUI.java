package cloud.main.module.impl.render;

import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.clickgui.ClickGui;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "A GUI that allows you to see the modules and customize them", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(ClickGui.INSTANCE);
        this.toggle();
    }
}
