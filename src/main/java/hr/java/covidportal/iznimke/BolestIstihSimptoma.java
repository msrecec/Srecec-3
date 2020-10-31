package main.java.hr.java.covidportal.iznimke;

public class BolestIstihSimptoma extends Exception {

    public BolestIstihSimptoma() {
        super("Unesena bolest ima iste simptome kao prethodno unesena bolest!");
    }

    public BolestIstihSimptoma(String message) {
        super(message);
    }

    public BolestIstihSimptoma(String message, Throwable cause) {
        super(message, cause);
    }

    public BolestIstihSimptoma(Throwable cause) {
        super(cause);
    }
}
