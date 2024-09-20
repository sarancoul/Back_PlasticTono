package com.example.plasti_tono.Model;

import java.time.LocalDateTime;

public class HistoriqueDepotDTO {
        private LocalDateTime dateDepot;
        private String codeKiosque;
        private Double poids;
        private double points;

        public LocalDateTime getDateDepot() {
                return dateDepot;
        }

        public void setDateDepot(LocalDateTime dateDepot) {
                this.dateDepot = dateDepot;
        }

        public String getCodeKiosque() {
                return codeKiosque;
        }

        public void setCodeKiosque(String codeKiosque) {
                this.codeKiosque = codeKiosque;
        }

        public Double getPoids() {
                return poids;
        }

        public void setPoids(Double poids) {
                this.poids = poids;
        }

        public double getPoints() {
                return points;
        }

        public void setPoints(double points) {
                this.points = points;
        }
}
