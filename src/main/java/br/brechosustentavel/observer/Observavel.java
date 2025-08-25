/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thiag
 */
public class Observavel {
    private static Observavel instance;
    private final List<Observador> observers = new ArrayList<>();

    private Observavel() {}

    public static Observavel getInstance() {
        if (instance == null) {
            instance = new Observavel();
        }
        return instance;
    }

    public void addObserver(Observador observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observador observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observador observer : new ArrayList<>(observers)) {
            observer.atualizar();
        }
    }
}
