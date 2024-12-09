package com.example.controller;

import com.example.model.Comensal;
import com.example.model.Mesa;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RestaurantSimulator {
  private final Monitor monitor;
  private final Queue<Comensal> colaEspera;
  private SimulacionListener listener;

  public RestaurantSimulator(Monitor monitor) {
    this.monitor = monitor;
    this.colaEspera = new LinkedBlockingQueue<>();
  }

  public void setListener(SimulacionListener listener) {
    this.listener = listener;
  }

  public void llegadaComensal(Comensal comensal) {
    new Thread(() -> {
      Mesa mesa = monitor.asignarMesa();
      if (mesa != null) {
        if (listener != null) {
          listener.comensalAsignado(comensal, mesa);
        }

        // Simular tiempo en la mesa
        try {
          Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        monitor.liberarMesa(mesa);
        if (listener != null) {
          listener.mesaLiberada(comensal, mesa);
        }
      } else {
        colaEspera.add(comensal);
        if (listener != null) {
          listener.comensalEnEspera(comensal);
        }
      }
    }).start();
  }

  public interface SimulacionListener {
    void comensalAsignado(Comensal comensal, Mesa mesa);

    void mesaLiberada(Comensal comensal, Mesa mesa);

    void comensalEnEspera(Comensal comensal);
  }
}