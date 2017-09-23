# zunda-devstudy
イマドキの開発手法を実践しながらプロダクトを作成する

# 起動方法
+ 資材をクローン
    * https://github.com/zunda-cafe/zunda-devstudy.git
+ postgreSQLをインストールして起動
    * postgresSQLから本体をダウンロード
    * pg_ctl -D /usr/local/pgsql/data -l /var/log/postgres start
        * macの場合
    * dbとロールを作る
        * データベース blogdbとロールpostgresを作成する
        + ./schema.sql, ./data.sqlを実行しテーブルとデータを入れる
+ APを実行する
    * ./src/main/java/ZundaBlogApplicationをRunする

### Build status
[![Build Status](https://travis-ci.org/zunda-cafe/zunda-devstudy.svg?branch=master)](https://travis-ci.org/zunda-cafe/zunda-devstudy)
