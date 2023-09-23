package Entities;

public class RendezVous {
    private String heureRdv;
    private String nomPatient;
    private String nomPathologie;

    public RendezVous(String heureRdv, String nomPatient, String nomPathologie) {
        this.heureRdv = heureRdv;
        this.nomPatient = nomPatient;
        this.nomPathologie = nomPathologie;
    }

    public String getHeureRdv() {
        return heureRdv;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public String getNomPathologie() {
        return nomPathologie;
    }
}
