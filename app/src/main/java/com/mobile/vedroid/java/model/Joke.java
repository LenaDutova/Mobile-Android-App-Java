package com.mobile.vedroid.java.model;

public class Joke
        implements JokeAdapterModel{

    private final int id;
    private final boolean isSingle;
    private final String setup;
    private final String delivery;

    public Joke(int id, boolean isSingle, String setup, String delivery) {
        this.id = id;
        this.isSingle = isSingle;
        this.setup = setup;
        this.delivery = delivery;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isSingleJoke() {
        return isSingle;
    }

    @Override
    public String getJokeSetup() {
        return setup;
    }

    @Override
    public String getJokeDelivery() {
        return delivery;
    }

    public static class JokeFactory {
        private static boolean isSingle = false;
        private static int id = -1;
        private static String setup = null;
        private static String delivery = null;

        public static Joke create(){
            Joke joke = new Joke(id, isSingle, setup, delivery);
            id = -1;
            setup = null;
            delivery = null;
            return joke;
        }

        public static boolean parseToJokeAttr (String txt) {
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

        public static String valueOf (JokeAdapterModel model){
            StringBuilder txt = new StringBuilder();
            txt.append("id:");
            txt.append(model.getId());
            txt.append("\nsingle:");
            txt.append(model.isSingleJoke());
            txt.append("\nsetup:");
            txt.append(model.getJokeSetup());
            txt.append("\ndelivery:");
            txt.append(model.getJokeDelivery());
            txt.append("\n");
            txt.append(model.getClass().getSimpleName());
            txt.append("\n");

            return String.valueOf(txt);
        }
    }
}
