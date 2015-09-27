package ru.newpointer.currency;

/**
 * Имплементация ошибки в работе сервиса
 */
public class Fault implements IResponse {
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
