package dad.javafx.ventanamemoria.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	private Controller controller;
	
	private void crearConfiguracionInicial(File fichero) throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(fichero));
		String configInit = "";
		configInit += "background.red=255\n";
		configInit += "background.green=255\n";
		configInit += "background.blue=255\n";
		configInit += "size.width=320\n";
		configInit += "size.height=200\n";
		configInit += "location.x=440\n";
		configInit += "location.y=244";
		
		bWriter.write(configInit);
		bWriter.close();
	}
	
	@Override
	public void init() throws Exception {
		String rutaPerfil = System.getProperty("user.home");
		Properties prop = new Properties();
		File fConfig = new File(rutaPerfil + "\\.VentanaConMemoria\\ventana.config");
		
		if (!fConfig.getParentFile().exists() && !fConfig.getParentFile().mkdirs())
			throw new IllegalStateException("No se pudo crear la carpeta .VentanaConMemoria");
		
		if (fConfig.createNewFile()) {
			crearConfiguracionInicial(fConfig);
		}
		
		prop.load(new FileInputStream(fConfig));
		controller = new Controller(prop);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(controller.getView(), controller.getModel().getWidth(), controller.getModel().getHeight());
		primaryStage.setTitle("Ventana con memoria");
		primaryStage.setScene(scene);
		primaryStage.setX(controller.getModel().getLocationX());
		primaryStage.setY(controller.getModel().getLocationY());
		primaryStage.show();
		
		controller.crearListenersStage(primaryStage);
	}
	
	@Override
	public void stop() throws Exception {
		String rutaPerfil = System.getProperty("user.home");
		Properties prop = new Properties();
		File fConfig = new File(rutaPerfil + "\\.VentanaConMemoria\\ventana.config");
		Model model = controller.getModel();
		
		if (!fConfig.getParentFile().exists() && !fConfig.getParentFile().mkdirs())
			throw new IllegalStateException("No se pudo crear la carpeta .VentanaConMemoria");
		
		// Creamos el fichero en caso de no existir
		fConfig.createNewFile();
		
		prop.setProperty("background.red", String.valueOf(model.getRed()));
		prop.setProperty("background.green", String.valueOf(model.getGreen()));
		prop.setProperty("background.blue", String.valueOf(model.getBlue()));
		prop.setProperty("size.width", String.valueOf(model.getWidth()));
		prop.setProperty("size.height", String.valueOf(model.getHeight()));
		prop.setProperty("location.x", String.valueOf(model.getLocationX()));
		prop.setProperty("location.y", String.valueOf(model.getLocationY()));
		
		prop.store(new FileWriter(fConfig), null);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
