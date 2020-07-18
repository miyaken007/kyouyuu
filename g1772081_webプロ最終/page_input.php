<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Style-Type" content="text/css">
        <title>写真投稿システム</title>
    </head>
    <body>
        <h1> 写真投稿フォーム </h1>
        <h2> 写真投稿入力画面 </h2>
        <p>  このページは写真投稿入力画面です。</p>

<?php
$msg = null;
// もし$_FILES['upfile']があって、一時的なファイル名の$_FILES['upfile']が
// POSTでアップロードされたファイルだったら
if(isset($_FILES['upfile']) && is_uploaded_file($_FILES['upfile']['tmp_name'])){
    $old_name = $_FILES['upfile']['tmp_name'];
//  もしuploadというフォルダーがなければ
    if(!file_exists('upload')){
        mkdir('upload');
    }
    $new_name = date("YmdHis"); //ベースとなるファイル名は日付
    $new_name .= mt_rand(); //ランダムな数字も追加
    switch (exif_imagetype($_FILES['upfile']['tmp_name'])){
        case IMAGETYPE_JPEG:
            $new_name .= '.jpg';
            break;
        case IMAGETYPE_GIF:
            $new_name .= '.gif';
            break;
        case IMAGETYPE_PNG:
            $new_name .= '.png';
            break;
        default:
            header('Location: upload.php');
            exit();
    }
//  もし一時的なファイル名の$_FILES['upfile']ファイルを
//  upload/basename($_FILES['upfile']['name'])ファイルに移動したら
    $gazou = basename($_FILES['upfile']['name']);
    if(move_uploaded_file($old_name, 'upload/'.$new_name)){
        $msg = $gazou. 'のアップロードに成功しました';
    }else {
        $msg = 'アップロードに失敗しました';
    }
}
?>


<p>アップロードするファイルを指定してください。</p>
<p><form action="page_input.php" method="post" enctype="multipart/form-data">
<input type="file" name="upfile">
<input type="submit" value="読み込み" name="yomikomi">
</form>
</p>
<?php
if(isset($msg) && $msg == true){
    echo '<p>'. $msg . '</p>';
}
?>
<a href="page_input.php">戻る</a>


    <form method="POST" action="page_confirm.php">
        <button type="submit" name="post" value="post">投稿確認</button>
    </form>

    </body>
</html>