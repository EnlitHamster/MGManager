package io.github.enlithamster.mgmanager;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.util.Objects;

public class Utils {

    @SuppressWarnings("ConstantConditions")
    public static boolean hasAvaliableSlot(Player player, int minEmptySlots){
        Inventory inv = player.getInventory();
        int check=0;
        for (ItemStack item: inv.getContents())
            if (Objects.isNull(item))
                ++check;

        return check >= minEmptySlots;
    }

    public static void lineBetween(World world, int x1, int y1, int z1, int x2, int y2, int z2, Material material) {
        int lenX = x2 - x1;
        int lenY = y2 - y1;
        int lenZ = z2 - z1;

        double distance = Math.sqrt(NumberConversions.square(lenX) + NumberConversions.square(lenY) + NumberConversions.square(lenZ));
        double stepX = lenX / distance;
        double stepY = lenY / distance;
        double stepZ = lenZ / distance;

        for (double dx = 0, dy = 0, dz = 0; checkDelta(dx, lenX) && checkDelta(dy, lenY) && checkDelta(dz, lenZ); dx += stepX, dy += stepY, dz += stepZ)
            world.getBlockAt((int) Math.round(x1 + dx), (int) Math.round(y1 + dy), (int) Math.round(z1 + dz)).setType(material);
    }

    private static boolean checkDelta(double delta, double len) {
        if (len < 0)
            return delta >= len;
        else
            return delta <= len;
    }

}
