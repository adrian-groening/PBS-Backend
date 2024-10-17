package com.pbs.app.Entities;

public class CreatorAttributes {
    public String attributesID, creatorID, work, location, bio, influencerLevel, tone, techLevel, mindedness, age;
    
    public CreatorAttributes(String creatorID, String attributesID, String work, String location, String bio, String influencerLevel, String tone, String techLevel, String mindedness, String age) {
        this.creatorID = creatorID;
        this.attributesID = attributesID;
        this.work = work;
        this.location = location;
        this.bio = bio;
        this.influencerLevel = influencerLevel;
        this.tone = tone;
        this.techLevel = techLevel;
        this.mindedness = mindedness;
        this.age = age;
    }


    public CreatorAttributes() {
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getAttributesID() {
        return attributesID;
    }

    public void setAttributesID(String attributesID) {
        this.attributesID = attributesID;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInfluencerLevel() {
        return influencerLevel;
    }

    public void setInfluencerLevel(String influencerLevel) {
        this.influencerLevel = influencerLevel;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(String techLevel) {
        this.techLevel = techLevel;
    }

    public String getMindedness() {
        return mindedness;
    }

    public void setMindedness(String mindedness) {
        this.mindedness = mindedness;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


}
