package iscas.tcse.fwh;

//import edu.columbia.cs.psl.phosphor.Instrumenter;
//import edu.columbia.cs.psl.phosphor.struct.harmony.util.ArrayList;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.HashMap;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.List;
import edu.columbia.cs.psl.phosphor.struct.harmony.util.Map;
import org.objectweb.asm.MethodVisitor;

//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class FWHMethodVisitor extends MethodVisitor {

    static Map<String, List<String[]>> nativeMethodLib = new HashMap<>();

    static int testResult = 0;

    static String testString = "";

    public static String METHOD_SUFFIX = "$$FWH";

    public FWHMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    private boolean check(String owner, String name, String descriptor) {
        boolean result = true;
        if (owner.equals("java/io/FileOutputStream") && name.equals("write") && descriptor.equals("(IZ)V")) {
            return true;
        }
        if (owner.equals("java/io/FileInputStream") && name.equals("read0") && descriptor.equals("()I")) {
            return true;
        }
        if (owner.equals("java/net/SocketInputStream") && name.equals("socketRead0") && descriptor.equals("(Ljava/io/FileDescriptor;[BIII)I")) {
            return true;
        }

//        if (owner.equals("java/net/SocketOutputStream") && name.equals("socketWrite0") && descriptor.equals("(Ljava/io/FileDescriptor;[BII)V")) {
//            return true;
//        }


        if (owner.equals("java/io/FileOutputStream") && name.equals("writeBytes") && descriptor.equals("([BIIZ)V")) {
            return true;
        }
        return false;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        if (check(owner, name, descriptor)) {
            super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
                    "record", "()V", false);
            super.visitMethodInsn(opcode, owner, name+FWHMethodVisitor.METHOD_SUFFIX, descriptor, isInterface);
        }
        else if (owner.equals("java/net/SocketOutputStream") && name.equals("socketWrite0") && descriptor.equals("(Ljava/io/FileDescriptor;[BII)V")) {
//            String s = owner + " " + name + " " + descriptor + "\n";
//            super.visitLdcInsn(s);
//            super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
//                    "recordString", "(Ljava/lang/String;)V", false);
            super.visitMethodInsn(opcode, owner, name+FWHMethodVisitor.METHOD_SUFFIX, descriptor, isInterface);
        }
        else {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

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

    public static void recordString(String s) {
        testString = testString + s;
    }

    public static void printRecord() {
//        System.out.println("record is: " + testResult);
        System.out.println("recordString is: \n" + testString);
    }


    public static void printSome() {
        System.out.println("FWH print some in FWHMethodVisitor!");
    }

    public static void printVisitCode() {
        System.out.println("FWH begin to execute visitCode!");
    }



}
