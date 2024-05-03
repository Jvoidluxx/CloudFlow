package cloud.main.utils.clickgui.components;

import cloud.main.utils.RenderUtil;
import cloud.main.utils.clickgui.Frame;
import cloud.main.utils.clickgui.ModuleButton;
import cloud.main.utils.settings.NumberSetting;
import cloud.main.utils.settings.Setting;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component{
    private NumberSetting numSet = (NumberSetting) setting;
    private boolean sliding = false;
    public Slider(Setting setting, int offset, ModuleButton mb) {
        super(setting, offset, mb);
        this.numSet = (NumberSetting) setting;
    }

    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRectBetter(mb.frame.x, mb.frame.y + offset + mb.offset, mb.frame.x + mb.frame.width, mb.frame.y + mb.frame.height + offset + mb.offset, new Color(0, 0, 0, 150).getRGB());
        double diff = Math.min(mb.frame.width, Math.max(0, mouseX - mb.frame.x));
        int renderWidth = (int) (mb.frame.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));
        //RenderUtil.drawRectBetter(mb.frame.x, mb.frame.y + offset + mb.offset, mb.frame.x + renderWidth, mb.frame.y + mb.frame.height + offset + mb.offset, Color.RED.darker().getRGB());
        RenderUtil.drawHorizontalLine(mb.frame.x, mb.frame.y + offset + mb.offset + 10, mb.frame.x + renderWidth, 1.3f, -1);
        Frame.fr.drawString(numSet.getName() + " : " + roundToPlace(numSet.getValue(), 2), mb.frame.x + 2, mb.frame.y + offset + 2 + mb.offset, -1);
        if (sliding) {
            double newValue = (diff / mb.frame.width) * (numSet.getMax() - numSet.getMin()) + numSet.getMin();
            numSet.setValue(roundToPlace(newValue, 2));
        }

        super.onRender(mouseX, mouseY, partialTicks);
    }


    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            sliding = true;
        }
        super.onMouseClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        sliding = false;
        super.onMouseRelease(mouseX, mouseY, mouseButton);
    }
    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
