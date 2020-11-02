package model;

import java.io.Serializable;

public class Card implements Serializable {
    private String suite;  // card suite
    private int value;  // card value

    // getters and setters for the private data members
    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public Card(String suite, int value) {
        this.suite = suite;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}