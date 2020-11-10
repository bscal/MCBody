package me.bscal.mcbody.body;

import me.bscal.mcbody.utils.MathUtils;

public class BodyPart
{
    private int m_id;

    private double m_maxHp;
    private double m_dmgMultiplier;

    private double m_hp;

    private static final double MIN_HP = 0;

    protected BodyPart() {}

    public BodyPart(int id, double maxHp)
    {
        m_id = id;
        m_maxHp = maxHp;
        Heal();
    }

    public int GetID()
    {
        return m_id;
    }

    public void SetHP(double hp)
    {
        m_hp = MathUtils.ClampD(hp, MIN_HP, m_maxHp);
    }

    public void AddHP(double hp)
    {
        m_hp = MathUtils.ClampD(m_hp + hp, MIN_HP, m_maxHp);
    }

    public void SubtractHP(double hp)
    {
        m_hp = MathUtils.ClampD(m_hp - hp, MIN_HP, m_maxHp);
    }

    public void Heal()
    {
        m_hp = m_maxHp;
    }

    public double GetMaxHP()
    {
        return m_maxHp;
    }

    public double GetHP()
    {
        return m_hp;
    }

    public void SetDamageMultiplier(double multiplier)
    {
        m_dmgMultiplier = multiplier;
    }

    public double GetDamageMultiplier()
    {
        return m_dmgMultiplier;
    }

}
