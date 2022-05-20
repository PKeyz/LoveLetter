package bb.love_letter.user_interface.view;

import bb.love_letter.user_interface.Client;
import bb.love_letter.user_interface.controller.ChatController;
import bb.love_letter.user_interface.model.ChatModel;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 *
 * @author Zeynab Baiani
 */
public class ChatView {
    public ChatModel model;
    private GridPane view;
    private TextField messageField;
    private Button sentbutton;
    private ListView<HBox> listView;
    public ChatView (ChatModel model) {
        this.model = model;
        buildUI();
        setUpListeners();
        //observeModelandUpdate();
    }

    private void buildUI() {
        view = new GridPane();
        messageField = new TextField();
        messageField.setPromptText("Type your message here...");
        messageField.setId("messageField");
        sentbutton = new Button("Send");
        sentbutton.setId("sentButton");
        listView = new ListView<>(model.getHBoxObservableList());
        listView.setMouseTransparent(true);
        listView.setFocusTraversable(false);
        HBox chatBox = new HBox();
        HBox.setHgrow(messageField, Priority.ALWAYS);
        RowConstraints regRow = new RowConstraints();
        regRow.setVgrow(Priority.ALWAYS);
        view.getRowConstraints().add(regRow);

        chatBox.getChildren().addAll(messageField, sentbutton);
        messageField.setPrefWidth(600);
        sentbutton.setPrefWidth(100);
        view.addRow(0,listView);
        view.addRow(1, chatBox);
    }

    public Parent asParent() {
        return view ;
    }

    private void setUpListeners() {
        sentbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String message = messageField.getText();
                model.setCurrentMessage(message);
                messageField.setText("");
            }
        });
    }
}
