package com.example.plasti_tono.Model;

import java.time.LocalDateTime;

public class HistoriqueDepotDTO {
        private LocalDateTime dateDepot;
        private String codeKiosque;
        private Long poids;
        private int points;

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

        public Long getPoids() {
                return poids;
        }

        public void setPoids(Long poids) {
                this.poids = poids;
        }

        public int getPoints() {
                return points;
        }

        public void setPoints(int points) {
                this.points = points;
        }
}
