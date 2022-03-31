package iscas.tcse.fwh;

import edu.columbia.cs.psl.phosphor.Instrumenter;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.ArrayList;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.HashMap;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.List;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.Map;
import org.objectweb.asm.MethodVisitor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class FWHMethodVisitor extends MethodVisitor {

    static Map<String, List<String[]>> nativeMethodLib = new HashMap<>();

    static int testResult = 0;

    static {
        try {
            FileInputStream in = new FileInputStream("/home/fengwenhan/data/fwh_phosphor_scanner_test/1.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String str = null;
            String name = null;
            List<String[]> info = new ArrayList<>();
            while ((str = bufferedReader.readLine()) != null) {
                if (!str.equals("") && !str.contains(" ")) {    // is class Name
                    name = str;
                    continue;
                }
                if (str.contains(" ")) {
                    info.add(str.split(" "));
                    continue;
                }
                if (str.equals("")) {
                    nativeMethodLib.put(name, info);
                    name = null;
                    info = new ArrayList<>();
                }
            }
            in.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        for (Map.Entry<String, List<String[]>> entry : nativeMethodLib.entrySet()) {
            System.out.println(entry.getKey());
            for (String[] s : entry.getValue()) {
                System.out.println(s[0]+" "+s[1]);
            }
        }
        */

        //System.load("/home/fengwenhan/data/phosphor_test/test_out/Hello.so");
    }

    public static String METHOD_SUFFIX = "$$FWH";

    public FWHMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }




    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        boolean isNativeCall = false;
        //if (owner.equals("java/io/FileOutputStream") && (name.equals("write") || name.equals("writeBytes"))) {
        if (!Instrumenter.isIgnoredClass(owner) && owner.equals("java/io/FileOutputStream") && name.equals("write") && descriptor.equals("(IZ)V")) {
            if (nativeMethodLib.containsKey(owner)) {
                List<String[]> list = nativeMethodLib.get(owner);
                for (String[] s : nativeMethodLib.get(owner)) {
                    if (name.equals(s[0]) && descriptor.equals(s[1])) {
                        isNativeCall = true;
                        break;
                    }
                }
            }
        }

        if (isNativeCall) {
            super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
                   "printSome", "()V", false);
            //super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
            //       "record", "()V", false);
            super.visitMethodInsn(opcode, owner, name+FWHMethodVisitor.METHOD_SUFFIX, descriptor, isInterface);
        } else {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
        /*
        if (owner.equals("fwh/Main") && name.equals("printFlag")) {
            super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
                    "printRecord", "()V", false);
        }
         */



        //super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

        /*
        if (owner.equals("java/io/FileOutputStream") && name.equals("write") && descriptor.equals("(IZ)V")) {
            super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
                    "printSome", "()V", false);
            super.visitMethodInsn(opcode, owner, name+FWHMethodVisitor.METHOD_SUFFIX, descriptor, isInterface);
        } else {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
        */
    }

    @Override
    public void visitCode() {
        super.visitCode();
        /*
        super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
                "printVisitCode", "()V", false);
         */
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(99, 99);
        //super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    public void visitEnd() {
        //System.out.println("VisitEnd in tainting process");

        super.visitEnd();

    }

    //public native void Hello();

    public static void record() {
        testResult += 1;
    }

    public static void printRecord() {
        System.out.println("record is: " + testResult);
    }

    public static void printSome() {
        System.out.println("FWH print some in FWHMethodVisitor!");
    }

    public static void printVisitCode() {
        System.out.println("FWH begin to execute visitCode!");
    }



}
