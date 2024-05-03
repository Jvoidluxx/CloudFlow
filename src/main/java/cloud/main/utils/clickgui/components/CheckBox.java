package cloud.main.utils.clickgui.components;

import cloud.main.utils.RenderUtil;
import cloud.main.utils.clickgui.Frame;
import cloud.main.utils.clickgui.ModuleButton;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.Setting;

import java.awt.*;

public class CheckBox extends Component{
    public BooleanSetting boolSet = (BooleanSetting) setting;
    public CheckBox(Setting setting, int offset, ModuleButton mb) {
        super(setting, offset, mb);
        this.boolSet = (BooleanSetting) setting;
    }

    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRectBetter(mb.frame.x, mb.frame.y+offset+mb.offset, mb.frame.x+mb.frame.width, mb.frame.y+mb.frame.height+offset+mb.offset, Color.GRAY.getRGB());
        Frame.fr.drawString(boolSet.getName() + " : " + boolSet.isEnabled(), mb.frame.x+2, mb.frame.y+offset+2+mb.offset, -1);
        super.onRender(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            boolSet.setEnabled(!boolSet.isEnabled());
        }
        super.onMouseClick(mouseX, mouseY, mouseButton);
    }
}
