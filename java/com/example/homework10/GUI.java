package com.example.homework10;

import javafx.animation.*;
import javafx.application.Application;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import static java.lang.Double.parseDouble;
import static javafx.collections.FXCollections.*;

public class GUI extends Application implements Serializable {

    public static User currentUser = null;
    public static int ownShare;
    public static ArrayList<String> Gdates= new ArrayList<>();
    public static  ArrayList<String> Gprices = new ArrayList<>();
    public static  ArrayList<String> GobalComapreprices = new ArrayList<>();
    public static  ArrayList<String> GobalComapredates = new ArrayList<>();
    private static TextField tfPassword;
    private static TextField tfUsername;

    Stage window;
    Scene sceneLogin, sceneSigUp, sceneHome,sceneMarket ,sceneAccount;


    @Override
    public void start(Stage stage) {
        window = stage;
        Pane root = loginGUI();
        sceneLogin = new Scene(root, 1200, 670);
        sceneLogin.getStylesheets().add("styleLogInpage.css");
        window.setScene(sceneLogin);
        window.setTitle("Login Page");
        window.show();
    }

    public static void main(String[] args)  {
        DataCenter.getInstance();
        launch();
        DataCenter.getInstance().savefile();
    }

    private Pane loginGUI() {
        Image image = new Image("pic2 2.jpeg");
        BackgroundSize backgroundSize = new BackgroundSize(1100, 800, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);
        //button.setEffect(dropShadow);


        GridPane pane = new GridPane();


        pane.setBackground(background);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
        Label lbltitle= new Label("TradeMaster");
        lbltitle.setFont(new Font("Arial", 86));
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(lbltitle.opacityProperty(), 0))
        );
        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();

        Label lblUsername = new Label("Username");
        Label lblPassword = new Label("Password");
        tfUsername = new TextField();
        tfUsername.setPromptText("Username");
        tfUsername.setFocusTraversable(false);
        tfPassword = new TextField();
        tfPassword.setPromptText("Password");
        tfPassword.setFocusTraversable(false);
        pane.add(lbltitle,0,0,2,1);
        pane.add(lblUsername, 0, 1);
        pane.add(lblPassword, 0, 2);
        pane.add(tfUsername, 1, 1, 2, 1);
        pane.add(tfPassword, 1, 2, 2, 1);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        Button btnLogin = new Button("Login");
        Button btnSigUp = new Button("SignUp");
        btnLogin.setDisable(true);
        btnLogin.setPrefWidth(120);
        btnSigUp.setPrefWidth(120);
        hbox.getChildren().addAll(btnSigUp, btnLogin);
        pane.add(hbox, 1, 4, 2, 1);

        // register event handlers
        class MyKeyEventHanlder implements EventHandler<KeyEvent> {
            public void handle(KeyEvent e) {
                String username = tfUsername.getText();
                String password = tfPassword.getText();
                btnLogin.setDisable(!validateLogin(username, password));
            }
        }
        tfUsername.setOnKeyTyped(new MyKeyEventHanlder());


        tfPassword.setOnKeyTyped(e -> {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            btnLogin.setDisable(!validateLogin(username, password));
        });

        btnLogin.setOnAction(e -> {
            User user = DataCenter.getInstance().login(tfUsername.getText(),tfPassword.getText());
            boolean b = DataCenter.getInstance().findUser(tfUsername.getText(), tfPassword.getText());
            if (b) {
                sceneHome = new Scene(homePage(user), 1200, 1000);
                sceneHome.getStylesheets().add("/styleOne.css");
                window.setScene(sceneHome);
                window.setTitle("Home Page");
                window.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("error-header");
                alert.setContentText("Failed to validate user login");
                alert.showAndWait();
            }
        });
        btnSigUp.setOnAction(e -> {
            sceneSigUp = new Scene(SignUp(), 1200, 1000);
            sceneSigUp.getStylesheets().add("/styleOne.css");
            window.setScene(sceneSigUp);
            window.setTitle("Sign Up");
            window.show();
        });

        btnLogin.setEffect(dropShadow);
        btnSigUp.setEffect(dropShadow);
        lbltitle.setEffect(dropShadow);
        lblUsername.setEffect(dropShadow);
        lblPassword.setEffect(dropShadow);

        tfUsername.setEffect(dropShadow);
        tfPassword.setEffect(dropShadow);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(lbltitle.prefWidthProperty(), 500),
                        new KeyValue(lbltitle.prefHeightProperty(), 150)));
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(lbltitle.prefWidthProperty(), 500),
                        new KeyValue(lbltitle.prefHeightProperty(), 150)));
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(4),
                        new KeyValue(lbltitle.prefWidthProperty(), 500),
                        new KeyValue(lbltitle.prefHeightProperty(), 150)));

        timeline.play();


        return pane;
    }

    public Pane SignUp() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblName = new Label("Full Name");
        Label lblUsername = new Label("Username");
        Label lblPassword = new Label("Password");
        Label lblConfirmPasword = new Label("Confirm Password");

        TextField tfrname = new TextField();
        tfrname.setPromptText("Full Name");
        tfrname.setFocusTraversable(false);
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");
        tfUsername.setFocusTraversable(false);
        TextField tfPassword = new TextField();
        tfPassword.setPromptText("Password");
        tfPassword.setFocusTraversable(false);
        TextField tfConfirmPassword = new TextField();
        tfConfirmPassword.setPromptText("Confirm Paswword");
        tfConfirmPassword.setFocusTraversable(false);

        pane.add(lblName, 0, 0);
        pane.add(lblUsername, 0, 1);
        pane.add(lblPassword, 0, 2);
        pane.add(lblConfirmPasword, 0, 3);
        pane.add(tfrname, 1, 0, 2, 1);
        pane.add(tfUsername, 1, 1, 2, 1);
        pane.add(tfPassword, 1, 2, 2, 1);
        pane.add(tfConfirmPassword, 1, 3, 2, 1);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        Button btnSignUp = new Button("SignUp");
        Button btnLogIn = new Button("LogIn");
        btnSignUp.setDisable(true);
        btnSignUp.setPrefWidth(120);
        btnLogIn.setPrefWidth(120);
        hbox.getChildren().addAll(btnSignUp, btnLogIn);
        pane.add(hbox, 1, 4, 2, 1);

        tfUsername.setOnKeyTyped(e -> {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            String confirmPassword = tfConfirmPassword.getText();
            btnSignUp.setDisable(!validateSignUp(username, password, confirmPassword));
        });
        tfPassword.setOnKeyTyped(e -> {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            String confirmPassword = tfConfirmPassword.getText();
            btnSignUp.setDisable(!validateSignUp(username, password, confirmPassword));
        });
        tfConfirmPassword.setOnKeyTyped(e -> {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            String confirmPassword = tfConfirmPassword.getText();
            btnSignUp.setDisable(!validateSignUp(username, password, confirmPassword));
        });
        btnSignUp.setOnAction(e -> {
            User user = new User(tfrname.getText(), tfUsername.getText(), tfPassword.getText());
            boolean add = DataCenter.getInstance().addUser(user);
            if (add) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Registration is completed");
                alert.setContentText("You may logIn now");
                alert.showAndWait();
                sceneLogin.getStylesheets().add("styleLogInpage.css");
                window.setScene(sceneLogin);
                window.setTitle("Log In");
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("error-header");
                alert.setContentText("Failed Registration");
                alert.setContentText("Please try  using different username");
                alert.showAndWait();
            }
        });
        btnLogIn.setOnAction(e -> {
            //sceneLogin = new Scene(loginGUI(), 800, 600);
            FillTransition transition = new FillTransition(Duration.seconds(1), btnLogIn.getShape(), Color.WHITE, Color.GREEN);
            transition.play();
            sceneLogin.getStylesheets().add("styleLogInpage.css");
            window.setScene(sceneLogin);
            window.setTitle("Log In");
            window.show();
        });


        return pane;

    }

    private boolean validateLogin(String username, String password) {
        return (username.length() >= 6 && password.length() >= 6);
    }

    public boolean validateSignUp(String username, String password, String confirmPaswoord) {
        if (!username.equals("") ||Character.isAlphabetic(username.charAt(0)) && username.length() >= 6) {
            if (password.length() >= 6 && password.equals(confirmPaswoord)) {
                for (int i = 0; i < password.length(); i++) {
                    if (Character.isDigit(password.charAt(i))) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public Pane homePage(User user) {
        currentUser=user;

        System.out.println(currentUser.getHistory());
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
        Label lblCash = new Label("Cash $ " + Math.round(user.getCash() * 100) / 100.0);


        Label lblName = new Label("Welcome " + DataCenter.getInstance().getNameData(user));
        System.out.println(user.getName());

        lblName.setFont(new Font("Arial", 36));

        Button btnAcc = new Button("Wallet");
        Button btnMrket = new Button("Market");
        Button btnLogOut = new Button("Logout");
        btnLogOut.setOnAction(e->{
            currentUser=null;
            tfPassword.setText("");
            tfUsername.setText("");
            sceneLogin.getStylesheets().add("/styleOne.css");
            window.setScene(sceneLogin);
            window.setTitle("Log In");
            window.show();

        });

        btnAcc.setOnAction(e->{
            sceneAccount= new Scene(accountPage(),1200,1200);
            sceneAccount.getStylesheets().add("/styleOne.css");
            window.setScene(sceneAccount);
            window.show();
        });
        btnMrket.setOnAction(e->{
            sceneMarket= new Scene( new MyDatePicker().initialGUI(),1200,1000);
            sceneMarket.getStylesheets().add("/styleOne.css");
            window.setScene(sceneMarket);
            window.show();
        });
        Button btnPortflio = new Button("My Stocks");
        btnPortflio.setOnAction(e->{
            sceneMarket= new Scene(walletPane(),1200,1000);
            sceneMarket.getStylesheets().add("/styleOne.css");
            window.setScene(sceneMarket);
            window.show();
        });


        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(btnAcc, btnPortflio, btnMrket);

        pane.add(hBox, 0, 1);
        pane.add(lblCash, 3, 0);


        pane.add(pieChart(), 0, 4);
        pane.add(listView(),3,4);
        pane.add(btnLogOut,0,5);


        pane.add(lblName, 0, 0);


        return pane;
    }
        public PieChart pieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Portfolio");

        ObservableList<PieChart.Data> list = observableArrayList();
        ArrayList<String> newNameList = currentUser.getWallet().getStockNames();
        for(int i =0;i< newNameList.size();i++) {
            list.add(new PieChart.Data(newNameList.get(i), currentUser.getWallet().findUserStocks(newNameList.get(i))));
        }


        pieChart.setData(list);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(10),
                            new KeyValue(pieChart.startAngleProperty(), 360))
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();


        return pieChart;
    }


        class MyDatePicker extends BorderPane implements Serializable {

            public static TextField tfStock;
            private DatePicker start;
            private DatePicker end;

            private Button btnApply, btnHome;



            public MyDatePicker() {


            }


            public double getCurrentStockPrice(String stockName) throws IOException {

                LocalDate now = LocalDate.now();
                LocalDate start = now.minusDays(2);
                long startPeriod = getPeriod(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
                long endPeriod = getPeriod(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
                URL url = new URL(getURL(stockName, startPeriod, endPeriod));
                Scanner s = new Scanner(url.openStream());
                int count = 0;
                try {
                    while (s.hasNext()) {
                        count++;
                        String[] values = s.nextLine().split(",");
                        if (count == 2) {
                            return Double.valueOf(values[5]);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return Double.MAX_VALUE;
            }
            public String tenDaysStockPrice(String stockName) throws IOException {

                LocalDate now = LocalDate.now();
                LocalDate start = now.minusDays(10);
                long startPeriod = getPeriod(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
                long endPeriod = getPeriod(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
                URL url = new URL(getURL(stockName, startPeriod, endPeriod));
                Scanner s = new Scanner(url.openStream());
                String stockInfo = "";

                ArrayList<String> dates = new ArrayList<>();
                ArrayList<String> prices = new ArrayList<>();

                while (s.hasNext()) {

                    String[] array = s.nextLine().split(",");

                    dates.add(array[0]);
                    prices.add(array[5]);

                }
                GobalComapredates= dates;
                GobalComapreprices = prices;


                return stockInfo;

            }

            public Pane initialGUI() {

                HBox hbox = new HBox();
                HBox hboxBtn = new HBox();

                hbox.setSpacing(20);
                hboxBtn.setSpacing(20);
                GridPane pane = new GridPane();
                pane.setAlignment(Pos.TOP_CENTER);
                pane.setPadding(new Insets(10));
                pane.setHgap(10);
                pane.setVgap(10);
                tfStock = new TextField();
                tfStock.setPromptText("Enter Stock Symbol");
                tfStock.setFocusTraversable(false);

                start = new DatePicker();
                start.setPromptText("From");
                start.setFocusTraversable(false);
                end = new DatePicker();
                end.setPromptText("To");
                end.setFocusTraversable(false);
                Label lblstart = new Label("Search Stock");
                lblstart.setFont(new Font("Arial", 56));
                btnApply = new Button("Apply");
                btnApply.setPrefWidth(350);
                btnHome = new Button("Home");
                btnHome.setPrefWidth(350);



                hbox.getChildren().addAll(start, end);
                hboxBtn.getChildren().addAll(btnApply, btnHome);

                pane.add(lblstart, 0, 0);
                pane.add(tfStock, 0, 1);
                pane.add(hbox, 0, 2);
                pane.add(hboxBtn, 0, 3);


                btnHome.setOnAction(e -> {
                    sceneHome = new Scene(homePage(currentUser), 1200, 1000);
                    sceneHome.getStylesheets().add("/styleOne.css");
                    window.setScene(sceneHome);
                    window.setTitle("Home Page");
                    window.show();
                });


                //Action after Apply

                btnApply.setOnAction(e -> {
                    GridPane Applypane =new GridPane();
                    Applypane.setAlignment(Pos.CENTER);
                    Applypane.setHgap(10);
                    Applypane.setVgap(10);

                    LocalDate period1 = start.getValue();
                    LocalDate period2 = end.getValue();
                    int year1 = period1.getYear();
                    int month1 = period1.getMonthValue();
                    int day1 = period1.getDayOfMonth();
                    int year2 = period2.getYear();
                    int month2 = period2.getMonthValue();
                    int day2 = period2.getDayOfMonth();

                    long startPeriod = getPeriod(year1, month1, day1);
                    long endPeriod = getPeriod(year2, month2, day2);




                    String stockSymbol = tfStock.getText();



                    String strUrl = getURL(stockSymbol, startPeriod, endPeriod);

                    try {
                        getStockInfo(strUrl);
                        tenDaysStockPrice(stockSymbol);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }






                    LineChart lineChart = LineChart(Gdates,Gprices,tfStock.getText() + " Current Price: " + Gprices.get(Gprices.size() - 1));

                    LineChart lineChartOne = LineChart(GobalComapredates,GobalComapreprices,tfStock.getText() + " Current Price: " + Gprices.get(Gprices.size() - 1));
                    Applypane.add(lineChart,0,0);


                    tfStock.setOnKeyTyped(ee->{

                        pane.getChildren().remove(lineChart);
                        pane.getChildren().remove(lineChartOne);

                    });
                    start.setOnKeyTyped(ee->{
                        pane.getChildren().remove(lineChart);
                        pane.getChildren().remove(lineChartOne);
                    });
                    end.setOnKeyTyped(ee->{
                        pane.getChildren().remove(lineChart);
                        pane.getChildren().remove(lineChartOne);
                    });

                    pane.add(lineChart, 0, 4);
                    pane.add(lineChartOne,1,4);

                });


                Button btnBuy = new Button("Buy");
                TextField shareCount = new TextField();

                shareCount.setPromptText("Enter share count.");

                btnBuy.setDisable(true);

                btnBuy.setOnAction(e -> {

                    String shareCountString = shareCount.getText();

                    if (!shareCountString.equals("")) {
                        try {

                            int shareCountInteger = Integer.parseInt(shareCountString);
                            double currentPrice = getCurrentStockPrice(tfStock.getText());
                            double insideTotalPrice = (shareCountInteger * currentPrice);
                           // DoubleProperty price = new SimpleDoubleProperty(insideTotalPrice);



                            if (insideTotalPrice > currentUser.getCash()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("YOU DONT HAVE ENOUGH CASH TO MAKE THIS PURCHASE");
                                alert.setContentText("Depost Cash To Your Acount");
                                alert.showAndWait();
                                sceneMarket= new Scene( new MyDatePicker().initialGUI(),1200,1000);
                                sceneMarket.getStylesheets().add("/styleOne.css");
                                window.setScene(sceneMarket);
                                window.show();
                            } else {
                                currentUser.getWallet().addStock(tfStock.getText(), shareCountInteger,insideTotalPrice);
                                currentUser.setCash(currentUser.getCash() - (shareCountInteger * currentPrice));
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                currentUser.setHistory(new Date().toString() + " Bought "+shareCountString+" shares for $ "
                                        + String.valueOf(insideTotalPrice));
                                alert.setHeaderText("PURCHASE APPOVED");
                                alert.setContentText("Symbol: "+tfStock.getText() + "Share Amount: " + shareCountInteger + "Cost: "+(shareCountInteger * currentPrice));
                                alert.showAndWait();
                                sceneMarket= new Scene( new MyDatePicker().initialGUI(),1200,1000);
                                sceneMarket.getStylesheets().add("/styleOne.css");
                                window.setScene(sceneMarket);
                                window.show();


                               // System.out.println("Shares Bought: " + shareCountInteger + ", Amount Purchased: " + (shareCountInteger * currentPrice));
                            }
                        } catch (NumberFormatException | IOException error) {
                            error.printStackTrace();
                        }
                    }
                });
                NumberStringConverter converter = new NumberStringConverter();


                TextFormatter<Number> textFormatter = new TextFormatter<>(converter);


                shareCount.setTextFormatter(textFormatter);
                //start here
                shareCount.setOnKeyTyped(e-> {
                    StringProperty bindtotalprice = new SimpleStringProperty();
                    TextField textFieldTotalprice = new TextField();
                    textFieldTotalprice.setStyle("-fx-background-color: green;");

                    textFieldTotalprice.setEditable(false);

                    double totalprice=0;
                    try {
                        if(!shareCount.getText().isEmpty()) {
                            totalprice = getCurrentStockPrice(tfStock.getText()) * Double.parseDouble(shareCount.getText());
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    bindtotalprice.setValue("$ " + Math.round(totalprice * 100) / 100.0);

                    textFieldTotalprice.textProperty().bindBidirectional(bindtotalprice);

                    if(totalprice>=1) {
                        btnBuy.setDisable(false);
                    }else {
                        btnBuy.setDisable(true);
                    }

                    pane.add(textFieldTotalprice,0,5);

                });


                HBox hboxbuyShares = new HBox();
                hboxbuyShares.getChildren().addAll(shareCount, btnBuy);



                pane.add(hboxbuyShares, 0, 6);

                return pane;

            }


            public long getPeriod(int y, int m, int d) {
                Calendar cal = Calendar.getInstance();
                cal.set(y, m - 1, d);
                Date date = cal.getTime();
                return date.getTime() / 1000;
            }

            //modifying the link
            public String getURL(String stockSymbol, long period1, long period2) {
                String urlTemplate = "https://query1.finance.yahoo.com/v7/finance/download/%s?period1=%d&period2=%d&interval=1d&events=history&includeAdjustedClose=true";
                String urlStr = String.format(urlTemplate, stockSymbol, period1, period2);
                return urlStr;
            }

            //reading dates and prices
            public String getStockInfo(String link) throws IOException {
                URL url = new URL(link);
                Scanner s = new Scanner(url.openStream());

                String stockInfo = "";

                ArrayList<String> dates = new ArrayList<>();
                ArrayList<String> prices = new ArrayList<>();

                while (s.hasNext()) {

                    String[] array = s.nextLine().split(",");

                    dates.add(array[0]);
                    prices.add(array[5]);

                }
                Gdates = dates;
                Gprices = prices;


                return stockInfo;

            }

            //mydatepicker ends
        }

        public LineChart LineChart(ArrayList<String> dates, ArrayList<String> prices,String name){
                CategoryAxis xAxis = new CategoryAxis();
                 NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Days");
                yAxis.setLabel("closed Price");
             LineChart lineChart = new LineChart<>(xAxis, yAxis);

            XYChart.Series<String, Number> series1 = new XYChart.Series<>();


        for (int i=1; i<dates.size(); i++) {

            series1.getData().add(new XYChart.Data<>(dates.get(i), Double.valueOf(prices.get(i))));
        }
            series1.setName(name);

        lineChart.getData().add(series1);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(5);
            dropShadow.setOffsetY(5);
            dropShadow.setColor(Color.GRAY);
            lineChart.setEffect(dropShadow);



            return lineChart;
    }




    public Pane accountPage(){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
        //only accepts numbers
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            return change.getControlNewText().matches("\\d*") ? change : null;
        });

        TextField tfMoney = new TextField();
        tfMoney.setPromptText("$$$$$$$$");
        tfMoney.focusTraversableProperty();
        tfMoney.setTextFormatter(formatter);

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setPrefWidth(200);
        btnDeposit.setDisable(true);
        Button btnWithdraw = new Button("Withdrawal");
        btnWithdraw.setPrefWidth(200);
        btnWithdraw.setDisable(true);
        hBox.getChildren().addAll(btnWithdraw,btnDeposit);
        Button btnHome = new Button("Home");
        btnHome.setPrefWidth(500);
        double value = currentUser.getCash();
        String formatted = String.format("%.2f", value);
        Label cashText = new Label("Balance $ "+ formatted);
        cashText.setFont( new Font(36));
        pane.add(cashText,0,0);
        pane.add(tfMoney,0,1);
        pane.add(hBox,0,2);
        pane.add(btnHome,0,4);
        pane.add(barChart(),0,3);

        tfMoney.setOnKeyTyped(e->{
            if(currentUser.getCash()>= parseDouble(tfMoney.getText())){
                btnWithdraw.setDisable(false);
                btnDeposit.setDisable(false);
            } else if (currentUser.getCash()<= parseDouble(tfMoney.getText())) {
                btnWithdraw.setDisable(true);
            }

        });
        btnWithdraw.setOnAction(e->{
            double curCash = currentUser.getCash();
            currentUser.setCash(curCash- parseDouble(tfMoney.getText()));
            currentUser.setHistory(new Date().toString() + " Withdrew $ "+tfMoney.getText());
            sceneAccount= new Scene(accountPage(),1200,1200);
            sceneAccount.getStylesheets().add("/styleOne.css");
            window.setScene(sceneAccount);
            window.show();
        });
        btnDeposit.setOnAction(e->{
            double curCash = currentUser.getCash();
            currentUser.setCash(curCash+Double.parseDouble(tfMoney.getText()));
            currentUser.setHistory(new Date().toString() + " Deposited $ "+tfMoney.getText());
            sceneAccount= new Scene(accountPage(),1200,1200);
            sceneAccount.getStylesheets().add("/styleOne.css");
            window.setScene(sceneAccount);
            window.show();
        });
        btnHome.setOnAction(e->{
            sceneHome = new Scene(homePage(currentUser), 1200, 1000);
            sceneHome.getStylesheets().add("/styleOne.css");
            window.setScene(sceneHome);
            window.setTitle("Home Page");
            window.show();
        });


        return pane;
    }

    public Pane listView() {
        GridPane pane = new GridPane();

        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);

            ListView<String> listView = new ListView<>();
            listView.setStyle("-fx-background-color: blue;");
            listView.setPrefSize(500,500);
            listView.setStyle("-fx-font-size: 10pt;");
//       Create a list of items to be displayed in the ListView
        System.out.println(currentUser.getHistory().toString());
        String rec = currentUser.getHistory().replace('[',' ');
        rec=rec.replace(']',' ');
        String [] stockRecds = rec.split(",");

        ArrayList<String> records = new ArrayList<>();
        records.addAll(Arrays.asList(stockRecds));
        List<String> items = records;

        listView.setItems(observableList(items));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);
        listView.setEffect(dropShadow);





        Label lblHistory = new Label("Stock Transactions");
        lblHistory.setAlignment(Pos.CENTER);
        pane.add(lblHistory,0,0);
        pane.add(listView, 0, 1);
        return pane;
    }

    public Pane walletPane(){
        GridPane pane = new GridPane();
        GridPane paneSell = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        paneSell.setAlignment(Pos.CENTER);
        paneSell.setVgap(10);
        paneSell.setHgap(10);

        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);

        Label lblsyb = new Label("Stock Symbol");
        TextField tfsymb = new TextField();

        hBox1.getChildren().addAll(lblsyb,tfsymb);

        Label lblshare = new Label("Share              ");
        TextField tfshare = new TextField();


        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);

        hBox2.getChildren().addAll(lblshare,tfshare);

        Button btnsell = new Button("sell");
        btnsell.setPrefSize(500,50);
        Button btnhome = new Button("home");
        btnhome.setPrefSize(500,50);

        paneSell.add(hBox1,0,0);
        paneSell.add(hBox2, 0,1);
        paneSell.add(btnsell,0,2);
        paneSell.add(btnhome,0,3);
        btnhome.setOnAction(e->{
            sceneHome = new Scene(homePage(currentUser), 1200, 1000);
            sceneHome.getStylesheets().add("/styleOne.css");
            window.setScene(sceneHome);
            window.setTitle("Home Page");
            window.show();
        });

        //list view in 0,1 postion
        ListView <String> listView = new ListView<>();
        listView.setPrefSize(500,500);
        listView.setStyle("-fx-font-size: 12pt;");
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), cell);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            cell.textProperty().bind(cell.itemProperty());
            cell.setOnMouseEntered(event -> fadeIn.playFromStart());

            return cell;
        });

//       Create a list of items to be displayed in the ListView
        String rec = currentUser.getWallet().toString().replace('[',' ');
        rec=rec.replace(']',' ');
        String [] stockRecds = rec.split(",");

        ArrayList<String> records = new ArrayList<>();
        records.addAll(Arrays.asList(stockRecds));
        List<String> items = records;


        listView.setItems(observableList(items));

        pane.add(listView,1,0);
        pane.add(paneSell,0,0);
        btnsell.setDisable(true);

        btnsell.setOnAction(e->{

            //System.out.println(currentUser.getWallet().findUserStocks(tfsymb.getText()));
            try {
                double sellmoney= new MyDatePicker().getCurrentStockPrice(tfsymb.getText());
                double money =sellmoney * Integer.parseInt(tfshare.getText());
                double buyPrice= (currentUser.getWallet().findUserStocksPrice(tfsymb.getText()) / currentUser.getWallet().findUserStocks(tfsymb.getText())) * Integer.parseInt(tfshare.getText())  ;
                double reTurn = money - buyPrice;
                reTurn=Math.round(reTurn * 100.0) / 100.0;
                currentUser.setCash(currentUser.getCash()+ money);
                currentUser.getWallet().addStock(tfsymb.getText(),-Integer.parseInt(tfshare.getText()),-money);
                currentUser.setHistory(new Date().toString() + " Sold "+tfshare.getText()+" shares for $ "
                        + String.valueOf(money) +" Return: " + String.valueOf(reTurn));

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(tfshare.getText() +" sold for " + String.valueOf(money));
                alert.showAndWait();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            sceneMarket= new Scene(walletPane(),1200,1000);
            sceneMarket.getStylesheets().add("/styleOne.css");
            window.setScene(sceneMarket);
            window.show();

        });
        TextField tfSellingPrice = new TextField();
        tfSellingPrice.setDisable(true);

        tfsymb.setOnKeyTyped(e->{
        if(tfsymb.getText().equals("")){
            ownShare=0;
            btnsell.setDisable(true);


        }else if (ownShare< Integer.parseInt(tfshare.getText())){
            btnsell.setDisable(true);
        }
        });
        TextField lblownShare = new TextField();
        lblownShare.setPrefSize(200,50);
        tfshare.setOnKeyTyped(e->{
            NumberStringConverter converter = new NumberStringConverter();


            TextFormatter<Number> textFormatter = new TextFormatter<>(converter);


            tfshare.setTextFormatter(textFormatter);
            ownShare = currentUser.getWallet().findUserStocks(tfsymb.getText());
            int sellCount = Integer.parseInt(tfshare.getText());

            lblownShare.setText("Shares: " + String.valueOf(ownShare));
            try {
                double sellmoney= new MyDatePicker().getCurrentStockPrice(tfsymb.getText());
                double totalsellpice = sellmoney * Integer.parseInt(tfshare.getText());
                tfSellingPrice.setText("Total selling price: "+String.valueOf(totalsellpice));
              //  pane.add(tfSellingPrice,0,3);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(tfsymb.getText().equals("")){
                ownShare=0;
                btnsell.setDisable(true);


            }
            if (ownShare< sellCount){
                btnsell.setDisable(true);
            }
            else if(tfshare.getLength()<1){
                btnsell.setDisable(true);
            }else
                btnsell.setDisable(false);


        });
        lblownShare.setStyle("-fx-background-color: green;");
        lblownShare.setDisable(true);
        tfSellingPrice.setStyle("-fx-background-color: red;");
        pane.add(tfSellingPrice,0,3);
        pane.add(lblownShare,0,4);
        return pane;
    }
    public Pane barChart() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");

        // Set up the y-axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        double stockValue=0;
        for(int i=0; i<currentUser.getWallet().stocks.size();i++) {
            stockValue += currentUser.getWallet().stocks.get(i).getBuyPrice();
        }
        double value = stockValue;
        String formatted = String.format("%.2f", value);
        barChart.setTitle("Investment value: " + String.valueOf(formatted));

        // Add some data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Stocks");

        for(int i=0; i<currentUser.getWallet().stocks.size();i++) {
            series.getData().add(new XYChart.Data<>(currentUser.getWallet().stocks.get(i).getStockName(), currentUser.getWallet().stocks.get(i).getBuyPrice()));
        }



        barChart.getData().add(series);
        barChart.setAnimated(true);

        // Set the gap between bars and categories
        barChart.setCategoryGap(10);
        barChart.setBarGap(5);



        pane.add(barChart,0,0);


        return pane;

    }


}