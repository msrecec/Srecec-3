package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;



public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public static final int BROJ_ZUPANIJA = 3, BROJ_SIMPTOMA = BROJ_ZUPANIJA, BROJ_BOLESTI = 2, BROJ_VIRUSA = 2, BROJ_OSOBA = BROJ_ZUPANIJA;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Zupanija[] zupanije = new Zupanija[BROJ_ZUPANIJA];
        Simptom[] simptomi = new Simptom[BROJ_SIMPTOMA];
        Bolest[] bolesti = new Bolest[BROJ_BOLESTI + BROJ_VIRUSA];
        Osoba[] osobe = new Osoba[BROJ_OSOBA];

        logger.info("Pokrenuli smo aplikaciju");


        // Unos Zupanija

        unosZupanija(input, zupanije);

        // Unos Simptoma

        unosSimptoma(input, simptomi);

        // Unos Bolesti

        unosBolesti(input, simptomi, bolesti);

        // Unos osoba

        unosOsoba(input, zupanije, bolesti, osobe);

        // Ispis osoba

        ispisOsoba(osobe);


    }

    private static void unosZupanija(Scanner input, Zupanija[] zupanije) {
        String nazivZupanije;
        int brojStanovnika;

        System.out.printf("Unesite podatke o %d zupanije:%n", zupanije.length);
        for (int i = 0; i < zupanije.length; ++i) {

            System.out.printf("Unesite naziv zupanije: ");
            nazivZupanije = input.nextLine();

            System.out.printf("Unesite broj stanovnika: ");
            brojStanovnika = Integer.parseInt(input.nextLine());

            zupanije[i] = new Zupanija(nazivZupanije, brojStanovnika);

        }
    }

    private static void unosSimptoma(Scanner input, Simptom[] simptomi) {
        String nazivSimptoma;
        String vrijednostSimptoma;

        System.out.printf("Unesite podatke o %d simptoma:%n", simptomi.length);

        for (int i = 0; i < simptomi.length; ++i) {
            System.out.printf("Unesite naziv simptoma: ");
            nazivSimptoma = input.nextLine();

            do {
                System.out.printf("Unesite vrijednost simptoma(%s, %s, %s): ", Simptom.RIJETKO, Simptom.SREDNJE, Simptom.CESTO);
                vrijednostSimptoma = input.nextLine();

                // if(!vrijednostSimptoma.in([RIJETKO, SREDNJE, CESTO])) Provjera pojave vrijednosti simptoma analogno IN Operatoru u SQL

                if (!Arrays.asList(Simptom.RIJETKO, Simptom.SREDNJE, Simptom.CESTO).contains(vrijednostSimptoma)) {
                    System.out.println("Pogrešan unos simptoma !");
                }

            } while (!Arrays.asList(Simptom.RIJETKO, Simptom.SREDNJE, Simptom.CESTO).contains(vrijednostSimptoma));

            simptomi[i] = new Simptom(nazivSimptoma, vrijednostSimptoma);

        }
    }

    private static void unosBolesti(Scanner input, Simptom[] simptomi, Bolest[] bolesti) {
        String nazivBolestiIliVirusa;
        int brojOdabranihSimptoma, odabraniSimptom;
        int[] odabraniSimptomi;
        Simptom[] kopiraniSimptomi;
        int bolestIliVirus;

        System.out.printf("Unesite podatke o %d bolesti ili virusa:%n", bolesti.length);

        for (int i = 0; i < bolesti.length; ++i) {

            // Odabir unosa bolesti ili virusa

            do {
                System.out.printf("Unosite li bolest ili virus ?%n1)BOLEST%n2)VIRUS%n");
                bolestIliVirus = Integer.parseInt(input.nextLine());

                // Provjera unosa bolesti ili virusa

                if (bolestIliVirus != 1 && bolestIliVirus != 2) {
                    System.out.println("Pogresan unos!");
                }
            } while (bolestIliVirus != 1 && bolestIliVirus != 2);
            System.out.printf("Unesite naziv bolesti ili virusa: ");
            nazivBolestiIliVirusa = input.nextLine();

            // Unos Broja Odabranih Simptoma

            do {
                System.out.printf("Unesite broj simptoma: ");
                brojOdabranihSimptoma = Integer.parseInt(input.nextLine());

                // Provjera unesenog Broja Odabranih Simptoma

                if (brojOdabranihSimptoma > simptomi.length || brojOdabranihSimptoma < 1) {
                    System.out.println("Pogresan unos broja simptoma !");
                }

            } while (brojOdabranihSimptoma > simptomi.length || brojOdabranihSimptoma < 1);

            // Unos odabranih simptoma

            odabraniSimptomi = new int[brojOdabranihSimptoma];

            for (int j = 0; j < brojOdabranihSimptoma; ++j) {
                System.out.printf("Odaberite %d. simptom:%n", j + 1);

                // Ispis Postojecih Simptoma

                for (int k = 0; k < simptomi.length; ++k) {
                    System.out.printf("%d. %s %s%n", k + 1, simptomi[k].getNaziv(), simptomi[k].getVrijednost());
                }

                // Biranje Postojeceg Simptoma

                System.out.print("Odabir: ");

                odabraniSimptom = Integer.parseInt(input.nextLine());

                // Provjera ispravnog unosa Odabranog Postojeceg Simptoma

                if (odabraniSimptom > simptomi.length || odabraniSimptom < 1) {
                    System.out.println("Neispravan unos, molimo pokusajte ponovno!");
                    j--;
                    continue;
                }

                // Provjera postojanosti Odabranog Postojeceg Simptoma u prethodno Odabranim Simptomima

                for (int k = 0; k < odabraniSimptomi.length; ++k) {
                    if (odabraniSimptomi[k] == odabraniSimptom) {
                        System.out.println("Odabrani Simptom je vec unesen!");
                        j--;
                        continue;
                    }
                }

                // Dodavanje Odabranog Simptoma u polje Odabranih Simptoma

                odabraniSimptomi[j] = odabraniSimptom;

            }

            // Kopiranje Postojecih Simptoma u novo polje Kopiranih Simptoma

            kopiraniSimptomi = new Simptom[brojOdabranihSimptoma];
            for (int j = 0; j < brojOdabranihSimptoma; ++j) {
                kopiraniSimptomi[j] = new Simptom(simptomi[odabraniSimptomi[j] - 1].getNaziv(), simptomi[odabraniSimptomi[j] - 1].getVrijednost());
            }

            // Provjera da li je unos bolest ili virus

            bolesti[i] = bolestIliVirus == 1 ? new Bolest(nazivBolestiIliVirusa, kopiraniSimptomi) : new Virus(nazivBolestiIliVirusa, kopiraniSimptomi);
        }
    }

    private static void ispisOsoba(Osoba[] osobe) {
        System.out.println("Popis osoba:");

        for (Osoba osoba : osobe) {
            System.out.print(osoba.toString());
        }
    }

    private static void unosOsoba(Scanner input, Zupanija[] zupanije, Bolest[] bolesti, Osoba[] osobe) {
        boolean duplikatiOsoba = false;
        int odabranaZupanija;
        int odabranaBolest;
        int odabranaKontaktiranaOsoba;
        int[] odabraneKontaktiraneOsobe;
        int brojKontaktiranihOsoba = 0;
        String ime, prezime;
        Integer starost;
        Zupanija zupanija;
        Bolest zarazenBolescu;
        Osoba[] kontaktiraneOsobe = null;

        for (int i = 0; i < osobe.length; ++i) {

            // Unos imena

            System.out.printf("Unesite ime %d. osobe: ", i + 1);
            ime = input.nextLine();

            // Unos prezimena

            System.out.printf("Unesite prezime %d. osobe: ", i + 1);
            prezime = input.nextLine();

            // Unos starosti

            System.out.printf("Unesite starost osobe: ");
            starost = Integer.parseInt(input.nextLine());

            // Unos zupanije prebivalista

            do {

                System.out.printf("Unesite zupaniju prebivalista osobe:%n");

                for (int j = 0; j < zupanije.length; ++j) {
                    System.out.printf("%d. %s%n", j + 1, zupanije[j].getNaziv());
                }

                System.out.print("Odabir: ");

                odabranaZupanija = Integer.parseInt(input.nextLine());

                // Provjera ispravnosti unosa Odabrane Zupanije

                if (odabranaZupanija < 1 || odabranaZupanija > zupanije.length) {
                    System.out.println("Pogresan unos zupanije!");
                }

            } while (odabranaZupanija < 1 || odabranaZupanija > zupanije.length);


            zupanija = zupanije[odabranaZupanija - 1];

            // Unos bolesti osobe

            do {

                System.out.println("Unesite bolest ili virus osobe:");

                for (int j = 0; j < bolesti.length; ++j) {
                    System.out.printf("%d. %s%n", j + 1, bolesti[j].getNaziv());
                }

                System.out.print("Odabir: ");

                odabranaBolest = Integer.parseInt(input.nextLine());

                // Provjera ispravnosti unosa Odabrane Bolesti Osobe

                if (odabranaBolest < 1 || odabranaBolest > bolesti.length) {
                    System.out.println("Pogresan unos bolesti!");
                }

            } while (odabranaBolest < 1 || odabranaBolest > bolesti.length);


            zarazenBolescu = bolesti[odabranaBolest - 1] instanceof Virus ?
                    new Virus(bolesti[odabranaBolest - 1].getNaziv(), bolesti[odabranaBolest - 1].getSimptomi()) :
                    new Bolest(bolesti[odabranaBolest - 1].getNaziv(), bolesti[odabranaBolest - 1].getSimptomi());


            // Provjera osoba s kojim je osoba usla u kontakt u slucaju da nije prva osoba - prva se ne gleda

            if (i > 0) {

                // Unos broja kontaktiranih osoba

                do {

                    System.out.println("Unesite broj osoba koje su bile u kontaktu s tom osobom:");

                    brojKontaktiranihOsoba = Integer.parseInt(input.nextLine());

                    // Provjera unosa broja kontaktiranih osoba

                    if (brojKontaktiranihOsoba > i || brojKontaktiranihOsoba < 0) {
                        System.out.println("Greska u unosu broja kontaktiranih osoba");
                    }

                } while (brojKontaktiranihOsoba > i || brojKontaktiranihOsoba < 0);

                if (brojKontaktiranihOsoba > 0) {

                    // Unos Odabranih Kontaktiranih Osoba

                    odabraneKontaktiraneOsobe = new int[brojKontaktiranihOsoba];

                    for (int j = 0; j < brojKontaktiranihOsoba; ++j) {

                        // Unos Odabrane Kontaktirane Osobe

                        System.out.printf("Odaberite %d. osobu: %n", j + 1);

                        for (int k = 0; k < i; ++k) {
                            System.out.printf("%d. %s %s%n", k + 1, osobe[k].getIme(), osobe[k].getPrezime());
                        }

                        System.out.print("Odabir: ");

                        odabranaKontaktiranaOsoba = Integer.parseInt(input.nextLine());

                        // Provjera unosa Odabrane Kontaktirane Osobe

                        if (odabranaKontaktiranaOsoba < 1 || odabranaKontaktiranaOsoba > i) {
                            System.out.println("Greska pri unosu odabrane kontaktirane osobe");
                            j--;
                            continue;
                        }

                        // Provjera postojanosti Odabrane Kontaktirane Osobe u prethodno Odabranim Kontaktiranim Osobama

                        for (int k = 0; k < odabraneKontaktiraneOsobe.length; ++k) {
                            if (odabraneKontaktiraneOsobe[k] == odabranaKontaktiranaOsoba) {
                                System.out.println("Osoba je vec odabrana, molimo ponovno unesite!");
                                duplikatiOsoba = true;
                            }
                        }

                        if (duplikatiOsoba) {
                            duplikatiOsoba = false;
                            j--;
                            continue;
                        }

                        odabraneKontaktiraneOsobe[j] = odabranaKontaktiranaOsoba;

                    }

                    // Spremanje Odabranih Kontaktiranih Osoba u polje Kontaktiranih Osoba

                    kontaktiraneOsobe = new Osoba[odabraneKontaktiraneOsobe.length];

                    for (int j = 0; j < odabraneKontaktiraneOsobe.length; ++j) {
                        kontaktiraneOsobe[j] = osobe[odabraneKontaktiraneOsobe[j] - 1];
                    }
                }
            }

            // Spremanje osoba u polje osoba

            if (i == 0) {
                osobe[i] = new Osoba.Builder(ime).prezime(prezime).starost(starost).zupanija(zupanija)
                        .zarazenBolescu(zarazenBolescu).build();
            } else {
                osobe[i] = new Osoba.Builder(ime).prezime(prezime).starost(starost).zupanija(zupanija)
                        .zarazenBolescu(zarazenBolescu).kontaktiraneOsobe(kontaktiraneOsobe).build();
            }
        }
    }
}




























