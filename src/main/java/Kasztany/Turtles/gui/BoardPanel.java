package Kasztany.Turtles.gui;


import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.model.Vector;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BoardPanel {
    private Board board;
    private VBox boardBox=new VBox();
    private GridPane gridPane=new GridPane();
    private HBox playersBox=new HBox();

    public BoardPanel(Board board) {
        this.board = board;
        String boardLayout = """
                -fx-padding: 10;
                """;

        boardBox.getChildren().addAll(playersBox,gridPane);
        boardBox.setPrefSize(1000, 1000);
        boardBox.setStyle(boardLayout);
        drawHeader();
        drawBoard();

    }

    private void drawBoard(){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPrefSize(800,800);
        gridPane.setStyle("""
                -fx-border-color: red;
                -fx-border-width: 1;
                -fx-border-style: solid;
                """);

        Vector maxVector=board.getMaxVetor();
        System.out.println(maxVector.toString());


        for (int x = 0; x <= maxVector.getX(); x++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(50,800/(board.getMaxVetor().getX()+1),200));
        }
        for (int y = 0; y <= maxVector.getY(); y++) {
            gridPane.getRowConstraints().add(new RowConstraints(50,800/(board.getMaxVetor().getX()+1),200));
        }
        for (Field field: board.getFields()) {
                GridPane fieldBox=new GridPane();
                fieldBox.setStyle("""
                -fx-border-color: #AAAAAA;
                -fx-border-width: 1;
                -fx-border-style: solid;
                -fx-border-insets: 1;
                """);
                if(field.getBottomTurtle().isPresent()){
                    Turtle turtle=field.getBottomTurtle().get();
                    ArrayList<Turtle> turtlesOnFiled=new ArrayList<>();
                    turtlesOnFiled.add(turtle);
                    while(turtle.getTurtleOnBack().isPresent()){
                        turtle=turtle.getTurtleOnBack().get();
                        turtlesOnFiled.add(turtle);
                    }
                    double size=Math.max(800/(board.getMaxVetor().getX()+1),50);
                    System.out.println(800/(board.getMaxVetor().getX()+1));
                    drawTurtlesInField(size/(turtlesOnFiled.size()-1),fieldBox,turtlesOnFiled);
                }

                gridPane.add(fieldBox,field.getPosition().getX(),maxVector.getY()-field.getPosition().getY(),1,1);
        }
    }
    private void drawTurtlesInField(double size,GridPane field,ArrayList<Turtle> turtlesOnFiled){
        for (int i = 0; i <turtlesOnFiled.size() ; i++) {
            field.add(drawTurtle(size,turtlesOnFiled.get(i ).getColor()),0,turtlesOnFiled.size()-i,1,1);

        }
        field.setAlignment(Pos.CENTER);

    }
    private void drawHeader(){
        playersBox.setPrefSize(800,50);
        for(Turtle turtle:board.getTurtles()) {
            Text turtleText = new Text(turtle.getName());

            HBox turtleDrawing=drawTurtle(50,turtle.getColor());
            turtleDrawing.setOnMouseClicked((e) -> {
                System.out.println("Choose "+turtle.getColor());
            });
            VBox turtleBox=new VBox(turtleText,turtleDrawing);

            playersBox.setSpacing(800/board.getTurtles().size()-50);
            playersBox.getChildren().add(turtleBox);
            playersBox.setAlignment(Pos.CENTER);
            playersBox.setStyle("""
                -fx-border-color: blue;
                -fx-border-width: 1;
                -fx-border-style: solid;
                """);
        }
    }
    private HBox drawTurtle(double size, String color){
        HBox shell=new HBox();
        shell.setMinSize(size/2,size/2);
        shell.setStyle("-fx-background-color: #"+ color);
        HBox headBox=new HBox();
        headBox.setMinSize(size/2,size/2);
        headBox.setAlignment(Pos.BOTTOM_LEFT);

        HBox head=new HBox();
        head.setMaxSize(size/2,size/3);
        head.setMinSize(size/2,size/3);
        head.setStyle(
                "-fx-border-color: black;\n"+
                "-fx-border-width: 1;\n"+
                "-fx-border-style: solid;\n"+
                "-fx-padding:"+ size/20+";\n"
                );
        head.setAlignment(Pos.TOP_RIGHT);
        headBox.getChildren().add(head);
        HBox eye=new HBox();
        eye.setMaxSize(4,4);
        eye.setMinSize(4,4);
        eye.setStyle("""
                -fx-background-color: black;
                """);

        head.getChildren().add(eye);




        HBox turtle=new HBox(shell,headBox);
        turtle.setPrefSize(size,size/2);

        return turtle;
    }


    public VBox getBoard(){
        return boardBox;
    }
}
