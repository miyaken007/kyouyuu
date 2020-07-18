/*
 *
 * プログラミング演習B  2018年 最終プロジェクト課題用 原型プログラム Toshiaki Yokoi
 *
 *　作品名：　世界遺産クイズ
 *　作成者学番：g1772081　　　　　　　　　　氏名：宮北健太
 *
 *
 *　１．プロジェクト作品の作成は、このプログラムを元にしてください。
 *
 *     1) 大本の土台は，BorderPaneを用意し，その上にAnchorPane（自由に配置
 *        できるPane）を貼り付け，その上にラベルや，ボタン，テキストの部品を
 *        配置しています．
 *     2) 画面は，３枚同時に用意してあります（開始画面，クイズA画面，クイズB画面）．
 *　   3) 最初にAnchorPaneで部品を配置した「開始画面」が表示されたあと，
 *        クリックされたクイズの選択ボタンによって，クイズAのAnchorPaneか，
 *        クイズBのAnchorPaneを，土台のBorderPaneの中央に貼り付け直しています．
 *
 *　２．その上で、授業中で解説した様々な技術を組み込んだり、ボタン等を置き換え
 *     たりして、作品を仕上げてください。
 *     授業解説した仕組みを使って置き換えてもかまいません。
 *
 *　３．解説したさまざまな技術も可能な限り、取り入れてみましょう。
 *　　　例：　グラフィクス部品、Canvas、半透明、マウスカーソル変更
 *　　　　　　DropShadow、ラジオボタン、チェックボックス、タイマー機能．
 *
 *　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　以上
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.text.*;
import javafx.util.Duration;

public class Project2018a extends Application {

    private Label lb1,lb2, lb3, lba1,lba2, lba3; // 画面上部のラベル
    private Button btA, btB, btC; // クイズ選択ボタン
    private Button btA1, btA2, btA3, btB1, btB2, btB3; // クイズA,クイズBの中のボタン
    private AnchorPane ap0, ap1, apA, apB, apC; // 大元のPane,開始画面Pane,クイズAのPane,クイズBのPane
    private BorderPane bp;  //　パネルを切り替えるための土台のPane
    private Text text1,textA,textB; // 各画面に表示するText部品
    private GraphicsContext gc;// イベントハンドラーでgcを使えるようにここで宣言します。
    private Image im1, im2, im3;//
    
    int n = 15;//
    Timer timer1, timer2,timer3, timer4,timer5, timer6;//
    TimerTask tt1,tt2;//
    long answeringTime = 15;// 秒
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception  {
        
        //　画面の構成の構成は，別の図解資料も見ながら確認してください． 
        //
        //画像の描画　gif、jpegなど可能
        //ファイルの読み込み　ファイルがプロジェクトフォルダのimages内にあるとき
        Image im1 = new Image(Paths.get( "./images/万里の長城1.jpg").toUri().toString());
        Image im2 = new Image(Paths.get( "./images/グランドキャニオン1.jpg" ).toUri().toString());
        Image im3 = new Image(Paths.get( "./images/バターリャの修道院.jpg" ).toUri().toString());
        
        ImageView imv1 = new ImageView(im1); //JavaFXで描画可能な形式に変換
        ImageView imv2 = new ImageView(im2);
        ImageView imv3 = new ImageView(im3);
        
        imv1.setLayoutX(0);
        imv1.setLayoutY(20);
        imv2.setLayoutX(0);
        imv2.setLayoutY(20);
        imv3.setLayoutX(0);
        imv3.setLayoutY(20);
        
        // 大元の土台に貼り付けるタイトルラベルの作成とAnchoPane上での配置位置指定．
        lb1 = new Label("~~　世界遺産クイズ　~~");
        lb1.setLayoutX(150);// AnchorPane ap0条での配置位置を指定する
        lb1.setLayoutY(20); 
        lb1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        lb1.setFont(Font.font("TimesRoman",FontWeight.BOLD,24));

        lba1 = new Label("・このクイズは制限時間15秒。");//
        lba1.setLayoutX(0);
        lba1.setLayoutY(330);
        
        lba2 = new Label("・クイズAが正解出来たらクイズBを解答する。");//
        lba2.setLayoutX(0);
        lba2.setLayoutY(410);
        
        lba3 = new Label("・クイズAを選択すると秒数カウントが開始されます。");//
        lba3.setLayoutX(0);
        lba3.setLayoutY(490);
        
        
        lb2 = new Label("どこの国世界遺産でしょうか？正解だと思う解答を選んで下さい。");//
        lb2.setLayoutX(0);
        lb2.setLayoutY(350);
        
        lb3 = new Label("どこの国世界遺産でしょうか？正解だと思う解答を選んで下さい。");//
        lb3.setLayoutX(0);
        lb3.setLayoutY(390);
        //
        // 大元の土台のPaneの作成
        ap0 = new AnchorPane();
        ap0.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
        //
        // 開始画面のPaneの作成
        ap1 = new AnchorPane();
        ap1.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
        ap1.getChildren().add(imv1);//AnchorPaneへ配置
        //
        // クイズAのPaneの作成
        apA = new AnchorPane();
        apA.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        apA.getChildren().add(imv2);//AnchorPaneへ配置
        //
        // クイズBのPaneの作成
        apB = new AnchorPane();
        apB.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
        apB.getChildren().add(imv3);//AnchorPaneへ配置
        // 
        //　大元の土台のPaneの下におくクイズ選択ボタン２つの作成
        btA = new Button("クイズＡ");
        btB = new Button("クイズＢ");
        btC = new Button("終了");//
        btA.setLayoutX(150);
        btA.setLayoutY(560);
        btB.setLayoutX(380);
        btB.setLayoutY(560);
        btC.setLayoutX(275);//
        btC.setLayoutY(560);//
        //　クイズ選択ボタンへのイベントハンドラーの組み込み
        btA.setOnAction(new ButtonEventHandler());
        btB.setOnAction(new ButtonEventHandler());
        btC.setOnAction(new ButtonEventHandler());//
        //
        //　クイズAのページ内の解答ボタンの作成と，イベントハンドラーの組み込み
        btA1 = new Button("アメリカ合衆国");
        btA2 = new Button("カナダ");
        btA3 = new Button("ニュージーランド");//
        btA1.setLayoutX(75);
        btA1.setLayoutY(450);
        btA2.setLayoutX(250);
        btA2.setLayoutY(450);
        btA3.setLayoutX(370);//
        btA3.setLayoutY(450);//
        //　解答ボタンへのイベントハンドラーの組み込み
        btA1.setOnAction(new ButtonEventHandlerA());
        btA2.setOnAction(new ButtonEventHandlerA());
        btA3.setOnAction(new ButtonEventHandlerA());//
        
        //
        //　クイズBのページ内の解答ボタンの作成と，イベントハンドラーの組み込み
        btB1 = new Button("イタリア");
        btB2 = new Button("ポルトガル");
        btB3 = new Button("アルバニア共和国");//
        btB1.setLayoutX(75);
        btB1.setLayoutY(450);
        btB2.setLayoutX(210);
        btB2.setLayoutY(450);
        btB3.setLayoutX(370);//
        btB3.setLayoutY(450);//
        //　解答ボタンへのイベントハンドラーの組み込み
        btB1.setOnAction(new ButtonEventHandlerB());
        btB2.setOnAction(new ButtonEventHandlerB());
        btB3.setOnAction(new ButtonEventHandlerB());//

        //
        //開始画面，クイズA画面，クイズB画面の中央に配置するText部品の生成と位置指定
        //　開始画面の中央に配置するText部品
        Text text1 =new Text("開始ページ （クイズ選択ページ）");
        text1.setLayoutX(0);
        text1.setLayoutY(10);
        
        //
        //　クイズAの画面に配置するText部品
        Text textA =new Text("クイズＡ ページ");
        textA.setLayoutX(0);
        textA.setLayoutY(10);
        //
        //　クイズBの画面に配置するText部品
        Text textB =new Text("クイズＢ ページ");
        textB.setLayoutX(0);
        textB.setLayoutY(10);
        //
        //　中央に配置するBorderPaneの作成
        //　開始画面，クイズA，クイズBを貼り付ける相手
        bp = new BorderPane();
        bp.setPrefSize(550, 500);
        bp.setLayoutX(25);
        bp.setLayoutY(50);
        //
        //　大元の土台ap0にラベル，BorderPane，ボタン2つを貼り付ける
        //　　それぞれの位置は，各部品生成時に指定してあり，
        //　　その値に従って，AnchorPaneであるap0内で位置決めされている
        ap0.getChildren().addAll(lb1,bp,btA,btB,btC);
        //
        // 開始画面ap1にテキスト部品を貼り付け，自身をBorderPane bpに貼り付けている
        ap1.getChildren().addAll(text1,lba1, lba2, lba3);
        bp.setCenter(ap1);
        //
        // クイズA画面にテキスト部品とボタンを貼り付けている
        //　　この時点では，BprderPane bpには貼り付けない
        //　　クイズAボタンが押されたときに　bpに貼り付ける
        apA.getChildren().addAll(textA,btA1,btA2,btA3, lb2);
        //
        // クイズB画面にテキスト部品とボタンを貼り付けている
        //　　この時点では，BprderPane bpには貼り付けない
        //　　クイズBボタンが押されたときに　bpに貼り付ける
        apB.getChildren().addAll(textB,btB1,btB2,btB3, lb3);
        //シーンの作成 (額縁の土台) 
        Scene sc = new Scene(ap0, 600, 600);
        //ステージへの追加（額縁へ）
        stage.setScene(sc);
        //ステージの表示（額縁のタイトル，表示指示）
        stage.setTitle("My Graphics　with JavaFX");
        stage.show();
        
    }

// 開始画面のクイズ選択ボタン用のイベントハンドラー
    class ButtonEventHandler implements EventHandler<ActionEvent> {

        public void handle(ActionEvent e) {
            Button tb = (Button) e.getSource();
            if (tb.getText().equals("クイズＡ")) {
                //　AnchorPane apA を中央のBprderPane bpへ貼る
                bp.setCenter(apA);
                
                //Timerは、一定時間後の処理を指定できる部品
                timer1 = new Timer();
                // Timer には、一定時間後に実行するTimerTaskを生成して設定する（繰り返しも可能）
                tt1 = new TimerTask() {
                //run()には、実行内容を書く
                public void run() {
                //
                //ここでは、一定時間ごとに行う処理（今回は1秒ごと以下うんとダウン）を書く
                if (n == 0) {
                    //終了時刻　1秒ごとにnの値を減らし、n=0になったら終了処理
                    System.out.println("時間終了です！");
                    btA1.setOpacity(0);//ボタンを完全に見透明にしている
                    btA2.setOpacity(0);
                    btA3.setOpacity(0);
                    tt1.cancel();//タイマータスクを停止させる
                    timer1.cancel();//タイマーも停止させる
                    
                } else if (n > 0) {
                    //毎回の処理では、残り秒数に応じて、ボタンの不透明度を減らしている
                    btA1.setOpacity(1.0*n/answeringTime);
                    btA2.setOpacity(1.0*n/answeringTime);
                    btA3.setOpacity(1.0*n/answeringTime);
                    System.out.println("あと" + (n--) + "秒です。");
                }
            }
        };
            //timerTask ttを、0秒後に開始し、1000ミリ秒ごとに繰り返し実行する、という設定例
            timer1.schedule(tt1, 0L, 1000L);

            } else if (tb.getText().equals("クイズＢ")) {
                //　AnchorPane apB を中央のBprderPane bpへ貼る
                bp.setCenter(apB);
    
                //Timerは、一定時間後の処理を指定できる部品
                timer2 = new Timer();
                // Timer には、一定時間後に実行するTimerTaskを生成して設定する（繰り返しも可能）
                tt2 = new TimerTask() {
                //run()には、実行内容を書く
                public void run() {
                //
                //ここでは、一定時間ごとに行う処理（今回は1秒ごと以下うんとダウン）を書く
                if (n == 0) {
                    //終了時刻　1秒ごとにnの値を減らし、n=0になったら終了処理
                    
                    btB1.setOpacity(0);//ボタンを完全に見透明にしている
                    btB2.setOpacity(0);
                    btB3.setOpacity(0);
                    tt2.cancel();//タイマータスクを停止させる
                    timer2.cancel();//タイマーも停止させる
                    
                } else if (n > 0) {
                    //毎回の処理では、残り秒数に応じて、ボタンの不透明度を減らしている
                    btB1.setOpacity(1.0*n/answeringTime);
                    btB2.setOpacity(1.0*n/answeringTime);
                    btB3.setOpacity(1.0*n/answeringTime);
                }
            }
        };
            //timerTask ttを、0秒後に開始し、1000ミリ秒ごとに繰り返し実行する、という設定例
            timer2.schedule(tt2, 0L, 1000L);
            
            } else if (tb.getText().equals("終了")) {
                //　AnchorPane apB を中央のBprderPane bpへ貼る
                bp.setCenter(apC);
                System.exit(0);
            }
        }
    }

    // クイズＡの画面のボタン用のイベントハンドラー
    class ButtonEventHandlerA implements EventHandler<ActionEvent> {

        public void handle(ActionEvent e) {
            Button tb = (Button) e.getSource();
            if (tb.getText().equals("アメリカ合衆国")) {
                // クイズAの解答A国の場合の処理です．
                //   画像の表示，音の再生などを組み込みましょう
                // MP3形式サウンドの読み込み
                Path path1 = Paths.get("./sounds/Quiz-Correct_Answer01-1.mp3");
                String soundURL1 = path1.toUri().toString();
                AudioClip clip1 = new AudioClip(soundURL1);
                clip1.play();//ボタン１に音声１の再生を設定
                System.out.println("正解です。");
                System.out.println("ここは、グランドキャニオン国立公園です。");
                
            } else if (tb.getText().equals("カナダ")) {
                // クイズAの解答B国の場合の処理です．
                //   画像の表示，音の再生などを組み込みましょう
                // MP3形式サウンドの読み込み
                Path path2 = Paths.get("./sounds/Quiz-Wrong_Buzzer01-1.mp3");
                String soundURL2 = path2.toUri().toString();
                AudioClip clip2 = new AudioClip(soundURL2);
                clip2.play();//ボタン１に音声１の再生を設定
                System.out.println("不正解です。");
                
            } else if (tb.getText().equals("ニュージーランド")) {//
                // クイズAの解答C国の場合の処理です．
                //   画像の表示，音の再生などを組み込みましょう
                // MP3形式サウンドの読み込み
                Path path2 = Paths.get("./sounds/Quiz-Wrong_Buzzer01-1.mp3");
                String soundURL2 = path2.toUri().toString();
                AudioClip clip2 = new AudioClip(soundURL2);
                clip2.play();//ボタン１に音声１の再生を設定
                System.out.println("不正解です。");    
            }
    }
    }

    // クイズＢの画面のボタン用のイベントハンドラー
    class ButtonEventHandlerB implements EventHandler<ActionEvent> {

        public void handle(ActionEvent e) {
            Button tb = (Button) e.getSource();
            if (tb.getText().equals("イタリア")) {
                // クイズBの解答B1の場合の処理です．
                //   画像の表示，音の再生などを組み込みましょう
                // MP3形式サウンドの読み込み
                Path path2 = Paths.get("./sounds/Quiz-Wrong_Buzzer01-1.mp3");
                String soundURL2 = path2.toUri().toString();
                AudioClip clip2 = new AudioClip(soundURL2);
                clip2.play();//ボタン１に音声１の再生を設定
                System.out.println("不正解です。");
                
            } else if (tb.getText().equals("ポルトガル")) {
                // クイズBの解答B2の場合の処理です．
                //   画像の表示，音の再生などを組み込みましょう
                // MP3形式サウンドの読み込み
                Path path1 = Paths.get("./sounds/Quiz-Correct_Answer01-1.mp3");
                String soundURL1 = path1.toUri().toString();
                AudioClip clip1 = new AudioClip(soundURL1);
                clip1.play();//ボタン１に音声１の再生を設定
                System.out.println("正解です。");
                System.out.println("ここは、バターリャの修道院です。");
                
            } else if (tb.getText().equals("アルバニア共和国")) {//
                // クイズBの解答B3の場合の処理です．
                //   画像の表示，音の再生などを組み込みましょう
                // MP3形式サウンドの読み込み
                Path path2 = Paths.get("./sounds/Quiz-Wrong_Buzzer01-1.mp3");
                String soundURL2 = path2.toUri().toString();
                AudioClip clip2 = new AudioClip(soundURL2);
                clip2.play();//ボタン１に音声１の再生を設定
                System.out.println("不正解です。");
            }
        }
    }
}