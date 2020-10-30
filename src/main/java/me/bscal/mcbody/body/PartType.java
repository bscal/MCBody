package me.bscal.mcbody.body;

public enum PartType
{

    HEAD(0, "Head"),
    BODY(1, "Body"),
    LEG_LEFT(2, "Left Leg"),
    LEG_RIGHT(3, "Right Leg"),
    ARM_LEFT(4, "Left Arm"),
    ARM_RIGHT(5, "Right Arm");

    public final int id;
    public String localName;

    private PartType(final int id, final String localName)
    {
        this.id = id;
        this.localName = localName;
    }
}
