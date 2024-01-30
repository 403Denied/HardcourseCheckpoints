package com.hardcourse.hardcoursecheckpoints.Utils;

import java.util.HashMap;
import java.util.Map;

public class CheckpointData {
    private int level;
    private double x;
    private double y;
    private double z;
    private String world;

    public CheckpointData(int level, double x, double y, double z, String world) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public int getLevel() {
        return level;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("level", level);
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        map.put("world", world);
        return map;
    }

    public static CheckpointData fromMap(Map<String, Object> map) {
        int level = (int) map.get("level");
        double x = (double) map.get("x");
        double y = (double) map.get("y");
        double z = (double) map.get("z");
        String world = (String) map.get("world");
        return new CheckpointData(level, x, y, z, world);
    }
}
