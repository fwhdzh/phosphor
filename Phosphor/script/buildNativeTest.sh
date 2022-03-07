cd /home/fengwenhan/code/TestPhosphor
mvn compile
java -jar /home/fengwenhan/code/phosphor/Phosphor/target/Phosphor-0.0.5-SNAPSHOT.jar /home/fengwenhan/code/TestPhosphor/target/classes/ /home/fengwenhan/data/phosphor_test/test_out/

cd /home/fengwenhan/data/phosphor_test/test_out/
ls

old_Java=$JAVA_HOME
echo $old_Java
JAVA_HOME=/home/fengwenhan/data/phosphor_test/fav-jre-inst/
echo $JAVA_HOME

javah fwh.NativeMain
cp /home/fengwenhan/code/TestPhosphor/src/main/resources/Hello.cpp .
sed -i "s@<jni.h>@<${JAVA_HOME}include/jni.h>@g" fwh_NativeMain.h
g++ -I /home/fengwenhan/data/phosphor_test/fav-jre-inst/include/linux Hello.cpp -fpic -shared -o Hello.so

JAVA_HOME=$old_Java
echo $JAVA_HOME