package com.mobile.vedroid.java.model;

import java.io.Serializable;

/**
 * Adapter / Адаптер
 * Реализация паттерна преобразующего интерфейс класса(ов) к другому интерфейсу,
 * на который расчитан клиент
 */
public interface JokeModelAdapter extends Serializable {

    int getId();

    boolean isSingleJoke();
    String getJokeSetup();
    String getJokeDelivery();

}
