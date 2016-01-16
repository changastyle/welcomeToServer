package net;

import com.google.gson.Gson;

public class RAM 
{
    private float free;
    private float used;
    private float total;

    public RAM(float free, float total) 
    {
        this.free = free;
        this.total = total;
        this.used = total - free;
    }
    
    //<editor-fold desc="GYS:">

    public float getFree() {
        return free;
    }
    public void setFree(float free) {
        this.free = free;
    }
    public float getUsed() {
        return used;
    }
    public void setUsed(float used) {
        this.used = used;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total)
    {
        this.total = total;
    }
    
    //</editor-fold>

    @Override
    public String toString()
    {
        return "RAM{" + "free=" + free + ", used=" + used + ", total=" + total + '}';
    }
    public String toJSON()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
 
}