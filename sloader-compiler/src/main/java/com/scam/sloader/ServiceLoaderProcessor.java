package com.scam.sloader;

import com.scam.annotation.ExportService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Date: 2019-09-26
 * Version: 1.0
 * Author: Andy
 * Description:
 */
@SupportedAnnotationTypes({"com.scam.annotation.ExportLoader"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ServiceLoaderProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Filer mFiler;
    private Elements mElementUtils;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "init------------------------------");
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "process------------------------------");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ExportService.class);
        if (elements != null) {
            for (Element e :
                    elements) {
                ExportService exportLoader = e.getAnnotation(ExportService.class);
                mMessager.printMessage(Diagnostic.Kind.NOTE, "process------------------------------" + exportLoader.value());
            }
        }
        test();
        return true;
    }

    private void test() {
        JavaFile javaFile = JavaFile.builder("com.scam.sloader",
                // TypeSpec 代表一个类
                TypeSpec.classBuilder("Test")
                        // 给类添加一个属性
                        .addField(FieldSpec.builder(int.class, "mField", Modifier.PRIVATE)
                                .build())
                        // 给类添加一个方法
                        .addMethod(MethodSpec.methodBuilder("method")
                                .addModifiers(Modifier.PUBLIC)
                                .returns(void.class)
                                .addStatement("System.out.println(mField)")
                                .build())
                        .build())
                .build();
        try {
            javaFile.writeTo(mFiler);
            mMessager.printMessage(Diagnostic.Kind.NOTE, "write------------------------------");
        } catch (IOException e) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "write exception------------------------------" + e.getMessage());
            e.printStackTrace();
        }
    }
}
