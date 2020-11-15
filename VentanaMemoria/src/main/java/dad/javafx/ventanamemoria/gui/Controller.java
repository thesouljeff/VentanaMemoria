package dad.javafx.ventanamemoria.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable {
	
	// view FXML
	@FXML
	private GridPane view;
	
	@FXML
	private Slider sRed;
	
	@FXML
	private Slider sGreen;
	
	@FXML
	private Slider sBlue;
	
	private Stage stage;
	
	// Model
	private Model model = new Model();
	
	public Controller(Properties prop) throws IOException {
		model.setRed(Integer.valueOf((String) prop.get("background.red")));
		model.setGreen(Integer.valueOf((String) prop.get("background.green")));
		model.setBlue(Integer.valueOf((String) prop.get("background.blue")));
		model.setWidth(Double.valueOf((String) prop.get("size.width")));
		model.setHeight(Double.valueOf((String) prop.get("size.height")));
		model.setLocationX(Double.valueOf((String) prop.get("location.x")));
		model.setLocationY(Double.valueOf((String) prop.get("location.y")));
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	
	public void initialize(URL location, ResourceBundle resources) {
		Bindings.bindBidirectional(sRed.valueProperty(), model.redProperty());
		Bindings.bindBidirectional(sGreen.valueProperty(), model.greenProperty());
		Bindings.bindBidirectional(sBlue.valueProperty(), model.blueProperty());
		
		sRed.valueProperty().addListener(e -> onSlideValueChange(e));
		sGreen.valueProperty().addListener(e -> onSlideValueChange(e));
		sBlue.valueProperty().addListener(e -> onSlideValueChange(e));
		
		view.setStyle("-fx-background-color: rgb(" + model.getRed() + "," + model.getGreen() + "," + model.getBlue() + ");");
	}
		
	private void onSlideValueChange(Observable e) {
		view.setStyle("-fx-background-color: rgb(" + model.getRed() + "," + model.getGreen() + "," + model.getBlue() + ");");
	}
	
	public void crearListenersStage(Stage primaryStage) {
		stage = primaryStage;
		
		stage.widthProperty().addListener(e -> onStageSizeChange(e));
		stage.heightProperty().addListener(e -> onStageSizeChange(e));
		
		stage.xProperty().addListener(e -> onStagePositionChange(e));
		stage.yProperty().addListener(e -> onStagePositionChange(e));
	}

	private void onStageSizeChange(Observable e) {
		model.setWidth(stage.getWidth());
		model.setHeight(stage.getHeight());
	}
	
	private void onStagePositionChange(Observable e) {
		model.setLocationX(stage.getX());
		model.setLocationY(stage.getY());
	}
	
	public GridPane getView() {
		return view;
	}

	public void setView(GridPane view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}	

}
