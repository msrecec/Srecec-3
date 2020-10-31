package main.java.hr.java.covidportal.model;

import java.util.Objects;

public class Simptom extends ImenovaniEntitet {
    public static final String RIJETKO = "RIJETKO", SREDNJE = "SREDNJE", CESTO = "ČESTO";
    private String vrijednost;

    public Simptom(String naziv, String vrijednost) {
        super(naziv);
        this.vrijednost = vrijednost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Simptom)) return false;
        Simptom simptom = (Simptom) o;
        return getNaziv().equals(simptom.getNaziv()) &&
                getVrijednost().equals(simptom.getVrijednost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNaziv(), getVrijednost());
    }

    public String getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }
}
