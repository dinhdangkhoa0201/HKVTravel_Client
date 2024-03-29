package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main_Client extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new FXMLLoader(getClass().getResource("/gui/DangNhap.fxml")).load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			new Services();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
//		File file = new File("src/img/man.png");
//		try {
//			if(file.exists()) {
//				System.out.println("Exists");
//				System.out.println(file.toURI());
//			} else {
//				System.out.println("Dell co gi");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
