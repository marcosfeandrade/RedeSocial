package utils;

import java.io.Serializable;

public class StringsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String s1;
    private String s2;
    private String s3;

    public StringsDTO(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public StringsDTO(String s1, String s2, String s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }
}
