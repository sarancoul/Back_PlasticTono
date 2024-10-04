package com.example.plasti_tono.Model;

import java.util.Date;

public class TransactionHistoriqueDTO {

    private String userId;
    private Double pointsConvertis;
    private Double montant;
    private Date date;
    private String description;
    private String type;
    private String statut;

    public TransactionHistoriqueDTO(String userId, Double pointsConvertis, Double montant, Date date, String description, String statut) {
        this.userId = userId;
        this.pointsConvertis = pointsConvertis;
        this.montant = montant;
        this.date = date;
        this.description = description;

        this.statut = statut;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getPointsConvertis() {
        return pointsConvertis;
    }

    public void setPointsConvertis(Double pointsConvertis) {
        this.pointsConvertis = pointsConvertis;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
