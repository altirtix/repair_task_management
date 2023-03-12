package repair.model;

import java.util.ArrayList;

public class RepairModel {
    private String name;
    private int cipher;
    private String repairType;
    private double repairCost;
    private String repairer;

    public static ArrayList<RepairModel> repairers = new ArrayList<RepairModel>();

    public static String[] categories = {"Name", "Cipher", "Repair Type", "Repair Cost", "Repairer"};

    public RepairModel(String name, int cipher, String repairType, double cost, String repairer) {
        this.name = name;
        this.cipher = cipher;
        this.repairType = repairType;
        this.repairCost = cost;
        this.repairer = repairer;
    }

    public RepairModel (RepairModel ob)
    {
        this.name = ob.name;
        this.cipher = ob.cipher;
        this.repairType = ob.repairType;
        this.repairCost = ob.repairCost;
        this.repairer = ob.repairer;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCipher(int cipher)
    {
        this.cipher = cipher;
    }

    public void setRepairType(String repairType)
    {
        this.repairType = repairType;
    }

    public void setRepairCost(Double repairCost)
    {
        this.repairCost = repairCost;
    }

    public void setRepairer(String repairer)
    {
        this.repairer = repairer;
    }

    public String getName()
    {
        return this.name;
    }

    public int getCipher()
    {
        return this.cipher;
    }

    public String getRepairType()
    {
        return this.repairType;
    }

    public double getRepairCost()
    {
        return this.repairCost;
    }

    public String getRepairer()
    {
        return this.repairer;
    }

    public String toString()
    {
        return getName() + " "
                + getCipher() + " "
                + getRepairType() + " "
                + getRepairCost() + " "
                + getRepairer() + "\n";
    }

    public String toString2()
    {
        return "Name: " + getName() + "\n"
                + "Cipher: " + getCipher() + "\n"
                + "Repair type: " + getRepairType() + "\n"
                + "Repair cost: " + getRepairCost() + " $" + "\n"
                + "Repairer: " + getRepairer() + "\n"
                + "\n";
    }
}
