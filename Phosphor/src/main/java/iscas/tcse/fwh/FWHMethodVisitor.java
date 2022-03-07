package iscas.tcse.fwh;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class FWHMethodVisitor extends MethodVisitor {

    public static String METHOD_SUFFIX = "$$FWH";

    public FWHMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }




    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        //super.visitMethodInsn(opcode, owner, name+FWHMethodVisitor.METHOD_SUFFIX, descriptor, isInterface);
        if (owner.equals("java/io/FileOutputStream") && name.equals("write") && descriptor.equals("(IZ)V")) {
            super.visitMethodInsn(INVOKESTATIC, "iscas/tcse/fwh/FWHMethodVisitor",
                    "printSome", "()V", false);
            super.visitMethodInsn(opcode, owner, name+FWHMethodVisitor.METHOD_SUFFIX, descriptor, isInterface);
        } else {
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
    public void visitEnd() {
        //System.out.println("VisitEnd in tainting process");
        super.visitEnd();
    }

    public static void printSome() {
        System.out.println("FWH print some in FWHMethodVisitor!");
    }

    public static void printVisitCode() {
        System.out.println("FWH begin to execute visitCode!");
    }

}
