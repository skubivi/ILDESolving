package sample;

import equation.Ratio;
import ilde.ILDE;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    //Создание начального окна для ввода n
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ILDE");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    //Создание окна для ввода a0..an
    public static void cont(int n, ActionEvent event) {
        FlowPane root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);


        Image f = new Image("f.png");
        ImageView fV = new ImageView(f);
        root.getChildren().add(fV);

        ArrayList<FlowPane> fA = new ArrayList<>();
        ArrayList<Label> lA = new ArrayList<>();
        ArrayList<TextField> tA = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            lA.add(new Label("a" + i + " ="));
            tA.add(new TextField());
            fA.add(new FlowPane());
            fA.get(i).setOrientation(Orientation.HORIZONTAL);
            fA.get(i).setAlignment(Pos.CENTER);
            fA.get(i).setVgap(10);
            fA.get(i).setHgap(10);
            fA.get(i).getChildren().addAll(lA.get(i), tA.get(i));
            root.getChildren().add(fA.get(i));
        }

        Button b = new Button("Выбрать файл функции u(t)");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    boolean c = check(tA.get(0));
                    for (int i = 1; i < tA.size(); i++) {
                        c = c && check(tA.get(i));
                    }
                    if (c)
                        end(tA, n, actionEvent);
                } catch (Exception e) {

                }
            }
        });
        Button back = new Button("Назад");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage primaryStage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setTitle("ILDE");
                primaryStage.setScene(new Scene(root, 400, 500));
                primaryStage.show();

                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        });

        FlowPane bF = new FlowPane(b, back);
        bF.setHgap(10);
        bF.setVgap(20);
        root.getChildren().add(bF);

        Stage stage = new Stage();
        stage.setTitle("ILDE");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private static boolean check(TextField a) {
        boolean b = true;
        if (a.getCharacters().toString().equals("")) return true;
        try {
            Double.parseDouble(a.getCharacters().toString());
            b = true;
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    //Создание окна для выбора файла функции и решения НЛДУ
    private static void end(ArrayList<TextField> tA, int n, ActionEvent event) {
        File dir = new File(System.getProperty("user.dir") + "\\functions");
        ArrayList<File> lst = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isFile() && !file.getName().equals("readme.txt")) {
                lst.add(file);
            }
        }

        FlowPane root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.TOP_CENTER);
        root.setHgap(10);
        root.setVgap(20);

        ArrayList<RadioButton> rBL = new ArrayList<>();
        ToggleGroup tG = new ToggleGroup();
        for (int i = 0; i < lst.size(); i++) {
            FlowPane file = new FlowPane();
            file.setOrientation(Orientation.HORIZONTAL);
            file.setAlignment(Pos.CENTER);
            file.setVgap(20);
            file.setHgap(10);
            rBL.add(new RadioButton());
            rBL.get(i).setToggleGroup(tG);
            Label text = new Label(lst.get(i).getName());
            file.getChildren().addAll(text, rBL.get(i));
            root.getChildren().add(file);
        }
        Button load = new Button("Вычислить");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = "";
                ArrayList<Ratio> r = new ArrayList<>();
                for (int i = 0; i < tA.size(); i++) {
                    double v = 0;
                    if (tA.get(i).getCharacters().toString() != "")
                        v = Double.parseDouble(tA.get(i).getCharacters().toString());
                    r.add(new Ratio(tA.size() - i - 1, v));
                }
                int c = -1;
                for (int i = 0; i < rBL.size(); i++) {
                    if (rBL.get(i).isSelected()) c = i;
                }
                if (c != -1) {
                    String eq = "";
                    Scanner in = null;
                    try {
                        in = new Scanner(lst.get(c));
                        s = in.nextLine();
                        ILDE f = new ILDE(r, s, 1);
                        f.solve();
                        eq = f.getEq();
                    } catch (FileNotFoundException e) {
                    }
                    try (FileWriter writer = new FileWriter("solvings\\" + lst.get(c).getName(), false)) {
                        System.out.println(eq);
                        writer.write(eq);
                        writer.flush();
                        sOut(eq, actionEvent);
                    } catch (IOException e) {
                    }
                }
            }
        });

        Button back = new Button("Назад");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cont(n, actionEvent);
            }
        });

        FlowPane bF = new FlowPane(load, back);
        bF.setHgap(10);
        bF.setVgap(20);
        root.getChildren().add(bF);

        Stage stage = new Stage();
        stage.setTitle("ILDE");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private static void sOut(String s, ActionEvent event){
        FlowPane root = new FlowPane();

        Label label = new Label(s);
        root.getChildren().add(label);

        Stage stage = new Stage();
        stage.setTitle("ILDE");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
