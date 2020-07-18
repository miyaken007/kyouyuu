<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Style-Type" content="text/css">
        <title>写真投稿システム</title>
    </head>
    <body>
        <h1> 写真一覧画面 </h1>
        <p>  このページは投稿された写真一覧が表示されます。</p>

<?php
  $images = array();  // 画像ファイル名のリストを格納する配列
  $num = 4; // 1ページに表示する画像の枚数

  // 画像フォルダから画像ファイル名を読み込む
  if ($handle = opendir('./upload')){
    while ($entry = readdir($handle)){
      // 「.」および「..」でないとき、ファイル名を配列に追加
      if ($entry != "." && $entry != ".."){
        $images[] = $entry;
      }
    }
    closedir($handle);
  }
?>

<?php
    if (count($images) > 0){
      // 指定枚数ごとに画像ファイル名を分割
      $images = array_chunk($images, $num);
      // ページ数指定、基本は0ページ目を指す
      $page = 0;
      // GETでページ数が指定されていた場合
      // URLを書き換えて、文字列が送られてくることもあるから、
      // 対策として送られてきた値が$_GET['page']で数字であるかチェックしている
      if (isset($_GET['page']) && is_numeric($_GET['page'])){
        $page = intval($_GET['page']) - 1;
        if (!isset($images[$page])){
          $page = 0;
        }
      }

      // 画像の表示
      foreach ($images[$page] as $img){
        echo '<img src="./upload/' . $img . '">';
      }

      // ページ数リンク
      echo '<p>';
      for ($i = 1; $i <= count($images); $i++){
        echo '<a href="photo_list.php?page=' . $i . '">' . $i . '</a>&nbsp;';
      }
      echo '</p>';
    } else {
      echo '<p>画像はまだありません。</p>';
    }
  ?>

<?php
var_dump(count($images));
var_dump($images)
?>

    <form method="post" action="page_login.php">
        <button type="submit" name="Btn1" value="Btn1">logout</button>
    </form>


    </body>
</html>