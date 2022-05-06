package craftschemers.railfire.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import static craftschemers.railfire.RailFirePlugin.railFirePlugin;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if (itemStack != null && itemStack.getType() == Material.WOODEN_HOE) {

            int maxDistance = 50;

            Location playerLocation = player.getEyeLocation();
            World world = playerLocation.getWorld();
            Location targetLocation;
            Block targetBlock = player.getTargetBlockExact(maxDistance); // TODO: use block face to determine exact spot
            Entity targetEntity = player.getTargetEntity(maxDistance);
            if (targetBlock != null) {
                targetLocation = targetBlock.getLocation();
            } else if (targetEntity != null) {
                targetLocation = targetEntity.getLocation();
            } else {
                float pitch = (float) Math.toRadians(playerLocation.getPitch());
                float yaw = playerLocation.getYaw();

                float dy = (float) (Math.sin(pitch) * maxDistance);
                float dHorizontal = (float) (Math.cos(pitch) * maxDistance);
                float dx = (float) (Math.sin(yaw) * dHorizontal);
                float dz = (float) (Math.cos(yaw) * dHorizontal);

                targetLocation = playerLocation.add(dx, dy, dz);
            }

            Firework firework = (Firework) world.spawnEntity(targetLocation, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder()
                    .with(FireworkEffect.Type.BALL)
                    .withColor(Color.TEAL)
                    .withFade(Color.BLUE)
                    .trail(true)
                    .build();
            fireworkMeta.addEffect(effect);
            fireworkMeta.setPower(1);
            firework.setFireworkMeta(fireworkMeta);
            firework.detonate();

            int explosionsPerBlock = 1;
            float distance = (float) targetLocation.distance(playerLocation);
            int explosions = (int) (distance * explosionsPerBlock);
            for (int i = 0; i < explosions; i++) {

                int finalI = i;
                railFirePlugin.getServer().getScheduler().scheduleSyncDelayedTask(railFirePlugin, () -> {

                    float delta = ((float) finalI / explosions) * distance;

                    float pitch = (float) Math.toRadians(playerLocation.getPitch());
                    float yaw = playerLocation.getYaw();

                    float dy = (float) (Math.sin(pitch) * delta);
                    float dHorizontal = (float) (Math.cos(pitch) * delta);
                    float dx = (float) (Math.sin(yaw) * dHorizontal);
                    float dz = (float) (Math.cos(yaw) * dHorizontal);

                    Location trailLocation = playerLocation.add(dx, dy, dz);

                    Firework trailFirework = (Firework) world.spawnEntity(trailLocation, EntityType.FIREWORK);
                    FireworkMeta trailFireworkMeta = firework.getFireworkMeta();
                    FireworkEffect trailEffect = FireworkEffect.builder()
                            .with(FireworkEffect.Type.BALL)
                            .withColor(Color.TEAL)
                            .trail(false)
                            .build();
                    trailFireworkMeta.addEffect(trailEffect);
                    trailFireworkMeta.setPower(1);
                    trailFirework.setFireworkMeta(trailFireworkMeta);
                    trailFirework.detonate();

                }, i);

            }

        }

    }

}
