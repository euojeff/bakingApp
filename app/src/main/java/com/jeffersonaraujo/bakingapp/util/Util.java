package com.jeffersonaraujo.bakingapp.util;

import java.io.InputStream;
import java.util.Scanner;

public class Util {

    public static String inputStreamToString(InputStream is ){
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
