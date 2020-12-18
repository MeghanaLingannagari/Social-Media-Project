package application;


import java.awt.TextArea;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import application.User;
import Visitor.*;
import application.Tree;
import application.UserGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {

	private Alert msg;
	@FXML
	private UserGroup ugRoot;
	@FXML
	private TreeItem<String> rootItem /*= new TreeItem<>("Root", new ImageView(folder))*/;
	@FXML // fx:id="treeView"
	private TreeView<String> treeView ; // Value injected by FXMLLoader

	public Controller() {
		ugRoot = new UserGroup("Root");
	}

	@FXML // fx:id="addUser"
	private Button addUser; // Value injected by FXMLLoader

	@FXML // fx:id="addGroup"
	private Button addGroup; // Value injected by FXMLLoader

	@FXML // fx:id="userId"
	private TextField userId; // Value injected by FXMLLoader

	@FXML // fx:id="groupId"
	private TextField groupId; // Value injected by FXMLLoader

	@FXML // fx:id="openUserView"
	private Button openUserView; // Value injected by FXMLLoader

	@FXML // fx:id="showUserTotal"
	private Button showUserTotal; // Value injected by FXMLLoader

	@FXML // fx:id="showMessageTotal"
	private Button showMessageTotal; // Value injected by FXMLLoader

	@FXML // fx:id="showPositivePercentage"
	private Button showPositivePercentage; // Value injected by FXMLLoader

	@FXML // fx:id="showGroupTotal"
	private Button showGroupTotal; // Value injected by FXMLLoader
	
	@FXML
	private Button latestUpdate;
	
	@FXML
	private Button verify;


	public void initialize() {	//method to initialize the root in tree view
		rootItem = new TreeItem<>("Root");
		treeView.setRoot(rootItem);
	}

	//find user with using Tree entry and ID
		private User grabUser(Tree entry, String id) { 
			if (entry instanceof User && entry.getId().equals(id)) {
				return (User) entry;
			}

			if (entry instanceof UserGroup) {
				for (Tree t : ((UserGroup) entry).getList()) {
					User user = grabUser(t, id);
					if (user != null) {
						return user;
					}
				}
			}
			return null;
		}
	
	
	@FXML
	void addUser(ActionEvent event) {	//Add User button

		if(treeView.getSelectionModel().getSelectedItem() == null) {
			msg = new Alert(Alert.AlertType.ERROR, "Select a group first.");
			msg.show();
		}
		else if(userId.getText().equals("")) {
			msg = new Alert(Alert.AlertType.ERROR, "Please enter an id.");
			msg.show();
		}
		else {
			if(treeView.getSelectionModel().getSelectedItem().getValue().equals("Root")) {
				TreeItem<String> treeItem = new TreeItem<>(userId.getText());
				treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
				treeView.getSelectionModel().getSelectedItem().setExpanded(true);
				ugRoot.addUser(userId.getText());
			}
			else {
				for (Tree t : ((UserGroup) ugRoot).getList()) {
					if (t instanceof UserGroup) {
						if (treeView.getSelectionModel().getSelectedItem().getValue().equals(t.getId())) {
							TreeItem<String> treeItem = new TreeItem<>(userId.getText());
							treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
							treeView.getSelectionModel().getSelectedItem().setExpanded(true);
							((UserGroup) t).addUser(userId.getText());
							break;
						}
					}
				}
			}
			userId.clear();
		}

	}

	@FXML
	void addUserGroup(ActionEvent event) {	//add user group button

		if (treeView.getSelectionModel().getSelectedItem() == null) {
			msg = new Alert(Alert.AlertType.ERROR, "Select a group first.");
			msg.show();
		}
		else if (groupId.getText().equals("")) {
			msg = new Alert(Alert.AlertType.ERROR, "Please enter an id.");
			msg.show();
		}
		else {
			if (treeView.getSelectionModel().getSelectedItem().getValue().equals("Root")) {
				TreeItem<String> treeItem = new TreeItem<>(groupId.getText());
				treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
				treeView.getSelectionModel().getSelectedItem().setExpanded(true);
				ugRoot.addGroup(groupId.getText());
			}
			else {
				
				for (Tree t : ((UserGroup) ugRoot).getList()) {
					if (t instanceof UserGroup) {
						if (treeView.getSelectionModel().getSelectedItem().getValue().equals(t.getId())) {
							TreeItem<String> treeItem = new TreeItem<>(groupId.getText());
							treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
							treeView.getSelectionModel().getSelectedItem().setExpanded(true);
							((UserGroup) t).addGroup(groupId.getText());
							break;
						}
					}
				}
			}
			groupId.clear();
		}
	}
	
	@FXML void userViewButtonPushed(ActionEvent event) {
		if (treeView.getSelectionModel().getSelectedItem() != null) {
            User user = grabUser(ugRoot, treeView.getSelectionModel().getSelectedItem().getValue());
            if(user != null) {
                user.openWindoUV();
            }
            else {
                msg = new Alert(Alert.AlertType.ERROR, "Please select a user.");
                msg.show();
            }
        }
        else {
            msg = new Alert(Alert.AlertType.ERROR, "Please select a user.");
            msg.show();
        }
	}

	//button functionalities:
	@FXML
	void userTotal(ActionEvent event) {	
		UserTotal visitor = new UserTotal();
		ugRoot.accept(visitor);
		int count = visitor.getUserCount();
		Alert registeredUsersalert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " users.");
		registeredUsersalert.show();
	}

	@FXML
	void groupTotal(ActionEvent event) {	
		UserGroupTotal visitor = new UserGroupTotal();
		ugRoot.accept(visitor);
		int count = visitor.getCounter() -1;
		Alert userGroupalert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " user group(s) in addition to the Root group. ");
		userGroupalert.show();
	}

	@FXML 
	void positiveTotal(ActionEvent event) {	//add positive percentage total button
		PositivePercentageTotal pVisitor = new PositivePercentageTotal();
		MessageTotal mVisitor = new MessageTotal();
		ugRoot.accept(pVisitor);
		ugRoot.accept(mVisitor);
		int pCounter = pVisitor.getCount();
		int mCounter = mVisitor.getCounter();
		
		if(mCounter == 0) {
			msg = new Alert(Alert.AlertType.ERROR, "There are currently no messages.");
		}
		else {
			double percent = (double) pCounter / mCounter;
			msg = new Alert(Alert.AlertType.INFORMATION, (percent * 100) + "% of messages are positive");
		}
		
		msg.setHeaderText("Positive Messages Total");
		msg.show();
	}
	
	@FXML 
	void messageTotal(ActionEvent event) {	//add messages total button
		MessageTotal visitor = new MessageTotal();
		ugRoot.accept(visitor);
		int count = visitor.getCounter();
		msg = new Alert(Alert.AlertType.INFORMATION, "Number of Current Messages is : " + count);
		msg.setHeaderText("Message Total");
		msg.show();
	}
	
	@FXML
	void checkUpdates (ActionEvent event) {
		CheckLastUpdate visitor = new CheckLastUpdate();
		ugRoot.accept(visitor);
		User user = visitor.getUser();
		
		if(user == null) 
			msg = new Alert(Alert.AlertType.INFORMATION, "There are currently no registered users. Please add users before trying again.");
		else
			msg = new Alert(Alert.AlertType.INFORMATION, "The user that was last updated is: " + user.getId());
		msg.setHeaderText("Updates");
		msg.show();
	}

}