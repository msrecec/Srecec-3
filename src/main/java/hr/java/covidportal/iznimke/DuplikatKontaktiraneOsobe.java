package main.java.hr.java.covidportal.iznimke;

public class DuplikatKontaktiraneOsobe extends Exception {

    public DuplikatKontaktiraneOsobe() {
        super("Unesena osoba je duplikat!");
    }

    public DuplikatKontaktiraneOsobe(String message) {
        super(message);
    }

    public DuplikatKontaktiraneOsobe(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplikatKontaktiraneOsobe(Throwable cause) {
        super(cause);
    }

}
