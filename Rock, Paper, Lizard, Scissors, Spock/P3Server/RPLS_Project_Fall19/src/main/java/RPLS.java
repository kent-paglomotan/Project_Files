import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RPLS extends Application {
	private Integer port;
	int choosePortNum;
	ListView<String> gameInfoLV;
	Button serverOn,serverOFF, choosePort, portChosen;
	TextField portNum;
	Scene startScene, infoScene;
	Server getServerConnection;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		// BORDER PANES
		BorderPane centerStage1 = new BorderPane();
		BorderPane centerStage2 = new BorderPane();
		BorderPane pane = new BorderPane();
		BorderPane pane2 = new BorderPane();
		BorderPane pane3 = new BorderPane();
		BorderPane pane4 = new BorderPane();

		//LABELS
		Label title1 = new Label("Enter A Valid Port");
		title1.setStyle("-fx-font-size: 30;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");
		centerStage1.setCenter(title1);
		centerStage1.setPadding(new Insets(80,0,0,0));

		Label title3 = new Label("Server and Game Information");
		pane2.setCenter(title3);
		title3.setStyle("-fx-font-size: 20;\n" +
				"    -fx-font-family: \"Segoe UI Semibold\";\n" +
				"    -fx-text-fill: white;\n" +
				"    -fx-opacity: 0.6;");

		Label availablePorts = new Label("Ports: 5000, 2345, 3334, 4445, 7001 or others");
		pane4.setCenter(availablePorts);
		pane4.setPadding(new Insets(20,0,0,0));

		// Text fields
		portNum = new TextField("5555");
		portNum.setStyle("-fx-pref-width: 150px;" + "-fx-pref-height: 40px;");

		// BUTTONS
		choosePort = new Button("Choose Here");
		choosePort.setStyle("-fx-text-fill: white;" + "-fx-background-color: linear-gradient(#61a2b1, #2A5058);"
                + "-fx-font-family: \"Arial Narrow\";" + "-fx-font-weight: bold;"
                + "-fx-pref-width: 200px;" + "-fx-pref-height: 40px;" + "-fx-font-size: 25;");
		//source: https://docs.oracle.com/javase/8/javafx/get-started-tutorial/css.htm
		centerStage2.setCenter(choosePort);

		// this button should get the port number and send it to the server
		portChosen = new Button("Select this Port");

		// this button should turn on the server after the port number is chosen
		serverOn = new Button("Server On");

		serverOn.setStyle("-fx-background-color: \n" +
				"        linear-gradient(#ffd65b, #e68400),\n" +
				"        linear-gradient(#ffef84, #f2ba44),\n" +
				"        linear-gradient(#ffea6a, #efaa22),\n" +
				"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
				"        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
				"    -fx-background-radius: 30;\n" +
				"    -fx-background-insets: 0,1,2,3,0;\n" +
				"    -fx-text-fill: #654b00;\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-font-size: 14px;\n" +
				"    -fx-padding: 10 20 10 20;");

		serverOFF = new Button("Server OFF");
		serverOFF.setStyle("-fx-background-color: \n" +
				"        linear-gradient(#ffd65b, #e68400),\n" +
				"        linear-gradient(#ffef84, #f2ba44),\n" +
				"        linear-gradient(#ffea6a, #efaa22),\n" +
				"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
				"        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
				"    -fx-background-radius: 30;\n" +
				"    -fx-background-insets: 0,1,2,3,0;\n" +
				"    -fx-text-fill: #654b00;\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-font-size: 14px;\n" +
				"    -fx-padding: 10 20 10 20;");

        //this list view is where the history
		gameInfoLV = new ListView<String>();
		pane3.setCenter(gameInfoLV);
		pane3.setPadding(new Insets(10,40,40,40));

		// HBOXES and VBOXES
		// Hbox text field of the port number
		HBox serverON_OFF = new HBox(10);
		serverON_OFF.getChildren().addAll(serverOn,serverOFF);
		serverON_OFF.setMargin(serverOn,new Insets(10,0,0,200));
		serverON_OFF.setMargin(serverOFF,new Insets(10,0,0,0));

		HBox port_hbox = new HBox(10);
		port_hbox.getChildren().addAll(portNum);

		VBox nextGUI = new VBox(20,serverON_OFF,pane2, pane3);
		pane.setPadding(new Insets(20,0,10,0));
		nextGUI.setStyle("-fx-background-color:linear-gradient(#232323,#3f87a6);");

		VBox startScreen = new VBox(30,centerStage1,port_hbox,centerStage2);
		startScreen.setMargin(port_hbox, new Insets(0,0,0,175));
        startScreen.setStyle("-fx-background-image: url(\"connect.jpg\");" + "-fx-background-size:cover,auto;");
        // source: https://jojorabbitjavafxblog.wordpress.com/tag/fx-background-image/

		choosePort.setOnAction(e->{
			// get the socket with the right port
			// send a message on the list view which port is connected
			// store the text from the port num
			// disable the off button
			serverOFF.setDisable(true);
			try {
				choosePortNum = Integer.parseInt(portNum.getText());
			}
			catch (Exception ex) {
				System.out.println("invalid port!");
				return;
			}
			this.port = choosePortNum;
			// put what ever string is produced into the list view
			getServerConnection = new Server(this.port,
					data -> {
						Platform.runLater(()->{
							gameInfoLV.getItems().add(data.toString());
						});
						//add here if needed
					});

			// transition to the information scene
			primaryStage.setScene(infoScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");

		});

		serverOn.setOnAction(e->{
			getServerConnection.getServerOn();
			serverOFF.setDisable(false);

		});

		serverOFF.setOnAction(event -> {
			// turn off the server
			try {
				getServerConnection.turnOffServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// exit the program
			System.exit(0);
		});

		// SCREENS
		infoScene = new Scene(nextGUI, 600,500);
		startScene = new Scene(startScreen,500,300);
		primaryStage.setTitle("Connecting to...");
		primaryStage.setScene(startScene);
		primaryStage.show();

	}

}
