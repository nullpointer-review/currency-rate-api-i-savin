package ru.newpointer.currency;

/**
 * Created by isavin on 27.09.15.
 */
public class Fault implements Response {
    private String errorDescription;
    private String errorCause;

    public Fault(String errorDescription, String errorCause) {
        this.errorDescription = errorDescription;
        this.errorCause = errorCause;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }
}
