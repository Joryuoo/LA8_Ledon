package com.example.graphsledon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HelloController {
    public AnchorPane apPane;
    @FXML
    private Button btnSave;
    List<Vertex> vertices;



    public void initialize(){
        apPane.getChildren().clear();
        vertices = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("vertices.txt"))){
            vertices = (ArrayList<Vertex>) ois.readObject();

            for(Vertex v: vertices){
                StackPane sp = new StackPane();
                sp.setOnMouseClicked(this::onVertexClicked);
                sp.setLayoutX(v.x);
                sp.setLayoutY(v.y);

                Circle c = new Circle();
                c.setRadius(40);
                c.setFill(Paint.valueOf("#fcbe03"));

                sp.getChildren().add(c);

                Text text = new Text();
                text.setText(v.name);

                sp.getChildren().add(text);

                apPane.getChildren().add(sp);
            }

            System.out.println("Loaded");
        } catch (IOException | ClassNotFoundException e){
            System.err.println(e.getClass());
        }

    }

    @FXML
    public void onSaveButtonClicked(ActionEvent event) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("vertices.txt"))){
            for(Node n : apPane.getChildren()){
                if(n instanceof StackPane){
                    Text text = (Text) ((StackPane) n).getChildren().get(1);
                    vertices.add(new Vertex(text.getText(),n.getLayoutX(), n.getLayoutY()));
                }
            }

            oos.writeObject(vertices);
            System.out.println("Saved!");

        }catch(IOException e){
            System.err.println(e.getClass());
        }
    }

    @FXML
    public void onVertexClicked(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.showAndWait().ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                StackPane sp = (StackPane) event.getSource();
                Text text = (Text) sp.getChildren().get(1);
                text.setText(s);

            }
        });
    }


}