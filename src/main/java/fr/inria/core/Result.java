package fr.inria.core;

public class Result {
    int status;
    String message;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() { return status == 0; }
}
