cd /home/fengwenhan/code/TestPhosphor

ls

mvn compile

java -jar /home/fengwenhan/code/phosphor/Phosphor/target/Phosphor-0.0.5-SNAPSHOT.jar /home/fengwenhan/code/TestPhosphor/target/classes/ /home/fengwenhan/data/phosphor_test/test_out/

#java -Xbootclasspath/a:/home/fengwenhan/code/phosphor/Phosphor/target/Phosphor-0.0.5-SNAPSHOT.jar -javaagent:/home/fengwenhan/code/phosphor/Phosphor/target/Phosphor-0.0.5-SNAPSHOT.jar=cacheDir=/home/fengwenhan/data/phosphor_cache -cp /home/fengwenhan/data/phosphor_test/test_out iscas.tcse.fwh.Main