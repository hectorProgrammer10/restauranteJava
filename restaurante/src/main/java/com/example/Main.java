package com.example;

import com.example.controller.Monitor;
import com.example.controller.RestaurantSimulator;
import com.example.model.Comensal;
import com.example.model.Mesa;
import com.example.view.Animacion;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    // Crear las mesas
    List<Mesa> mesas = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      mesas.add(new Mesa(i));
    }

    // Crear monitor y simulador
    Monitor monitor = new Monitor(mesas);
    RestaurantSimulator simulator = new RestaurantSimulator(monitor);

    // Inicializar la vista
    Animacion animacion = new Animacion(simulator);

    // Lanzar la aplicación
    animacion.launch(Animacion.class, args);
  }
}
