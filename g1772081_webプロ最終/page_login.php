<?php
$passlist=array( 'hogehoge' => 'hogepass', 'hoge2' => 'hoge2pass');

if(!isset($_POST['user']))
{
    echo_auth_page("ログイン");
    exit;
}
$user=$_POST['user'];
$pass=$_POST['pass'];


if( (!isset($passlist[$user])) || $passlist[$user] != $pass)
{
    echo_auth_page("パスワードが違います");
    exit;
}

if($_POST['transition']=="trans_hello")
{
    echo_hello_page($user);
}
else
{
   echo_auth_page($msg);
}



////////////////////////////////////////////////////////////////////////
function echo_auth_page($msg)
{
global $user;
global $pass;

echo <<<EOT
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Style-Type" content="text/css">
        <title>写真投稿システム</title>
    </head>
    <body>

$msg
    <form method="POST" action="page_login.php">
        username <input type="text" name="user" value=$user><br>
        password <input type="password" name="pass" value=$pass><br>
        <input type="hidden" name="transition" value="trans_hello">
        <button type="submit" name="login" value="login">Login</button>
    </form>
    </body>
</html>
EOT;
}
////////////////////////////////////////////////////////////////////////
function echo_hello_page($who)
{
global $user;
global $pass;

echo <<<EOT
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Style-Type" content="text/css">
        <title>写真投稿システム</title>
    </head>
    <body>
    <p> このページは写真投稿用です。</p>
    こんにちは $who さん



$msg
    <form method="POST" action="page_input.php">
        <input type="hidden" name="user" value=$user>
        <input type="hidden" name="pass" value=$pass>
        <button type="submit" name="post" value="post">新規投稿</button>
    </form>
    </body>
</html>
EOT;
}
?>
