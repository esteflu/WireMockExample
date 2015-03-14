package com.lundberg.http;

import org.springframework.stereotype.Component;

import javax.inject.Named;

/**
 * Created by stefanlundberg on 15-03-14.
 */

public class HttpResult {

    private boolean successful;
    private final static int OK = 200;
    private final static int BAD_REQUEST = 400;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void checkStatusCode(int statusCode) {

        switch(statusCode) {
            case OK:
                this.setSuccessful(true);
                break;
            case BAD_REQUEST:
                this.setSuccessful(false);
                break;
        }
    }
}
