package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.InputMismatchException;
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
        boolean ispravanUnos = true;

        System.out.printf("Unesite podatke o %d zupanije:%n", zupanije.length);
        for (int i = 0; i < zupanije.length; ++i) {
            do {
                try {

                    System.out.printf("Unesite naziv zupanije: ");

                    nazivZupanije = input.nextLine();

                    System.out.printf("Unesite broj stanovnika: ");

                    brojStanovnika = input.nextInt();

                    input.nextLine();

                    if (brojStanovnika < 0) {

                        System.out.println("Pogrešan unos! Molimo unesite pozitivan cijeli broj.");

                        logger.error("Prilikom unosa broja stanovnika, unesen je negativan broj.");

                        ispravanUnos = false;

                    } else {

                        logger.info("Unesen je broj stanovnika: " + Integer.toString(brojStanovnika));

                        ispravanUnos = true;

                        zupanije[i] = new Zupanija(nazivZupanije, brojStanovnika);

                    }


                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa broja stanovnika je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }
            } while (!ispravanUnos);
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
        int brojOdabranihSimptoma = 0, odabraniSimptom = 0;
        int[] odabraniSimptomi = null;
        Simptom[] kopiraniSimptomi;
        int bolestIliVirus = 0;
        boolean ispravanUnos = true;

        System.out.printf("Unesite podatke o %d bolesti ili virusa:%n", bolesti.length);

        for (int i = 0; i < bolesti.length; ++i) {

            // Odabir unosa bolesti ili virusa i validacija unosa

            do {
                try {
                    System.out.printf("Unosite li bolest ili virus ?%n1)BOLEST%n2)VIRUS%n");

                    bolestIliVirus = input.nextInt();

                    input.nextLine();

                    if (bolestIliVirus != 1 && bolestIliVirus != 2) {

                        System.out.println("Pogresan unos! Molimo unesite jedan od ponuđenih brojeva.");

                        logger.error("Prilikom unosa Bolesti ili Virusa unesen je broj izvan raspona dopuštenih brojeva.");

                        ispravanUnos = false;

                    } else {

                        logger.info((bolestIliVirus == 1 ? "Unesena je  Bolest: " : "Unesen je Virus: ")
                                + Integer.toString(bolestIliVirus));

                        ispravanUnos = true;

                    }
                } catch (InputMismatchException ex) {
                    logger.error("Prilikom unosa bolesti ili virusa je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }

            } while (!ispravanUnos);

            System.out.printf("Unesite naziv bolesti ili virusa: ");

            nazivBolestiIliVirusa = input.nextLine();

            // Unos Broja Odabranih Simptoma i validacija unosa

            do {
                try {
                    System.out.printf("Unesite broj simptoma: ");

                    brojOdabranihSimptoma = input.nextInt();

                    input.nextLine();

                    if (brojOdabranihSimptoma > simptomi.length || brojOdabranihSimptoma < 1) {

                        System.out.println("Pogresan unos broja simptoma ! Unesen je broj izvan raspona ukupnog broja mogućih simptoma.");

                        logger.error("Prilikom unosa broja simptoma unesen je broj izvan raspona ukupnog broja mogućih simptoma.");

                        ispravanUnos = false;
                    } else {

                        logger.info("Uneseni broj simptoma: " + Integer.toString(brojOdabranihSimptoma));

                        ispravanUnos = true;

                        odabraniSimptomi = new int[brojOdabranihSimptoma];
                    }

                } catch (InputMismatchException ex) {
                    logger.error("Prilikom unosa broja simptoma je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }

            } while (!ispravanUnos);

            // Unos odabranih simptoma i validacija

            for (int j = 0; j < brojOdabranihSimptoma; ++j) {

                // Biranje Postojeceg Simptoma i validacija unosa

                do {
                    System.out.printf("Odaberite %d. simptom:%n", j + 1);

                    // Ispis Postojecih Simptoma

                    for (int k = 0; k < simptomi.length; ++k) {
                        System.out.printf("%d. %s %s%n", k + 1, simptomi[k].getNaziv(), simptomi[k].getVrijednost());
                    }

                    try {
                        System.out.print("Odabir: ");

                        odabraniSimptom = input.nextInt();

                        input.nextLine();

                        if (odabraniSimptom > simptomi.length || odabraniSimptom < 1) {

                            System.out.println("Neispravan unos, molimo pokusajte ponovno!");

                            logger.error("Prilikom biranja simptoma unesen je broj izvan raspona ukupnog broja postojećih simptoma.");

                            ispravanUnos = false;

                        } else {

                            ispravanUnos = true;

                            // Provjera postojanosti Odabranog Postojeceg Simptoma u prethodno Odabranim Simptomima

                            for (int k = 0; k < odabraniSimptomi.length; ++k) {
                                if (odabraniSimptomi[k] == odabraniSimptom) {
                                    System.out.println("Odabrani Simptom je vec unesen! Molimo odaberite ponovno.");

                                    logger.error("Odabran je Simptom koji je već unesen: " + Integer.toString(odabraniSimptom));

                                    ispravanUnos = false;

                                    break;
                                }
                            }

                            if (ispravanUnos) {

                                logger.info("Odabran je (broj) simptom is postojećih simptoma: " + Integer.toString(odabraniSimptom));

                                odabraniSimptomi[j] = odabraniSimptom;
                            }
                        }
                    } catch (InputMismatchException ex) {

                        logger.error("Prilikom unosa brojčane vrijednosti kod biranja postojećih simptoma je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                        System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                        input.nextLine();

                        ispravanUnos = false;

                    }

                } while (!ispravanUnos);
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
        boolean duplikatiOsoba = false, ispravanUnos = true;
        int odabranaZupanija = 0;
        int odabranaBolest = 0;
        int odabranaKontaktiranaOsoba = 0;
        int[] odabraneKontaktiraneOsobe;
        int brojKontaktiranihOsoba = 0;
        String ime, prezime;
        Integer starost = 0;
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

            // Unos starosti i validacija unosa

            do {

                try {

                    System.out.printf("Unesite starost osobe: ");

                    starost = input.nextInt();

                    input.nextLine();

                    if (starost < 0) {
                        System.out.println("Unesena vrijednost ne smije biti negativan broj! Molimo ponovite unos.");

                        logger.error("Prilikom unosa starosti osobe, unesen je negativan broj: " + Integer.toString(starost));

                        ispravanUnos = false;

                    } else {

                        logger.info("Unesena je starost osobe: " + Integer.toString(starost));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa brojčane vrijednosti kod starosti osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }

            } while (!ispravanUnos);


            // Unos zupanije prebivalista i validacija

            do {

                try {

                    System.out.printf("Unesite županiju prebivališta osobe:%n");

                    for (int j = 0; j < zupanije.length; ++j) {
                        System.out.printf("%d. %s%n", j + 1, zupanije[j].getNaziv());
                    }

                    System.out.print("Odabir: ");

                    odabranaZupanija = input.nextInt();

                    input.nextLine();

                    // Provjera ispravnosti unosa Odabrane Zupanije

                    if (odabranaZupanija < 1 || odabranaZupanija > zupanije.length) {

                        System.out.println("Pogresan unos županije!");

                        logger.error("Prilikom unosa županije osobe, unesen je broj izvan prethodno navedenog raspona: "
                                + Integer.toString(odabranaZupanija));

                        ispravanUnos = false;

                    } else {

                        logger.info("Unesen je odabir županije: " + Integer.toString(odabranaZupanija));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa brojčane vrijednosti kod biranja županije osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }


            } while (!ispravanUnos);


            zupanija = zupanije[odabranaZupanija - 1];

            // Unos bolesti osobe

            do {

                try {

                    System.out.println("Unesite bolest ili virus osobe:");

                    for (int j = 0; j < bolesti.length; ++j) {
                        System.out.printf("%d. %s%n", j + 1, bolesti[j].getNaziv());
                    }

                    System.out.print("Odabir: ");

                    odabranaBolest = input.nextInt();

                    input.nextLine();

                    // Provjera ispravnosti unosa Odabrane Bolesti Osobe

                    if (odabranaBolest < 1 || odabranaBolest > bolesti.length) {

                        System.out.println("Pogrešan unos bolesti/virusa!");

                        logger.error("Prilikom unosa bolesti/virusa osobe, unesen je broj izvan prethodno navedenog raspona: "
                                + Integer.toString(odabranaBolest));

                        ispravanUnos = false;

                    } else {

                        logger.info(((bolesti[odabranaBolest - 1] instanceof Virus) ? "Unesen je virus: " : "Unesena je bolest: ")
                                + Integer.toString(odabranaBolest));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa brojčane vrijednosti kod biranja bolesti/virusa osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;

                }

            } while (!ispravanUnos);


            zarazenBolescu = bolesti[odabranaBolest - 1] instanceof Virus ?
                    new Virus(bolesti[odabranaBolest - 1].getNaziv(), bolesti[odabranaBolest - 1].getSimptomi()) :
                    new Bolest(bolesti[odabranaBolest - 1].getNaziv(), bolesti[odabranaBolest - 1].getSimptomi());


            // Provjera osoba s kojim je osoba usla u kontakt u slucaju da nije prva osoba - prva se ne gleda

            if (i > 0) {

                // Unos broja kontaktiranih osoba i validacija

                do {

                    try {

                        System.out.println("Unesite broj osoba koje su bile u kontaktu s tom osobom:");

                        brojKontaktiranihOsoba = input.nextInt();

                        input.nextLine();

                        // Provjera unosa broja kontaktiranih osoba

                        if (brojKontaktiranihOsoba > i || brojKontaktiranihOsoba < 0) {

                            System.out.println("Greska u unosu broja kontaktiranih osoba");

                            logger.error("Prilikom unosa broja kontaktiranih osoba, unesen je broj izvan raspona unesenog broja osoba: "
                                    + Integer.toString(brojKontaktiranihOsoba));

                            ispravanUnos = false;

                        } else {

                            ispravanUnos = true;

                            logger.info("Unesen je broj kontaktiranih osoba: " + Integer.toString(brojKontaktiranihOsoba));

                        }

                    } catch (InputMismatchException ex) {

                        logger.error("Prilikom unosa brojčane vrijednosti kod biranja broja kontaktiranih osoba je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                        System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                        input.nextLine();

                        ispravanUnos = false;

                    }

                } while (!ispravanUnos);

                if (brojKontaktiranihOsoba > 0) {

                    // Unos i validacija Odabranih Kontaktiranih Osoba

                    odabraneKontaktiraneOsobe = new int[brojKontaktiranihOsoba];

                    for (int j = 0; j < brojKontaktiranihOsoba; ++j) {

                        do {

                            // Unos Odabrane Kontaktirane Osobe

                            try {

                                System.out.printf("Odaberite %d. osobu: %n", j + 1);

                                for (int k = 0; k < i; ++k) {
                                    System.out.printf("%d. %s %s%n", k + 1, osobe[k].getIme(), osobe[k].getPrezime());
                                }

                                System.out.print("Odabir: ");

                                odabranaKontaktiranaOsoba = input.nextInt();

                                input.nextLine();

                                // Provjera unosa Odabrane Kontaktirane Osobe

                                if (odabranaKontaktiranaOsoba < 1 || odabranaKontaktiranaOsoba > i) {

                                    System.out.println("Greška pri unosu odabrane kontaktirane osobe");

                                    logger.error("Prilikom unosa odabira kontaktirane osobe, unesen je broj izvan raspona unesenog broja osoba: "
                                            + Integer.toString(odabranaKontaktiranaOsoba));

                                    ispravanUnos = false;

                                } else {

                                    ispravanUnos = true;

                                    // (Provjera duplikata) Provjera postojanosti Odabrane Kontaktirane Osobe u prethodno Odabranim Kontaktiranim Osobama

                                    for (int k = 0; k < odabraneKontaktiraneOsobe.length; ++k) {

                                        if (odabraneKontaktiraneOsobe[k] == odabranaKontaktiranaOsoba) {

                                            System.out.println("Osoba je vec odabrana, molimo ponovno unesite!");

                                            duplikatiOsoba = true;

                                            ispravanUnos = false;

                                        }
                                    }

                                    if (duplikatiOsoba) {

                                        duplikatiOsoba = false;

                                        logger.error("Prilikom unosa odabira kontaktirane osobe, unesena je prethodno odabrana osoba (duplikat): "
                                                + Integer.toString(odabranaKontaktiranaOsoba));

                                    }

                                    if (ispravanUnos) {

                                        logger.info("Unesen je odabir kontaktirane osobe: " + Integer.toString(odabranaKontaktiranaOsoba));

                                        odabraneKontaktiraneOsobe[j] = odabranaKontaktiranaOsoba;

                                    }

                                }

                            } catch (InputMismatchException ex) {

                                logger.error("Prilikom unosa brojčane vrijednosti kod unosa odabrane kontaktirane osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                                System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                                input.nextLine();

                                ispravanUnos = false;

                            }

                        } while (!ispravanUnos);

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




























