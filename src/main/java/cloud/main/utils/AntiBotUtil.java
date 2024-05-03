package cloud.main.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AntiBotUtil {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public static List<EntityLivingBase> getEntitiesNearby(double range) {
        List<EntityLivingBase> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).map(EntityLivingBase.class::cast).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range && entity.getHealth() > 0 && entity != mc.thePlayer && !entity.isDead).collect(Collectors.toList()); //  && entity instanceof EntityPlayer
        targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));
        return targets;
    }
    public static boolean isBot(Entity e) {
        return e.isInvisible() || (e instanceof EntityArmorStand);
    }

}
