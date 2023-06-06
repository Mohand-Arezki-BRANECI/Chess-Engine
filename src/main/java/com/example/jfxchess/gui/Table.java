package com.example.jfxchess.gui;

import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.board.MoveCreator;
import com.example.jfxchess.board.Tile;
import com.example.jfxchess.pieces.Piece;
import com.example.jfxchess.player.MoveBoard;
import com.example.jfxchess.player.MoveStatus;

import com.example.jfxchess.player.ai.Minimax;
import com.example.jfxchess.player.ai.Strategy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import javafx.scene.paint.Color;

public class Table extends Application {
    private final int tile_id = 0;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;

    private boolean isAiPlaying = false;
    private final List<Integer> legalMoves = new ArrayList<>();


    private void createBoard(GridPane board, Board[] chessBoard) throws MalformedURLException{

        System.out.println(chessBoard[0]);

            Strategy strategy = new Minimax(1);

        int tile_id = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane tile = new StackPane();
                tile.setPrefSize(70, 70);
                int finalTile_id = tile_id;

                tile.setOnMouseClicked(mouseEvent -> {

                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (sourceTile == null){
                            sourceTile = chessBoard[0].getTile(finalTile_id);

                                highLightLegalMoves(board, chessBoard, sourceTile);

                        }else{
                            destinationTile = chessBoard[0].getTile(finalTile_id);
                            System.out.println(destinationTile.getTileCoordinate());

                            final Move move = MoveCreator.createMove(chessBoard[0], sourceTile.getTileCoordinate(),destinationTile.getTileCoordinate());
                            final MoveBoard transition = chessBoard[0].currentPlayer().makeMove(move);
                            System.out.println(transition.getMoveStatus());
                            if (transition.getMoveStatus() == MoveStatus.DONE){
                                chessBoard[0] = transition.getToBoard();
                                System.out.println(chessBoard[0]);
                                drawBoard(board, chessBoard);
                                if (chessBoard[0].currentPlayer().inCheckMate()){
                                    System.out.println("Game Over " + chessBoard[0].currentPlayer() + "is in check mate");
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Game Over");
                                    alert.setHeaderText("The game has ended ! " + chessBoard[0].currentPlayer().toString() + " is in check mate");
                                    alert.setContentText("Thanks for playing!");
                                    alert.showAndWait();
                                }
                                if (isAiPlaying){
                                    Move calculateBestMove = strategy.execute(chessBoard[0]);
                                    MoveBoard aiMove = chessBoard[0].currentPlayer().makeMove(calculateBestMove);
                                    chessBoard[0] = aiMove.getToBoard();
                                    drawBoard(board, chessBoard);

                                    if (chessBoard[0].currentPlayer().inCheckMate()){
                                        System.out.println("Game Over " + chessBoard[0].currentPlayer() + "is in check mate");
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Game Over");
                                        alert.setHeaderText("The game has ended ! " + chessBoard[0].currentPlayer().toString() + " is in check mate");                                        alert.setContentText("Thanks for playing!");
                                        alert.showAndWait();
                                    }
                                }


                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }


                    }
                    else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        drawBoard(board, chessBoard);



                        System.out.println("Right btn pressed on tile " + finalTile_id);
                    }
                });

                tile.setAlignment(Pos.CENTER);
                String defaultPiecePath = "./src/main/java/com/example/jfxchess/images/";
                String pieceName = "";
                if(chessBoard[0].getTile(tile_id).getPiece() != null){
                    pieceName += chessBoard[0].getTile(tile_id).getPiece().getPieceColor().toString().substring(0, 1);
                    pieceName += chessBoard[0].getTile(tile_id).toString().toUpperCase();
                    pieceName += ".gif";
                    defaultPiecePath = defaultPiecePath + pieceName;
                    Image image = new Image(Path.of(defaultPiecePath).toUri().toURL().toString());
                    ImageView imageView = new ImageView(image);
                    tile.getChildren().add(imageView);

                }
                setTileColor(tile, row, col);
                board.add(tile, col, row);
                tile_id++;
            }
        }
        }

    private void setTileColor(StackPane tile,int row, int col){

        if((row + col) % 2 == 0){
            tile.setBackground(new Background(new BackgroundFill(Color.rgb(238,238,210), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else {
            tile.setBackground(new Background(new BackgroundFill(Color.rgb(118,150,86), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    private void drawBoard(GridPane board, Board[] chessBoard) {
        Strategy strategy = new Minimax(1);
        board.getChildren().clear();
        int tileId = 0;
        for (int row = 0; row <8; row++){
            for(int col = 0; col <8 ; col++){
                StackPane tile = new StackPane();
                tile.setPrefSize(70, 70);
                    int finalTileId = tileId;
                tile.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (sourceTile == null){
                            sourceTile = chessBoard[0].getTile(finalTileId );

                            highLightLegalMoves(board, chessBoard,sourceTile);

                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null){
                                sourceTile = null;
                            }
                        }else{
                            destinationTile = chessBoard[0].getTile(finalTileId );
                            final Move move = MoveCreator.createMove(chessBoard[0], sourceTile.getTileCoordinate(),destinationTile.getTileCoordinate());
                            final MoveBoard transition = chessBoard[0].currentPlayer().makeMove(move);
                            if (transition.getMoveStatus() == MoveStatus.DONE){
                                chessBoard[0] = transition.getToBoard();
                                System.out.println(chessBoard[0]);
                                drawBoard(board, chessBoard);
                                if (chessBoard[0].currentPlayer().inCheckMate()){
                                    System.out.println("Game Over " + chessBoard[0].currentPlayer() + "is in check mate");
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Game Over");
                                    alert.setHeaderText("The game has ended ! " + chessBoard[0].currentPlayer().toString() + " is in check mate");
                                    alert.setContentText("Thanks for playing!");
                                    alert.showAndWait();
                                }
                                if (isAiPlaying){
                                    Move calculateBestMove = strategy.execute(chessBoard[0]);
                                    MoveBoard aiMove = chessBoard[0].currentPlayer().makeMove(calculateBestMove);
                                    chessBoard[0] = aiMove.getToBoard();
                                    drawBoard(board, chessBoard);
                                    if (chessBoard[0].currentPlayer().inCheckMate()){
                                        System.out.println("Game Over " + chessBoard[0].currentPlayer() + "is in check mate");
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Game Over");
                                        alert.setHeaderText("The game has ended ! " + chessBoard[0].currentPlayer().toString() + " is in check mate");                                        alert.setContentText("Thanks for playing!");
                                        alert.showAndWait();
                                    }
                                }

                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;

                        }


                    }
                    else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        drawBoard(board, chessBoard);

                        System.out.println("Right btn pressed on tile " + finalTileId );
                    }
                });

                tile.setAlignment(Pos.CENTER);
                String defaultPiecePath = "./src/main/java/com/example/jfxchess/images/";
                String pieceName = "";

                if(chessBoard[0].getTile(tileId).getPiece() != null){
                    pieceName += chessBoard[0].getTile(tileId).getPiece().getPieceColor().toString().substring(0, 1);
                    pieceName += chessBoard[0].getTile(tileId).toString().toUpperCase();
                    pieceName += ".gif";
                    defaultPiecePath = defaultPiecePath + pieceName;
                    Image image = null;
                    try {
                        image = new Image(Path.of(defaultPiecePath).toUri().toURL().toString());
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    ImageView imageView = new ImageView(image);
                    tile.getChildren().add(imageView);

                }
                setTileColor(tile, row, col);
                board.add(tile, col, row);

                tileId++;
            }
        }
    }

    private void highLightLegalMoves(GridPane board, Board[] chessBoard, Tile sourceTile){
        for (Move move : sourceTile.getPiece().legalMoves(chessBoard[0])){
            int dest = move.getPieceDestination();
            legalMoves.add(dest);

            StackPane intermediaireTile = (StackPane) board.getChildren().get(dest);
            intermediaireTile.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1))));
        }
    }


    private void newGame(GridPane board) {
        board.getChildren().clear();
        Strategy strategy = new Minimax(1);

        final Board[] chessBoard = {Board.initialBoard()};

        int tileId = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane tile = new StackPane();
                tile.setPrefSize(70, 70);
                int finalTileId = tileId;
                tile.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard[0].getTile(finalTileId);

                            highLightLegalMoves(board, chessBoard, sourceTile);

                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard[0].getTile(finalTileId);
                            final Move move = MoveCreator.createMove(chessBoard[0], sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveBoard transition = chessBoard[0].currentPlayer().makeMove(move);
                            if (transition.getMoveStatus() == MoveStatus.DONE) {
                                chessBoard[0] = transition.getToBoard();
                                System.out.println(chessBoard[0]);
                                drawBoard(board, chessBoard);
                                if (chessBoard[0].currentPlayer().inCheckMate()){
                                    System.out.println("Game Over " + chessBoard[0].currentPlayer() + "is in check mate");
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Game Over");
                                    alert.setHeaderText("The game has ended ! " + chessBoard[0].currentPlayer().toString() + " is in check mate");                                    alert.setContentText("Thanks for playing!");
                                    alert.showAndWait();
                                }
                                if (isAiPlaying){
                                    Move calculateBestMove = strategy.execute(chessBoard[0]);
                                    MoveBoard aiMove = chessBoard[0].currentPlayer().makeMove(calculateBestMove);
                                    chessBoard[0] = aiMove.getToBoard();
                                    drawBoard(board, chessBoard);

                                    if (chessBoard[0].currentPlayer().inCheckMate()){
                                        System.out.println("Game Over " + chessBoard[0].currentPlayer() + "is in check mate");
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Game Over");
                                        alert.setHeaderText("The game has ended ! " + chessBoard[0].currentPlayer().toString() + " is in check mate");                                        alert.setContentText("Thanks for playing!");
                                        alert.showAndWait();
                                    }
                                }

                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;

                        }


                    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        drawBoard(board, chessBoard);

                        System.out.println("Right btn pressed on tile " + finalTileId);
                    }
                });

                tile.setAlignment(Pos.CENTER);
                String defaultPiecePath = "./src/main/java/com/example/jfxchess/images/";
                String pieceName = "";

                if (chessBoard[0].getTile(tileId).getPiece() != null) {
                    pieceName += chessBoard[0].getTile(tileId).getPiece().getPieceColor().toString().substring(0, 1);
                    pieceName += chessBoard[0].getTile(tileId).toString().toUpperCase();
                    pieceName += ".gif";
                    defaultPiecePath = defaultPiecePath + pieceName;
                    Image image = null;
                    try {
                        image = new Image(Path.of(defaultPiecePath).toUri().toURL().toString());
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    ImageView imageView = new ImageView(image);
                    tile.getChildren().add(imageView);

                }
                setTileColor(tile, row, col);
                board.add(tile, col, row);

                tileId++;
            }
        }
    }

    private void exitGame(MenuItem menuItem){
        menuItem.setOnAction(actionEvent ->
                Platform.exit());
    }


    @Override
    public void start(Stage stage) throws IOException {


        // Scenes
        Scene landingPageScene, scene, pauseGameScene;
        /*The code below is a part of the chess game (main thing) it is here because landingPage uses
            scene to switch from landingPage to the main chess game
         */
        BorderPane root = new BorderPane();
        scene = new Scene(root, 675, 600);

        // landingPageScene
        BorderPane layoutOne = new BorderPane();
        String bgImagePath = "./src/main/java/com/example/jfxchess/images/chessBg.jpg";
        Image img;
        try {
            img = new Image(Path.of(bgImagePath).toUri().toURL().toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(650);
        imageView.setFitWidth(624);
        layoutOne.getChildren().add(imageView);
        Label wellcomeText = new Label("Wellcome to our chess game ! Click on the Start Button to start playing");
        wellcomeText.setTextFill(Color.WHITE);
        wellcomeText.setFont(new Font(15));
        Button startButton = new Button("Start");
        startButton.setPrefWidth(100);
        startButton.setPrefHeight(25);
        startButton.setOnAction(actionEvent -> stage.setScene(scene));
        layoutOne.setTop(wellcomeText);
        layoutOne.setAlignment(wellcomeText, Pos.CENTER);
        layoutOne.setCenter(startButton);

        landingPageScene = new Scene(layoutOne, 624, 600);

        // PauseGameScene

        BorderPane layoutTwo = new BorderPane();
        String backgroundImagePath = "./src/main/java/com/example/jfxchess/images/bg.png";
        Image image;
        try {
            image = new Image(Path.of(backgroundImagePath).toUri().toURL().toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ImageView imageV = new ImageView(image);
        imageV.setFitHeight(650);
        imageV.setFitWidth(624);
        layoutTwo.getChildren().add(imageV);
        Label pauseText = new Label("You paused the game, to continue click on the Resume Game Button");
        pauseText.setTextFill(Color.WHITE);
        pauseText.setFont(new Font(15));
        Button resumeButton = new Button("Resume Game");
        resumeButton.setPrefWidth(100);
        resumeButton.setPrefHeight(25);
        resumeButton.setOnAction(actionEvent -> stage.setScene(scene));
        layoutTwo.setTop(pauseText);
        layoutTwo.setAlignment(pauseText, Pos.CENTER);
        layoutTwo.setCenter(resumeButton);

        pauseGameScene = new Scene(layoutTwo, 624, 600);

        // The code below is for the chess game

        stage.setTitle("Java Chess ");
            // Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(newGame, exit);

        Menu optionMenu = new Menu("Options");
        MenuItem pause = new MenuItem("Pause");
        pause.setOnAction(actionEvent -> stage.setScene(pauseGameScene));

        menuBar.getMenus().addAll(fileMenu, optionMenu);
        root.setTop(menuBar);
        exitGame(exit);

        // This is the 8x8 board
        GridPane board = new GridPane();
        board.setGridLinesVisible(true);

        newGame.setOnAction(actionEvent -> newGame(board));

        // left vbox, it will be used to display the captured pieces in the future
        VBox leftVBox = new VBox();
        leftVBox.setPrefSize(50,540);
        // right vbox, it will be used to diplay the moves made by each player
        VBox rightVBox = new VBox();

        rightVBox.setPrefSize(75,540);

            // This is the chess board that is displayed in the terminal
        final Board[] chessBoard = {Board.initialBoard()};
        createBoard(board, chessBoard);





        MenuItem playerVsAi = new MenuItem("Player Vs Ai");
        playerVsAi.setOnAction(actionEvent -> { newGame(board);
            isAiPlaying = true; });


        optionMenu.getItems().addAll(pause, playerVsAi);
        root.setCenter(board);
        root.setLeft(leftVBox);
        root.setRight(rightVBox);
        stage.setScene(landingPageScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
