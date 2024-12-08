package com.example.controller;

import com.example.model.Comensal;
import com.example.model.Mesa;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RestaurantSimulator {
  private final Monitor monitor;
  private final Queue<Comensal> colaEspera;

  public RestaurantSimulator(Monitor monitor) {
    this.monitor = monitor;
    this.colaEspera = new LinkedBlockingQueue<>();
  }

  public void llegadaComensal(Comensal comensal) {
    new Thread(() -> {
      Mesa mesa = monitor.asignarMesa();
      if (mesa != null) {
        System.out.println("Comensal " + comensal.getId() + " asignado a una mesa.");
        // Simular tiempo en la mesa
        try {
          Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        monitor.liberarMesa(mesa);
        System.out.println("Comensal " + comensal.getId() + " dejó la mesa.");
      } else {
        System.out.println("Comensal " + comensal.getId() + " esperando.");
        colaEspera.add(comensal);
      }
    }).start();
  }
}
