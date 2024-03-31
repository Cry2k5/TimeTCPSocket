package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApp extends Application {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Label clockLabel;

    @Override
    public void start(Stage primaryStage) {
    	
    	
        StackPane root = new StackPane();
        clockLabel = new Label();
        clockLabel.setStyle("-fx-font-size: 24;");
        root.getChildren().add(clockLabel);

        primaryStage.setScene(new Scene(root, 200, 100));
        primaryStage.setTitle("Clock");
        primaryStage.show();

        // Start clock animation
        startClockAnimation();

        // Connect to server and request time
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                while (true) {
                    out.println("time");
                    String response = in.readLine();
                    updateClock(response);
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        
    }

    private void startClockAnimation() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Platform.runLater(() -> {
                    // Update clock animation
                });
            }
        }.start();
    }

    private void updateClock(String time) {
        Platform.runLater(() -> {
            clockLabel.setText(time);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
