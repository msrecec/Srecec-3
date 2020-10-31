package main.java.hr.java.covidportal.iznimke;

public class DuplikatKontaktiraneOsobe extends Exception {
    public DuplikatKontaktiraneOsobe() {
        super("Dogodila se pogreška u radu programa!");
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
