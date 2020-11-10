package me.bscal.mcbody.ui;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.DevTec.TheAPI.GUIAPI.GUI;
import me.DevTec.TheAPI.GUIAPI.ItemGUI;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.BodyPart;
import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.entities.BodyPlayer;

public class BodyPartUI
{
    static final List<VarItem> ELEMENTS = new ArrayList<VarItem>(4);
    static
    {
        ELEMENTS.add(new VarItem(1, "Head"));
        ELEMENTS.add(new VarItem(1 + 9, "Body"));
        ELEMENTS.add(new VarItem(0 + 9 * 2, "Left Leg"));
        ELEMENTS.add(new VarItem(2 + 9 * 2, "Right Leg"));
    }

    static final String TITLE = "s Health";
    static final int SIZE = 3 * 9;

    static final int HEAD = PartType.HEAD.id;
    static final int BODY = PartType.BODY.id;
    static final int LEFT_LEG = PartType.LEG_LEFT.id;
    static final int RIGHT_LEG = PartType.LEG_RIGHT.id;

    private BodyPartUI() {}

    public static void open(Player p)
    {
        if (p == null || !MCBody.Get().GetPlayerPartManager().IsActive())
            return;

        GUI gui = new GUI(ChatColor.GREEN + p.getDisplayName() + TITLE, SIZE, p);

        final BodyPlayer body = MCBody.Get().GetPlayerPartManager().GetPlayer(p);
        for (int i = 0; i < ELEMENTS.size(); i++)
        {
            BodyPart part = body.GetPart(i);
            VarItem item = ELEMENTS.get(i);
            gui.setItem(item.slot, item.buildItem(part.GetHP(), 0, part.GetMaxHP()));
        }
    }

    static class VarItem
    {
        final int slot;
        final String name;
        final Material min, third, half, two_third, max;

        public VarItem(int slot, String name)
        {
            this.slot = slot;
            this.name = name;
            min = Material.RED_TERRACOTTA;
            third = Material.ORANGE_TERRACOTTA;
            half = Material.YELLOW_TERRACOTTA;
            two_third = Material.LIME_TERRACOTTA;
            max = Material.GREEN_TERRACOTTA;
        }

        public ItemGUI buildItem(double val, double min, double max)
        {
            val = val - min / max - min;
            ItemStack item = new ItemStack(calcItem(val));
            String s = MessageFormat.format("{3}{0} {4}| {3}HP{4}: {5}{1}{4}/{5}{2}", name, val, max, ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GREEN);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(s);
            item.setItemMeta(im);
            return new ItemGUI(item)
            {
                @Override
                public void onClick(Player arg0, GUI arg1, ClickType arg2) {return;}
            };
        }

        protected Material calcItem(double val)
        {
            if (val <= 0) return min;
            else if (val >= 1) return max;
            else if (val < .34) return third;
            else if (val > .66) return two_third;
            else return half;
        }
    }
}
