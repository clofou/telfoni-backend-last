package org.bamappli.telfonibackendspring.DTO;

public class VenteParMoisDTO {
    private int mois;
    private double montantTotal;

    public VenteParMoisDTO(int mois, double montantTotal) {
        this.mois = mois;
        this.montantTotal = montantTotal;
    }

    // Getters et Setters
    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }
}
