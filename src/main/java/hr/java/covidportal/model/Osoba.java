package main.java.hr.java.covidportal.model;

import java.util.Arrays;
import java.util.Objects;

public class Osoba {


    // Implementacija "Builder Pattern"

    public static class Builder {
        private String ime, prezime;
        private Integer starost;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private Osoba[] kontaktiraneOsobe;

        public Builder(String ime) {
            this.ime = ime;
        }
        public Builder prezime(String prezime) {
            this.prezime = prezime;
            return this;
        }
        public Builder starost(Integer starost) {
            this.starost = starost;
            return this;
        }
        public Builder zupanija(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }
        public Builder zarazenBolescu(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }
        public Builder kontaktiraneOsobe(Osoba[] kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }
        public Osoba build() {
            Osoba osoba = new Osoba();
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.starost = this.starost;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;

            // Zaraza osoba koje su bile u kontaktu s ovom osobom

            if (!Objects.isNull(osoba.kontaktiraneOsobe)) {
                if (zarazenBolescu instanceof Virus virus) {
                    for (Osoba kontaktiranaOsoba : osoba.kontaktiraneOsobe) {
                        virus.prelazakZarazeNaOsobu(kontaktiranaOsoba);
                    }
                }
            }
            return osoba;
        }


    }

    // Class Fields

    private String ime, prezime;
    private Integer starost;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private Osoba[] kontaktiraneOsobe;

    // Constructor is now private

    private Osoba() {

    }

    // Getters and setters


    @Override
    public String toString() {

        String ispisKontaktiranihOsoba = "";

        if (Objects.isNull(kontaktiraneOsobe)) {
            ispisKontaktiranihOsoba = "Nema kontaktiranih osoba.\n";
        } else {
            for (Osoba kontaktiranaOsoba : kontaktiraneOsobe) {
                ispisKontaktiranihOsoba += kontaktiranaOsoba.getIme() + " " + kontaktiranaOsoba.getPrezime() + "\n";
            }
        }


        return "Ime i prezime: " + ime + " " + prezime + "\n" +
                "Starost: " + starost + "\n" +
                "Županija prebivališta: " + zupanija.getNaziv() + "\n" +
                "Zaražen bolešću: " + zarazenBolescu.getNaziv() + "\n" +
                "Kontaktirane osobe: \n" + ispisKontaktiranihOsoba;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Integer getStarost() {
        return starost;
    }

    public void setStarost(Integer starost) {
        this.starost = starost;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public Osoba[] getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(Osoba[] kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }
}
