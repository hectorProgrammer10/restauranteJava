package com.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.example.controller.RestaurantSimulator;
import com.example.model.Comensal;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Animacion extends GameApplication {

  private Entity cocinero;

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setWidth(1520);
    settings.setHeight(1010);
    settings.setTitle("Juego con Interacciones");
  }

  @Override
  protected void initGame() {
    // Cargar la imagen del fondo
    Texture background = getAssetLoader().loadTexture("restaurante.png");
    background.setScaleX(1.3);
    background.setScaleY(1.3);

    // Crear la entidad del fondo
    entityBuilder()
        .at(0, 0)
        .view(background)
        .buildAndAttach();

    // Crear y configurar al cocinero
    Texture cocineroTexture = getAssetLoader().loadTexture("cocinero.png");
    cocineroTexture.setScaleX(0.09);
    cocineroTexture.setScaleY(0.09);

    cocinero = entityBuilder()
        .at(1000, 90)
        .view(cocineroTexture)
        .buildAndAttach();

    // Añadir mesas
    addTable(100, 280);
    addTable(200, 380);
    addTable(300, 480);

    // Añadir otras entidades si lo deseas
  }

  @Override
  protected void initInput() {
    // Agregar acción para mover al cocinero hacia la derecha
    getInput().addAction(new UserAction("Mover Derecha") {
      @Override
      protected void onAction() {
        cocinero.translateX(5); // Mueve al cocinero 5 píxeles hacia la derecha
      }
    }, KeyCode.RIGHT);

    // Agregar acción para mover al cocinero hacia la izquierda
    getInput().addAction(new UserAction("Mover Izquierda") {
      @Override
      protected void onAction() {
        cocinero.translateX(-5); // Mueve al cocinero 5 píxeles hacia la izquierda
      }
    }, KeyCode.LEFT);

  }

  private void addTable(double x, double y) {
    Texture tableTexture = getAssetLoader().loadTexture("mesa.png");
    tableTexture.setScaleX(0.1);
    tableTexture.setScaleY(0.1);

    entityBuilder()
        .at(x, y)
        .view(tableTexture)
        .buildAndAttach();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
