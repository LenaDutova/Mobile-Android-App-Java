package com.mobile.vedroid.java.model;

import com.mobile.vedroid.java.model.entityes.Joke;

public class JokeFactory {
    private static boolean isSingle = false;
    private static int id = -1;
    private static String setup = null;
    private static String delivery = null;

    public static Joke create() {
        Joke joke = new Joke(id, isSingle, setup, delivery);
        id = -1;
        setup = null;
        delivery = null;
        return joke;
    }

    public static boolean parseToJokeAttr(String txt) {
        if (txt.startsWith("id:")) {
            id = Integer.parseInt(txt.replaceFirst("id:", ""));
            return true;
        }
        if (txt.startsWith("single:")) {
            isSingle = Boolean.parseBoolean(txt.replaceFirst("single:", ""));
            return true;
        }
        if (txt.startsWith("setup:")) {
            setup = txt.replaceFirst("setup:", "");
            return true;
        }
        if (txt.startsWith("delivery:")) {
            delivery = txt.replaceFirst("delivery:", "");
            return true;
        }
        return false;
    }

    public static String valueOf(JokeAdapterModel model) {
        StringBuilder txt = new StringBuilder();
        txt.append("id:");
        txt.append(model.getId());
        txt.append("\nsingle:");
        txt.append(model.isSingle());
        txt.append("\nsetup:");
        txt.append(model.getSetup());
        txt.append("\ndelivery:");
        txt.append(model.getDelivery());
        txt.append("\n");
        txt.append(model.getClass().getSimpleName());
        txt.append("\n");

        return String.valueOf(txt);
    }
}
