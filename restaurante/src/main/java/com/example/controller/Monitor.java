package com.example.controller;

import com.example.model.Mesa;

import java.util.List;

public class Monitor {
  private final List<Mesa> mesas;

  public Monitor(List<Mesa> mesas) {
    this.mesas = mesas;
  }

  public synchronized Mesa asignarMesa() {
    for (Mesa mesa : mesas) {
      if (!mesa.isOcupada()) {
        mesa.ocupar();
        return mesa;
      }
    }
    return null;
  }

  public synchronized void liberarMesa(Mesa mesa) {
    mesa.liberar();
  }
}

// tienes que asignar la mesa desde Animacion