package me.bscal.mcbody.ui;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gyurix.inventory.CloseableGUI;
import gyurix.inventory.GUIConfig;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.BodyPart;
import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.entities.BodyPlayer;

public class BodyPartUI
{
    final GUIConfig m_bodyGUI = new GUIConfig();
    final List<VarItem> m_elements = new ArrayList<VarItem>(4);

    static final int HEAD       = PartType.HEAD.id;
    static final int BODY       = PartType.BODY.id;
    static final int LEFT_LEG   = PartType.LEG_LEFT.id;
    static final int RIGHT_LEG  = PartType.LEG_RIGHT.id;

    public BodyPartUI()
    {
        m_bodyGUI.title = "Player Health";
        m_bodyGUI.size = 6 * 9;
        m_bodyGUI.separator = new ItemStack(Material.AIR);

        m_elements.add(new VarItem(3, "Head"));
        m_elements.add(new VarItem(3 + 9, "Body"));
        m_elements.add(new VarItem(2 + 9 * 2, "Left Leg"));
        m_elements.add(new VarItem(4 + 9 * 2, "Right Leg"));
    }

    public void open(Player p)
    {
        if (p == null || !MCBody.Get().GetPlayerPartManager().IsActive())
            return;

        final BodyPlayer body = MCBody.Get().GetPlayerPartManager().GetPlayer(p);
        for (int i = 0; i < m_elements.size(); i++)
        {
            BodyPart part = body.GetPart(i);
            m_bodyGUI.items.put(m_elements.get(i).slot, m_elements.get(i).buildItem(part.GetHP(), 0, part.GetMaxHP()));
        }

        p.openInventory(m_bodyGUI.getInventory(new CloseableGUI(p)));
    }

    public class VarItem
    {
        final int slot;
        final String name;
        final Material min, third, half, two_third, max;
        ItemStack item;

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

        public ItemStack buildItem(double val, double min, double max)
        {
            val = val - min / max - min;
            item.setType(calcItem(val));
            String s = MessageFormat.format("{3}{0} {4}| {3}HP{4}: {5}{1}{4}/{5}{2}", name, val, max, ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.GREEN);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(s);
            item.setItemMeta(im);
            return item;
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
