import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class RPLS extends Application {

	private Integer port;
	private String ip;
	static final int picHeight = 150;
	static final int picWidth = 150;
	int choosePortNum;
	String chooseIp;
	Client getClientConnection;
	Button connect,homeScreen, playAgain,quit,playerPickBtn, rockBtn, paperBtn, scissorsBtn, lizardBtn,spockBtn;
	TextField enterPortNum, enterIP;
	Label portNum, ipAddress, p1PointsLbl, p2PointsLbl, enterLbl, playerPickLbl, roundNumber,roundTxt, p1PointCount, p2PointCount;
	Scene startScene, introScene, gameScene, playerPickScene;
	ListView<String> gameInfo;
	Image rockImg, paperImg, scissorsImg, lizardImg, spockImg;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Connecting to...");

		gameInfo = new ListView<String>();

		// BORDER PANES
		BorderPane pane = new BorderPane();
		BorderPane pane2 = new BorderPane();
		BorderPane pane3 = new BorderPane();
		BorderPane pane4 = new BorderPane();
		BorderPane pane5 = new BorderPane();
		BorderPane pane6 = new BorderPane();
		BorderPane pane7 = new BorderPane();
		BorderPane pane8 = new BorderPane();


		// LABELS
		enterLbl = new Label("Enter");
		enterLbl.setStyle("-fx-font-size: 22;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");
		pane.setCenter(enterLbl);
		pane.setPadding(new Insets(70,0,0,0));

		portNum = new Label("Port Number:");
		portNum.setStyle("-fx-font-size: 18;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");
		ipAddress = new Label("IP:");
		ipAddress.setStyle("-fx-font-size: 18;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");
		p1PointsLbl = new Label("Player 1 Points:");
		p1PointsLbl.setStyle("-fx-font-size: 22;" + "-fx-text-fill: lime;" + "-fx-font-weight:bold;");
		p2PointsLbl = new Label("Player 2 Points:");
		p2PointsLbl.setStyle("-fx-font-size: 22;" + "-fx-text-fill: orange;" + "-fx-font-weight:bold;");
		p1PointCount = new Label("0");
		p1PointCount.setStyle("-fx-font-size: 22;" + "-fx-text-fill: yellow;" + "-fx-font-weight:bold;");
		p2PointCount = new Label("0");
		p2PointCount.setStyle("-fx-font-size: 22;" + "-fx-text-fill: yellow;" + "-fx-font-weight:bold;");
		playerPickLbl = new Label("Player 1: Take Your Pick");
		playerPickLbl.setStyle("-fx-font-size: 25;" + "-fx-text-fill: DeepSkyBlue;" + "-fx-font-weight:bold;");
		pane8.setCenter(playerPickLbl);

		roundTxt = new Label("Round");
		roundTxt.setStyle("-fx-font-family: \"Helvetica\";\n" +
				"    -fx-font-size: 25px;\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-text-fill: white;\n");

		roundNumber = new Label(" 1");
		roundNumber.setStyle("-fx-font-family: \"Helvetica\";\n" +
				"    -fx-font-size: 25px;\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-text-fill: white;\n");
		pane5.setCenter(roundNumber);
		pane5.setPadding(new Insets(20,0,0,0));

		// TEXT FIELDS
		enterPortNum = new TextField("5555");
		enterIP = new TextField("127.0.0.1");

		// BUTTONS
		//source: http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
		connect = new Button("Connect");
		connect.setStyle("-fx-text-fill: white;" + "-fx-background-color: linear-gradient(#61a2b1, #2A5058);"
				+ "-fx-font-family: \"Arial Narrow\";" + "-fx-font-weight: bold;"
				+ "-fx-pref-width: 150px;" + "-fx-pref-height: 40px;" + "-fx-font-size: 18;");
		pane2.setCenter(connect);
		pane2.setPadding(new Insets(30,0,0,0));

		homeScreen = new Button("HOME SCREEN");
		homeScreen.setStyle("-fx-background-color: \n" +
				"        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n" +
				"        linear-gradient(#020b02, #3a3a3a),\n" +
				"        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n" +
				"        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n" +
				"        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\n" +
				"    -fx-background-insets: 0,1,4,5,6;\n" +
				"    -fx-background-radius: 9,8,5,4,3;\n" +
				"    -fx-padding: 15 30 15 30;\n" +
				"    -fx-font-family: \"Helvetica\";\n" +
				"    -fx-font-size: 18px;\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-text-fill: white;\n" +
				"    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
		pane7.setCenter(homeScreen);

		playAgain = new Button("Play");
		playAgain.setStyle("-fx-background-color: \n" +
				"        #000000,\n" +
				"        linear-gradient(#7ebcea, #2f4b8f),\n" +
				"        linear-gradient(#426ab7, #263e75),\n" +
				"        linear-gradient(#395cab, #223768);" + "-fx-background-radius: 30;"
				+ "-fx-background-radius: 30;" + "-fx-text-fill: white;" + "-fx-pref-width: 130px;" + "-fx-font-size: 20;");

		quit = new Button("QUIT");
		quit.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" + "-fx-background-radius: 30;"
				+ "-fx-background-radius: 30;" + "-fx-text-fill: white;" + "-fx-pref-width: 130px;" + "-fx-font-size: 20;");

		//source: http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
		playerPickBtn = new Button("Player Pick Here");
		playerPickBtn = new Button("Player Pick Here");
		playerPickBtn.setStyle("-fx-background-color: \n" +
				"        #000000,\n" +
				"        linear-gradient(#7ebcea, #2f4b8f),\n" +
				"        linear-gradient(#426ab7, #263e75),\n" +
				"        linear-gradient(#395cab, #223768);\n" +
				"    -fx-background-insets: 0,1,2,3;\n" +
				"    -fx-background-radius: 3,2,2,2;\n" +
				"    -fx-padding: 12 30 12 30;\n" +
				"    -fx-text-fill: white;\n" +
				"    -fx-font-size: 20px;");
		pane6.setCenter(playerPickBtn);

		// LIST VIEW
		gameInfo = new ListView<String>();

		// IMAGES
		rockImg = new Image("rock.jpg");
		ImageView rock_view = new ImageView(rockImg);
		rock_view.setFitHeight(picHeight);
		rock_view.setFitWidth(picWidth);
		rock_view.setPreserveRatio(true);

		paperImg = new Image("paper.png");
		ImageView paper_view = new ImageView(paperImg);
		paper_view.setFitHeight(picHeight);
		paper_view.setFitWidth(picWidth);
		paper_view.setPreserveRatio(true);

		scissorsImg = new Image("scissors.png");
		ImageView scissors_view = new ImageView(scissorsImg);
		scissors_view.setFitHeight(picHeight);
		scissors_view.setFitWidth(picWidth);
		scissors_view.setPreserveRatio(true);

		lizardImg = new Image("lizard.jpg");
		ImageView lizard_view = new ImageView(lizardImg);
		lizard_view.setFitHeight(picHeight);
		lizard_view.setFitWidth(picWidth);
		lizard_view.setPreserveRatio(true);

		spockImg = new Image("spock.png");
		ImageView spock_view = new ImageView(spockImg);
		spock_view.setFitHeight(picHeight);
		spock_view.setFitWidth(picWidth);
		spock_view.setPreserveRatio(true);

		// IMAGE BUTTONS
		rockBtn = new Button();
		rockBtn.setGraphic(rock_view);
		rockBtn.setOnAction(e->{
			getPlayerPick(0);
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});

		paperBtn = new Button();
		paperBtn.setOnAction(e->{
			getPlayerPick(1);
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});
		paperBtn.setGraphic(paper_view);

		scissorsBtn= new Button();
		scissorsBtn.setOnAction(event -> {
			getPlayerPick(2);
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});
		scissorsBtn.setGraphic(scissors_view);

		lizardBtn = new Button();
		lizardBtn.setOnAction(event -> {
			getPlayerPick(3);
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});
		lizardBtn.setGraphic(lizard_view);


		spockBtn = new Button();
		spockBtn.setOnAction(event -> {
			getPlayerPick(4);
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});
		spockBtn.setGraphic(spock_view);

		// Design area
		//VBox title1 = new VBox(10);
		HBox portArea = new HBox(10);
		portArea.getChildren().addAll(portNum,enterPortNum);
		portArea.setMargin(portNum, new Insets(30,0,0,30));
		portArea.setMargin(enterPortNum, new Insets(25,0,0,0));

		HBox ipArea = new HBox(10);
		ipArea.getChildren().addAll(ipAddress,enterIP);
		ipArea.setMargin(ipAddress, new Insets(10,0,0,124));
		ipArea.setMargin(enterIP, new Insets(5,0,0,0));

		//VBox firstBtn = new VBox(10);
		VBox startingScreen = new VBox(20);
		startingScreen.getChildren().addAll(pane,portArea,ipArea,pane2);

		HBox playAgain_quit_area = new HBox(10);
		playAgain_quit_area.getChildren().addAll(playAgain,quit);
		playAgain_quit_area.setMargin(playAgain, new Insets(70,0,0,50));
		playAgain_quit_area.setMargin(quit, new Insets(70,0,0,20));

		VBox introScreen = new VBox(10);
		introScreen.getChildren().addAll(playAgain_quit_area);

		HBox pointSystem = new HBox(10);
		pointSystem.getChildren().addAll(p1PointsLbl,p1PointCount,p2PointsLbl,p2PointCount);
		pointSystem.setMargin(p1PointsLbl, new Insets(10,0,0,150));
		pointSystem.setMargin(p1PointCount, new Insets(10,0,0,0));
		pointSystem.setMargin(p2PointsLbl, new Insets(10,0,0,30));
		pointSystem.setMargin(p2PointCount, new Insets(10,0,0,0));

		HBox round_system = new HBox(10);
		round_system.getChildren().addAll(roundTxt,roundNumber);
		round_system.setMargin(roundTxt, new Insets(10,0,0,300));
		round_system.setMargin(roundNumber, new Insets(10,0,0,0));

		VBox gameScreen = new VBox(10);
		gameScreen.getChildren().addAll(round_system,pane6,pointSystem,gameInfo,pane7);
		gameScreen.setMargin(gameInfo, new Insets(10,50,10,50));

		HBox firstRowImg = new HBox(10);
		firstRowImg.getChildren().addAll(rockBtn,paperBtn,scissorsBtn);
		firstRowImg.setMargin(rockBtn, new Insets(120,0,0,30));
		firstRowImg.setMargin(paperBtn, new Insets(0,0,0,20));
		firstRowImg.setMargin(scissorsBtn, new Insets(100,0,0,20));

		HBox secondImg = new HBox(10);
		secondImg.getChildren().addAll(lizardBtn,spockBtn);
		secondImg.setMargin(lizardBtn, new Insets(20,0,0,70));
		secondImg.setMargin(spockBtn, new Insets(30,0,0,70));

		VBox plPickScreen = new VBox(10);
		plPickScreen.getChildren().addAll(pane8,firstRowImg,secondImg);
		plPickScreen.setMargin(pane8, new Insets(20,0,10,0));

		// BACKGROUND Design
		plPickScreen.setStyle("-fx-background-image: url(\"back1.jpg\");" + "-fx-background-size:cover,auto;");
		gameScreen.setStyle("-fx-background-color: \n" +
				"        #090a0c,\n" +
				"        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
				"        linear-gradient(#20262b, #191d22),\n" +
				"        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));");
		startingScreen.setStyle("-fx-background-image: url(\"clientConnect.jpg\");" + "-fx-background-size:cover,auto;");
		introScreen.setStyle("-fx-background-image: url(\"home.jpg\");" + "-fx-background-size:cover,auto;");
		pane4.setStyle("-fx-background-color:linear-gradient(#232323,#3f87a6);");

		// Implementation Area
		// Just an idea after we connect we should set the primary stage to the add player since we know its connected
		connect.setOnAction(e->{
			// this should just send the IP and port number
			// get the IP and send it to the server
			// get the Port number and send it to the server
			//
			try {
				choosePortNum = Integer.parseInt(enterPortNum.getText());
				System.out.println(choosePortNum);
				chooseIp = enterIP.getText();
			}
			catch (Exception ex) {
				System.out.println("invalid port!");
				System.out.println("invalid ip!");
				return;
			}
			this.port = choosePortNum;
			this.ip   = chooseIp;

			getClientConnection = new Client(this.ip, this.port,
					data -> {
						Platform.runLater(()->{
							if(data.toString().indexOf("Score") != -1){
								int countTokens = 0;
								String[] tokens = data.toString().split("#");
								for (String t : tokens){
									if(countTokens == 0){}
									else if(countTokens == 1){
										roundNumber.setText(t);
									}
									else if(countTokens == 2){
										p1PointCount.setText(t);
									}
									else if(countTokens == 3){
										p2PointCount.setText(t);
									}
									countTokens++;
								}
							}
							else if(data.toString().indexOf("#GAMEOVER") != -1)
							{
								//Disable Both GUI Pick BTN
								playerPickBtn.setDisable(true);
								//Enable Bot GUI Homescreen BTN
								homeScreen.setDisable(false);
							}
							else
								gameInfo.getItems().add(data.toString());
						});
						//add here if needed

					});
			getClientConnection.getConnect();
			homeScreen.setDisable(true); // disable the home screen
			// if the points 3 then set it enable it
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});

		playerPickBtn.setOnAction(event -> {
			// if you picked already disable it
			// enable it after the other player picks one
			primaryStage.setScene(playerPickScene);
			primaryStage.setTitle("Pick One");
		});

		homeScreen.setOnAction(event -> {
			// should be disabled based at the start of the game
			// should be enabled when the game is over
			primaryStage.setScene(introScene);
			primaryStage.setTitle("Ready to Play?");
		});

		playAgain.setOnAction(e->{
			// clear the list view if that's possible
			// this should send a message to the server that there's a new game starting
			roundNumber.setText("1");
			p1PointCount.setText("0");
			p2PointCount.setText("0");
			gameInfo.getItems().clear();
			//getClientConnection.send("Client wants to play again!");

			playerPickBtn.setDisable(false);
			homeScreen.setDisable(true);
			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
		});

		quit.setOnAction(e->{
			// ends or close the client thread or program
			System.exit(0);
		});
		// Scene Area
		introScene = new Scene(introScreen, 400,200);
		gameScene = new Scene(gameScreen, 700,600);
		playerPickScene = new Scene(plPickScreen, 600,600);
		startScene = new Scene(startingScreen,500,400);
		primaryStage.setScene(startScene);
		primaryStage.show();
	}
	// Helper functions

	private void getPlayerPick(Integer i){
		ArrayList<String> rplsArr = new ArrayList<>(); // create an array to store RPSLS strings
		// add all the strings
		rplsArr.add("ROCK");
		rplsArr.add("PAPER");
		rplsArr.add("SCISSORS");
		rplsArr.add("LIZARD");
		rplsArr.add("SPOCK");

		String message = rplsArr.get(i); // store the strings on the string


		try {
			getClientConnection.send(message); // send it to the server
			gameInfo.getItems().add("You Played: " + message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
