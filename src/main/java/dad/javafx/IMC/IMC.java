package dad.javafx.IMC;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	// view
	private Label pesoLabel, kgLabel, alturaLabel, cmLabel, IMClabel, IMCresultLabel, resultadoFinalLabel;
	private TextField pesoText, alturaText;

	// model
	private DoubleProperty peso = new SimpleDoubleProperty(0);
	private DoubleProperty altura = new SimpleDoubleProperty(0);
	private DoubleProperty resultado = new SimpleDoubleProperty(0);

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		pesoLabel = new Label("Peso: ");

		kgLabel = new Label("kg");

		alturaLabel = new Label("Altura: ");

		cmLabel = new Label("cm");

		IMClabel = new Label("IMC:");

		IMCresultLabel = new Label();

		resultadoFinalLabel = new Label();

		pesoText = new TextField();
		pesoText.setPrefColumnCount(5);
		pesoText.setAlignment(Pos.CENTER);
		pesoText.setMaxWidth(150);

		alturaText = new TextField();
		alturaText.setPrefColumnCount(5);
		alturaText.setAlignment(Pos.CENTER);
		alturaText.setMaxWidth(150);

		HBox pesoHbox = new HBox();
		pesoHbox.setSpacing(5);
		pesoHbox.setAlignment(Pos.CENTER);
		pesoHbox.getChildren().addAll(pesoLabel, pesoText, kgLabel);

		HBox alturaHbox = new HBox();
		alturaHbox.setSpacing(5);
		alturaHbox.setAlignment(Pos.CENTER);
		alturaHbox.getChildren().addAll(alturaLabel, alturaText, cmLabel);

		HBox resultadoHbox = new HBox();
		resultadoHbox.setSpacing(5);
		resultadoHbox.setAlignment(Pos.CENTER);
		resultadoHbox.getChildren().addAll(IMClabel, IMCresultLabel);

		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoHbox, alturaHbox, resultadoHbox, resultadoFinalLabel);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setScene(scene);
		primaryStage.setTitle("IMC");
		primaryStage.show();

		// Bindings
		Bindings.bindBidirectional(alturaText.textProperty(), altura, new NumberStringConverter());

		Bindings.bindBidirectional(pesoText.textProperty(), peso, new NumberStringConverter());

		// Pasamos a metros
		DoubleBinding alturaenM = altura.divide(100);

		// Altura al cuadrado
		DoubleBinding alturaCuadrado = alturaenM.multiply(alturaenM);

		// Operacion
		DoubleBinding operacion = peso.divide(alturaCuadrado);

		resultado.bind(operacion);

		IMCresultLabel.textProperty().bind(resultado.asString("%.2f"));

		IMCresultLabel.textProperty().addListener((o, ov, nv) -> {
			Double n = resultado.doubleValue();
			if (n < 18.5) {
				resultadoFinalLabel.setText("Bajo Peso");
			}
			if (n >= 18.5 && n < 25) {
				resultadoFinalLabel.setText("Normal");
			}
			if (n >= 25 && n < 30) {
				resultadoFinalLabel.setText("Sobrepeso");
			}
			if (n >= 30) {
				resultadoFinalLabel.setText("Obeso");
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}

}
