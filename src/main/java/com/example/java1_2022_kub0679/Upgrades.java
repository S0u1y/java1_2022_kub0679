package com.example.java1_2022_kub0679;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Upgrades {

    private double[] ownedStats;
    private double speed = 1;
    private double jumpPower = 1;

    private ArrayList<Image> ownedHats = new ArrayList<>();
    private final Image[] hats;

    private String skill = "";
    private ArrayList<String> ownedSkills = new ArrayList<>();

    public Upgrades(){
        hats = new Image[]{
                new Image(getClass().getResource("images/hat_1.png").toString()),
                new Image(getClass().getResource("images/hat_2.png").toString()),
                new Image(getClass().getResource("images/hat_3.png").toString()),
        };
        ownedStats = new double[]{speed, jumpPower};
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(double jumpPower) {
        this.jumpPower = jumpPower;
    }

    public Image[] getHats() {
        return hats;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public boolean addSkill(String skill){
        if(skillOwned(skill)) return false;
        ownedSkills.add(skill);
        return true;
    }
    public boolean skillOwned(String skill){
        for(String oSkill : ownedSkills){
            if(oSkill.equals(skill)) {
                return true;
            }
        }
        return false;
    }

    public void setOwnedSkills(ArrayList<String> ownedSkills) {
        this.ownedSkills = ownedSkills;
    }

    public boolean speedOwned(double speed){
        if(speed <= ownedStats[0]) return true;
        return false;
    }
    public boolean jumpOwned(double jump){
        if(jump <= ownedStats[1]) return true;
        return false;
    }

    public boolean setSpeedOwned(double speed){
        if(!speedOwned(speed)){
            ownedStats[0] = speed;
            return true;
        }
        return false;
    }
    public boolean setJumpOwned(double jump){
        if(!jumpOwned(jump)){
            ownedStats[1] = jump;
            return true;
        }
        return false;
    }
    public boolean addHat(int hatIndex){
        if(ownedHats.contains(hats[hatIndex])) return false;
        ownedHats.add(hats[hatIndex]);
        return true;
    }

}
