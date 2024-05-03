package cloud.main.module.impl.combat;

import cloud.main.events.Event;
import cloud.main.events.impl.EventMotion;
import cloud.main.events.impl.EventStrafe;
import cloud.main.module.Category;
import cloud.main.module.Manager;
import cloud.main.module.Module;
import cloud.main.module.impl.world.Scaffold;
import cloud.main.utils.AntiBotUtil;
import cloud.main.utils.TimerUtil;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Killaura extends Module {
    private BooleanSetting player = new BooleanSetting("Player", true);
    private BooleanSetting animals = new BooleanSetting("Animals", true);
    public BooleanSetting movefix = new BooleanSetting("MoveFix", true);
    public NumberSetting reach = new NumberSetting("Reach", 1F, 6F, 3F, 0.01);
    public NumberSetting minClickDelay = new NumberSetting("MinClickDelay", 1, 20, 5, 1);
    public NumberSetting maxClickDelay = new NumberSetting("MaxClickDelay", 1, 20, 10, 1);

    public TimerUtil timer = new TimerUtil();
    private Random random = new Random();
    public Killaura() {
        super("Killaura", "Automatically hits entities around you", Keyboard.KEY_R, Category.COMBAT);
        addSettings(player, animals, movefix, reach, minClickDelay, maxClickDelay);
    }
    @Override
    public void onEvent(Event e) { // mainly made by https://www.youtube.com/watch?v=HLc9E9t04y0&list=PLhgpmtS-kfPfoXwj-LlgavSXr3AoTnZ34&index=5 / Intents
        // most of these things are made by me except the killaura main
        if (Objects.requireNonNull(Manager.getModuleByClass(Scaffold.class)).isToggled()) return;
        if (e instanceof EventMotion || e instanceof EventStrafe) {

            /*List<EntityLivingBase> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).map(EntityLivingBase.class::cast).collect(Collectors.toList());
            targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < reach.getValueFloat() && entity.getHealth() > 0 && entity != mc.thePlayer && !entity.isDead).collect(Collectors.toList()); //  && entity instanceof EntityPlayer
            targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));*/
            List<EntityLivingBase> targets = AntiBotUtil.getEntitiesNearby(reach.getValueFloat());
            if (!targets.isEmpty()) {
                EntityLivingBase target = targets.get(0);
                if (Manager.getModuleByClass(AntiBot.class).isToggled()) {
                    if (AntiBotUtil.isBot(target)) return;
                }
                if (!player.isEnabled()) {
                    if (target instanceof EntityPlayer) {
                        return;
                    }
                } else if (!animals.isEnabled()) {
                    if (!(target instanceof EntityAnimal)) {
                        return;
                    }
                }
                float yaw = getRotationsNeeded(target)[0]; mc.thePlayer.rotationYawHead = yaw;
                float pitch = getRotationsNeeded(target)[1]; mc.thePlayer.rotationPitchHead = pitch;
                //float yaw = event.getYaw() + this.getYawChange(target) / speed.getValueFloat();
                //float pitch = event.getPitch() + (this.getPitchChange(target, e.g) / speed.getValueFloat());

                float renderYawOffset = yaw + ((float)Math.random() * 2);
                pitch += ((float) Math.random() * 3);
                yaw += ((float) Math.random() * 5.5f);

                if (movefix.isEnabled()) {
                    if (e instanceof EventStrafe) {
                        ((EventStrafe) e).setYaw(yaw);
                        System.out.println("setYaw: " + yaw);
                    }
                }

                if (e instanceof EventMotion) {
                    ((EventMotion) e).setYaw(yaw);
                    ((EventMotion) e).setPitch(pitch);
                }

                mc.thePlayer.renderYawOffset = renderYawOffset;
                // check if min is bigger then max
                if (minClickDelay.getValueInt() > maxClickDelay.getValueInt()) {
                    int temp = minClickDelay.getValueInt();
                    minClickDelay.setValue(maxClickDelay.getValueInt());
                    maxClickDelay.setValue(temp);
                }
                if (TimerUtil.HTE(1000 / (random.nextInt(maxClickDelay.getValueInt() - minClickDelay.getValueInt() + 1) + minClickDelay.getValueInt()), true)) {
                    mc.thePlayer.swingItem();
                    mc.playerController.attackEntity(mc.thePlayer, target);
                }
            }
        }
    }
    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - mc.thePlayer.posX;
        final double diffZ = entity.posZ - mc.thePlayer.posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
    }
}
