package com.example.plasti_tono.Model;

public class UtilisateurPointsDTO {
    private String name;
    private String phone;
    private Double points;

    public UtilisateurPointsDTO(String name, String phone, Double points) {
        this.name = name;
        this.phone = phone;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Double getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
