package com.example.controller;

import com.example.model.Comensal;
import com.example.model.EstadoComensal;

public class RestauranteMonitor {
  private static final int AFORO_MAXIMO = 10;
  private int comensalesActuales;
  private boolean recepcionistaOcupado;

  public RestauranteMonitor() {
    this.comensalesActuales = 0;
    this.recepcionistaOcupado = false;
  }

  public synchronized boolean intentarEntrar(Comensal comensal) throws InterruptedException {
    // Esperar si el recepcionista está ocupado
    while (recepcionistaOcupado) {
      wait();
    }

    // Marcar al recepcionista como ocupado
    recepcionistaOcupado = true;

    // Verificar si hay espacio disponible
    if (comensalesActuales >= AFORO_MAXIMO) {
      recepcionistaOcupado = false;
      notifyAll();
      return false;
    }

    // Si hay espacio, incrementar el contador y permitir entrada
    comensalesActuales++;
    comensal.setAtendido(EstadoComensal.EN_RESTAURANTE);

    // Liberar al recepcionista
    recepcionistaOcupado = false;
    notifyAll();
    return true;
  }

  public synchronized void salirRestaurante() {
    comensalesActuales--;
    notifyAll();
  }

  public synchronized int getComensalesActuales() {
    return comensalesActuales;
  }
}
