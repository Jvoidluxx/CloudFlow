package cloud.main.utils.clickgui.components;

import cloud.main.module.Module;
import cloud.main.utils.clickgui.ModuleButton;
import cloud.main.utils.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class Component {
    public Setting setting;
    public int offset;
    public ModuleButton mb;
    public Component(Setting setting, int offset, ModuleButton mb) {
        this.setting = setting;
        this.offset = offset;
        this.mb = mb;
    }

    public void onRender(int mouseX, int mouseY, float partialTicks) {}

    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {}

    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {}

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > mb.frame.x && mouseX < mb.frame.x+mb.frame.width && mouseY > mb.frame.y + offset+mb.offset && mouseY < mb.frame.y+mb.frame.height + offset+mb.offset;
    }
}
