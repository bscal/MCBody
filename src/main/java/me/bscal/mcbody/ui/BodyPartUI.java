package me.bscal.mcbody.ui;

import java.text.MessageFormat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gyurix.inventory.CustomGUI;
import gyurix.inventory.GUIConfig;
import gyurix.inventory.PagedConfig;
import gyurix.inventory.StaticItem;

public class BodyPartUI
{
    GUIConfig bodyGUI;

    VarItem headItem;
    VarItem bodyItem;
    VarItem leftLegItem;
    VarItem rightLegItem;

    public BodyPartUI()
    {
        bodyGUI = new GUIConfig();
        bodyGUI.title = "Player Health";
        bodyGUI.size = 6 * 9;
        bodyGUI.separator = new ItemStack(Material.AIR);

        headItem = new VarItem(3, "Head");
        bodyItem = new VarItem(3 + 9, "Body");
        leftLegItem = new VarItem(2 + 9 * 2, "Left Leg");
        rightLegItem = new VarItem(4 + 9 * 2, "Right Leg");
    }


    public class VarItem
    {
        final int slot;
        final String name;
        ItemStack item;
        Material min, third, half, two_third, max;

        public VarItem(int slot, String name)
        {
            this.slot = slot;
            this.name = name;
            min = Material.RED_TERRACOTTA;
            third = Material.ORANGE_TERRACOTTA;
            half = Material.YELLOW_TERRACOTTA;
            two_third = Material.LIME_TERRACOTTA;
            max = Material.GREEN_TERRACOTTA;
            item = new ItemStack(max);
        }

        public void set(Inventory inv, int value, int min, int max)
        {
            double normal = (value - min / max - min);
            inv.setItem(slot, buildItem(normal, max));
        }

        public void set(Inventory inv, double value, double min, double max)
        {
            double normal = (value - min / max - min);
            inv.setItem(slot, buildItem(normal, max));
        }

        public ItemStack buildItem(double val, double max)
        {
            item.setType(calcItem(val));
            String s = MessageFormat.format("{3}{0} {4}| {3}HP{4}: {5}{1}{4}/{5}{2}", name, val, max, ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GREEN)
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(s);
            item.setItemMeta(im);
            return item;
        }

        public Material calcItem(double val)
        {
            if (val <= 0) return min;
            else if (val >= 1) return max;
            else if (val < .34) return third;
            else if (val > .66) return two_third;
            else return half;
        }
    }

}
