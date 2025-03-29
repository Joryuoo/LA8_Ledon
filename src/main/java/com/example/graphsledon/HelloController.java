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
import java.util.Random;
import java.util.function.Consumer;

public class HelloController {
    public AnchorPane apPane;
    public Button btnAdd;
    public Button btnClear;
    @FXML
    private Button btnSave;
    List<Vertex> vertices;



    public void initialize(){
        apPane.getChildren().clear();
        vertices = new ArrayList<>();
        System.out.println("init");
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("vertices.txt"))){
            vertices = (ArrayList<Vertex>) ois.readObject();
            System.out.println("ois");

            for(Vertex v: vertices){
                StackPane sp = new StackPane();
                sp.setOnMouseClicked(this::onVertexClicked);
                sp.setLayoutX(v.x);
                sp.setLayoutY(v.y);

                Circle c = new Circle();
                c.setRadius(40);

                c.setFill(Paint.valueOf(v.color));

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
                    Circle c = (Circle) ((StackPane) n).getChildren().get(0);
                    String color = c.getFill().toString();
                    vertices.add(new Vertex(text.getText(),n.getLayoutX(), n.getLayoutY(), color));
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


    public void onAddButtonClick(ActionEvent actionEvent) {
        Random random = new Random();
        double height = 561.0;
        double width = 600.0;
        StackPane sp = new StackPane();
        sp.setOnMouseClicked(this::onVertexClicked);
        Circle c = new Circle();
        c.setRadius(40);
//                              blue        //gray   //green     //red      //yellow   //skyblue
        String[] randomColor = {"#186cf8", "#6b757d", "#198855", "#dc3544", "#ffc232", "#11caf0"};
        String randColor = randomColor[random.nextInt(randomColor.length)];
        c.setFill(Paint.valueOf(randColor));
        sp.getChildren().add(c);

        double x = random.nextDouble() * (width - 80); 
        double y = random.nextDouble() * (height - 80);

        String abcXD = "abcdefghijklmnopqrstuvwxyz";

        char randChar = abcXD.charAt(random.nextInt(abcXD.length()));

        Text text = new Text();
        text.setText("" + randChar);

        sp.setLayoutX(x);
        sp.setLayoutY(y);

        sp.getChildren().add(text);
        apPane.getChildren().add(sp);

        vertices.add(new Vertex("" + randChar, x, y, randColor));
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        if(!vertices.isEmpty()){
            vertices.clear();
            apPane.getChildren().clear();
        }
    }

}