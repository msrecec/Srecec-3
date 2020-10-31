package main.java.hr.java.covidportal.model;

public class Virus extends Bolest implements Zarazno {
    public Virus(String naziv, Simptom[] simptomi) {
        super(naziv, simptomi);
    }

    // "Deep Copy" implementacija prelaska zaraze na drugu osobu

    @Override
    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(new Virus(this.getNaziv(), this.getSimptomi()));
    }

}
