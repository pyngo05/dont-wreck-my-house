package learn.dontwreckmyhouse.domain;

public class Result<T> extends Response {

    private T payload;

    public Result(T payload) {
        this.payload = payload;
    }

    public Result(String errorMessage) {
        addErrorMessage(errorMessage);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
