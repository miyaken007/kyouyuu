<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Style-Type" content="text/css">
        <title>写真投稿システム</title>
    </head>
    <body>
        <h1> 写真投稿フォーム </h1>
        <h2> 写真投稿確認画面 </h2>
        <p>  このページは写真投稿確認画面です。</p>

<?php
    $msg = null;
    $msg = $gazou. 'をアップロードします';
?>

    <form method="post" action="post_fin.php">
        <button type="submit" name="Btn1" value="Btn1">投稿</button>
    </form>

    <form method="post" action="page_input.php">
        <button type="submit" name="Btn1" value="">修正</button>
    </form>
    </body>
</html>