package application;

//This class supports the type User for each user in mini twitter

import Visitor.Observer;
import Visitor.Subject;
import Visitor.Visitor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class User extends Subject implements Tree, Observer{
	
	private String id;
	private List<String> followers;
	private List<String> following;
	private List<String> tweets;
	private List<String> feed;
	private ListView<String> foList;
	private ListView<String> feList;
	private Alert alert;
	
	private boolean windowOpened;
	private Stage stage;
	private BorderPane BP;
	private TextField userText;
	private TextArea messageText;
	
	private long createTime;
	private long updateTime;
	Label updateLabel;
	
	
	//Constructor
	public User(String id) {
		this.id = id;
		followers = new ArrayList<>();
		following = new ArrayList<>();
		tweets = new ArrayList<>();
		feed = new ArrayList<>();
		createTime = System.currentTimeMillis();
		updateTime = System.currentTimeMillis();
		updateLabel = new Label("Last updated was at: " + updateTime);
		startStage();
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public void update(Subject sub) {
		User user = (User) sub;
		feed.add(user.getTweets().get(user.getTweets().size()-1));
		ObservableList<String> tweetList = FXCollections.observableArrayList(feed);
		feList.setItems(tweetList);
	}
	
	public void follow(String id) {	//method where a user attaches to the given id 
		AdminPanel admin = AdminPanel.getInstance();
		User user = admin.findUser(id);
		if (user != null) {
			user.add(this);
			following.add(id);
		}
		else {
			alert = new Alert(Alert.AlertType.ERROR, "User Does Not Exist.");
			alert.show();
		}
	}
	
	public void post(String msg) {
		tweets.add(msg);
		feed.add(msg);
		updateTime = System.currentTimeMillis();
		updateLabel.setText("Was last updated at: " + updateTime);
		update();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public List<String> getFollowers() {
		return followers;
	}

	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}

	public List<String> getFollowing() {
		return following;
	}

	public void setFollowing(List<String> following) {
		this.following = following;
	}

	public List<String> getTweets() {
		return tweets;
	}

	public void setTweets(List<String> tweets) {
		this.tweets = tweets;
	}

	public List<String> getFeed() {
		return feed;
	}

	public void setFeed(List<String> feed) {
		this.feed = feed;
	}

	public ListView<String> getFoList() {
		return foList;
	}

	public void setFoList(ListView<String> foList) {
		this.foList = foList;
	}

	public ListView<String> getFeList() {
		return feList;
	}

	public void setFeList(ListView<String> feList) {
		this.feList = feList;
	}
	
	public long getUpdatedTime() {return updateTime;}
	
	
//UI
	private void startStage() { 
        windowOpened = false;
        BP = new BorderPane();
        stage = new Stage();
        stage.setTitle(id);
        stage.setScene(new Scene(BP, 750, 500));
        stage.setOnCloseRequest(event -> {
            windowOpened = false;
        });
    }
	
	public void openWindoUV() { 
        if (windowOpened) {
            return;
        }
        windowOpened = true;  

        userText = new TextField();
        messageText = new TextArea();
        Button followButton = new Button("Follow");
        Button postButton = new Button("Post");
        foList= new ListView<>();
        feList = new ListView<>();

        ObservableList<String> inFeList = FXCollections.observableArrayList(feed); 
        feList.setItems(inFeList);
        ObservableList<String> inFoList = FXCollections.observableArrayList(following);
        foList.setItems(inFoList);

        followButton.setOnAction(event -> {
            if (id.equals(userText.getText())) {
                alert = new Alert(Alert.AlertType.ERROR, "You cannot follow that user. Please enter a different user id.");
                alert.show();
            }
            else if(following.contains(userText.getText())) {
                alert = new Alert(Alert.AlertType.ERROR, "You are already following this user.");
                alert.show();
            }
            else {
                follow(userText.getText());
                ObservableList<String> followList = FXCollections.observableArrayList(following); 
                foList.setItems(followList);
            }
            userText.clear();
        });

        postButton.setOnAction(event -> {
            if (messageText.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter text.");
                alert.show();
            }
            post(id + ": " + messageText.getText());
            ObservableList<String> tweetList = FXCollections.observableArrayList(feed);
            feList.setItems(tweetList);
            messageText.clear();
        });

        VBox rootBox = new VBox(10);
        rootBox.setPadding(new Insets(10));
        rootBox.setAlignment(Pos.CENTER);
        HBox followBox = new HBox(10);
        Label followLabel = new Label("Follow User: ");
        followBox.setPadding(new Insets(10));
        followBox.setAlignment(Pos.CENTER);
        HBox postBox = new HBox(10);
        Label followingLabel = new Label("Following");
        Label feedLabel = new Label("News Feed");       
        postBox.setPadding(new Insets(10));   
        postBox.setAlignment(Pos.CENTER);
        
        HBox timeBox = new HBox(10);
        Label timeLabel = new Label("Created at: " + createTime);
        timeBox.setPadding(new Insets(10));
        

        rootBox.getChildren().addAll(followBox, followingLabel, foList, feedLabel, feList, postBox);
        followBox.getChildren().addAll(followLabel, userText, followButton);
        postBox.getChildren().addAll(messageText, postButton);
        timeBox.getChildren().addAll(timeLabel,updateLabel);
        BP.setCenter(rootBox);
        stage.show();
        
    }
	
}