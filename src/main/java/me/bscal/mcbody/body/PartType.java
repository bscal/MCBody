package me.bscal.mcbody.body;

public enum PartType
{

    HEAD(0, "Head"),
    BODY(1, "Body"),
    LEG_LEFT(2, "Left Leg"),
    LEG_RIGHT(3, "Right Leg"),
    ARM_LEFT(4, "Left Arm"),
    ARM_RIGHT(5, "Right Arm");

    // public static final int HEAD_ID = 0;
    // public static final int BODY_ID = 1;
    // public static final int LEFT_LEG_ID = 2;
    // public static final int RIGHT_LEG_ID = 3;
    // public static final int LEFT_ARM_ID = 4;
    // public static final int RIGHT_ARM_ID = 5;

    public final int id;
    public String localName;

    private PartType(final int id, final String localName)
    {
        this.id = id;
        this.localName = localName;
    }
}
