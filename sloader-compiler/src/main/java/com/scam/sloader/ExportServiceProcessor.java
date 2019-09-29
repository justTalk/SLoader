package com.scam.sloader;

import com.scam.annotation.ExportService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import javax.tools.Diagnostic;

/**
 * Date: 2019-09-27
 * Version: 1.0
 * Author: Andy
 * Description:
 */
@SupportedAnnotationTypes({"com.scam.annotation.ExportService"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExportServiceProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Filer mFiler;
    private String mModuleName = "";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        Map<String, String> options = processingEnvironment.getOptions();
        mModuleName = options != null ? options.get(SConfig.SLOADER_MODULE_KEY) : null;
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ExportService.class);
        if (elements != null && elements.size() > 0) {
            HashMap<String, String> map = new HashMap<>();
            for (Element e :
                    elements) {
                ExportService exportService = e.getAnnotation(ExportService.class);
                if (map.containsKey(exportService.value())) {
                    throw new RuntimeException("the same key is not allowed ");
                }
                map.put(exportService.value(), e.asType().toString());
                mMessager.printMessage(Diagnostic.Kind.NOTE, "======== process ==== map is " + map.hashCode() + " size is " + map.size());
            }
            mMessager.printMessage(Diagnostic.Kind.NOTE, "======== process ==== write map is " + map.hashCode()  + " " + map.size());
            createConfigFile(map);
        }
        return true;
    }

    private void createConfigFile(HashMap<String, String> map){
        JavaFile javaFile = JavaFile.builder(SConfig.SLOADER_SERIVICE_PACKAGE,
                TypeSpec.classBuilder(mModuleName)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(createMethod(map))
                        .build())
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MethodSpec createMethod(Map<String, String> map){
        MethodSpec.Builder builder = MethodSpec.methodBuilder(SConfig.SLOADER_SERIVICE_METHOD)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .returns(ParameterizedTypeName
                        .get(ClassName.get(Map.class), ClassName.get(String.class), ClassName.get(String.class)))
                .addStatement("$T map = new $T()", ParameterizedTypeName.get(ClassName.get(Map.class),
                        ClassName.get(String.class),
                        ClassName.get(String.class)), HashMap.class);
        if (map != null && !map.isEmpty()) {
            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()){
                String key = keys.next();
                String value = map.get(key);
                builder.addStatement("map.put($S, $S)", key, value);
            }
        }

        return builder.addStatement("return map").build();
    }
}
