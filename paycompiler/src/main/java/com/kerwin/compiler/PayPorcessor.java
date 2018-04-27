package com.kerwin.compiler;

import com.google.auto.service.AutoService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


@SupportedAnnotationTypes("com.kerwin.paysdk.annotation.PayMark")
@AutoService(Processor.class)
public class PayPorcessor extends AbstractProcessor {

    public static final String APPLICATIONID = "applicationId";


    private ProcessingEnvironment processingEnv;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        String applicationId = processingEnv.getOptions().get(APPLICATIONID);
        if (applicationId == null) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "no applicationId options set");
            return false;
        }

        createJavaFile(applicationId);
        return true;
    }

    private void createJavaFile(String applicationId) {

        String packageName = applicationId + ".wxapi";
        String className = packageName + ".WXPayEntryActivity";
        BufferedWriter writer = null;
        try {
            JavaFileObject sourceFile = processingEnv.getFiler()
                    .createSourceFile(className);

            writer = new BufferedWriter(sourceFile.openWriter());
            writer.write("package " + packageName + ";\n\n");
            writer.write("import com.kerwin.paysdk.pay.WXPayActivity;\n\n");
            writer.write("public class WXPayEntryActivity extends WXPayActivity {\n}");

        } catch (Exception e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    //Silent
                }
            }
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        processingEnv = processingEnvironment;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
