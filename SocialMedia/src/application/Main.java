package application;
	
//This class launches the application
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application{	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Mini Twitter");
		
		Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml")); //connected with fmxl file
		Scene scene = new Scene(root, 1280, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}