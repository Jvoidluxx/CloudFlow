package cloud.main.utils.clickgui.components;

import cloud.main.utils.RenderUtil;
import cloud.main.utils.clickgui.Frame;
import cloud.main.utils.clickgui.ModuleButton;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.Setting;

import java.awt.*;

public class ModeBox extends Component{
    private ModeSetting modeSet = (ModeSetting) setting;
    public ModeBox(Setting setting, int offset, ModuleButton mb) {
        super(setting, offset, mb);
        this.modeSet = (ModeSetting) setting;
    }

    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        super.onRender(mouseX, mouseY, partialTicks);
        RenderUtil.drawRectBetter(mb.frame.x, mb.frame.y+offset+mb.offset, mb.frame.x+mb.frame.width, mb.frame.y+mb.frame.height+offset+mb.offset, Color.GRAY.getRGB());
        Frame.fr.drawString(modeSet.getName() + " : " + modeSet.getMode(), mb.frame.x+2, mb.frame.y+offset+2+mb.offset, -1);
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            modeSet.cycle();
        }
        super.onMouseClick(mouseX, mouseY, mouseButton);
    }
}
