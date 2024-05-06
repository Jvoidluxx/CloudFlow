package cloud.main.module.impl.movement;

import cloud.main.events.Event;
import cloud.main.events.impl.EventMotion;
import cloud.main.events.impl.EventUpdate;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.MoveUtil;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    // Made by me
    public ModeSetting modes = new ModeSetting("Mode", "Vanilla", "Vanilla", "Vulcan");
    public NumberSetting vulcanSpeed = new NumberSetting("Vulcan Speed", 0.1f, 5.0f, 0.32f, 0.001f);
    public NumberSetting vanillaSpeed = new NumberSetting("Vanilla Speed", 0.1f, 5.0f, 0.32f, 0.001f);
    public NumberSetting vulcanTimer = new NumberSetting("Vulcan Timer", 0.1f, 10.0f, 1.0f, 0.1f);
    public BooleanSetting hop = new BooleanSetting("Hop", true);
    public Speed() {
        super("Speed", "Makes you move faster", Keyboard.KEY_Y, Category.MOVEMENT);
        addSettings(modes, vulcanSpeed, vulcanTimer, hop);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            mc.gameSettings.keyBindJump.pressed = hop.isEnabled();
            switch (modes.getMode()) {
                case "Vanilla": {
                    MoveUtil.setSpeed(vanillaSpeed.getValueFloat());
                    break;
                }
                case "Vulcan": {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        MoveUtil.setSpeed(vulcanSpeed.getValueFloat());
                        mc.timer.timerSpeed = vulcanTimer.getValueFloat();
                    }
                    break;
                }
            }
        }
    }
}