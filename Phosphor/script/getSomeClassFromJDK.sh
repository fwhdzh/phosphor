cd /home/fengwenhan/tmp/phosphor_tmp
jar xf /home/fengwenhan/data/phosphor_test/fav-jre-inst/jre/lib/rt.jar java/io/FileOutputStream.class
cp java/io/FileOutputStream.class .
jar xf /home/fengwenhan/data/phosphor_test/fav-jre-inst/jre/lib/rt.jar java/net/SocketOutputStream.class
cp java/net/SocketOutputStream.class .
rm -r ./java
