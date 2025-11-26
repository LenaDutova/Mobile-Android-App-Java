package com.mobile.vedroid.java.model;

/**
 * Adapter / Адаптер
 * Реализация паттерна преобразующего интерфейс класса(ов) к другому интерфейсу,
 * на который расчитан клиент
 */
public interface JokeAdapterModel {

    int getId();

    boolean isSingle();
    String getSetup();
    String getDelivery();

}
