rm -r /home/fengwenhan/data/phosphor_test/fav-jre-inst

java -jar /home/fengwenhan/code/phosphor/Phosphor/target/Phosphor-0.0.5-SNAPSHOT.jar /home/fengwenhan/util/java/jdk1.8.0_281 /home/fengwenhan/data/phosphor_test/fav-jre-inst

rm /home/fengwenhan/data/phosphor_test/fav-jre-inst/jre/lib/jce.jar

cp /home/fengwenhan/code/jdk8u262-b10/jre/lib/jce.jar /home/fengwenhan/data/phosphor_test/fav-jre-inst/jre/lib

rm -r /home/fengwenhan/data/phosphor_test/fav-jre-inst/jre/lib/security/policy/*

cp -r /home/fengwenhan/code/jdk8u262-b10/jre/lib/security/policy/* /home/fengwenhan/data/phosphor_test/fav-jre-inst/jre/lib/security/policy
