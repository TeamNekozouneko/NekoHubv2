<h1 align="center">NekoHub v2</h1>
<p align="center">NekoHub v2はNekoHubの改良型です。BungeeCordとSpigotに対応しています。</p>

## 注意
一部の機能はねこぞう鯖向けに作成されています。  
そのためソースコードを書き換え必要な部分がある可能性があります。

## 仕組み
BungeeCordとSpigotサーバーへの接続はプラグインメッセージという機能を使っています。

Spigotから送信されてくるプラグインのメッセージをBungeeCordの`PluginMessageEvent`で送信されてきたメッセージを回収し、
中身を読みチャンネル名に応じた処理を実行させます。

逆は`Bukkit.getMessenger().registerIncomingPluginChannel()`で返ってくるチャンネルを登録し、
そこで返ってきたデータを読み取り処理を実行します。

