package com.jcohy.boot.configuration.processor.utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor8;

/**
 * 描述: {@link Element} 表示一个程序元素，比如包，类或者方法。每个元素都表示一个静态的语言级构造（不表示虚拟机运行时的构造）.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/20:11:14
 * @since 2022.04.0
 */
public final class Elements {

    private Elements() {
    }

    /**
     * 获取此元素类型的完全限定名.
     * @param element element
     * @return /
     * @since 2022.04.0
     */
    public static String getQualifiedName(Element element) {
        if (element != null) {
            TypeElement enclosingElement = getEnclosingTypeElement(element.asType());
            if (enclosingElement != null) {
                return getQualifiedName(enclosingElement) + "$"
                        + ((DeclaredType) element.asType()).asElement().getSimpleName().toString();
            }
            if (element instanceof TypeElement) {
                return ((TypeElement) element).getQualifiedName().toString();
            }
        }
        return null;
    }

    private static TypeElement getEnclosingTypeElement(TypeMirror type) {
        if (type instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) type;
            Element enclosingElement = declaredType.asElement().getEnclosingElement();
            if (enclosingElement instanceof TypeElement) {
                return (TypeElement) enclosingElement;
            }
        }
        return null;
    }

    /**
     * 将 Element 转为 TypeElement.
     * @param element element
     * @return /
     * @since 2022.04.0
     */
    public static TypeElement asType(Element element) {
        return element.accept(TypeElementVisitor.INSTANCE, null);
    }

    /**
     * 根据 TypeElement 获取元素的全类名.
     * @param element element
     * @return string 全类名
     * @since 2022.04.0
     */
    public static String getBinaryName(Element element) {
        return getBinaryNameImpl(element, element.getSimpleName().toString());
    }

    /**
     * 根据 TypeElement 获取元素的全类名.
     * @param element element
     * @param className 类名(不含包名)
     * @return string 全类名
     * @since 2022.04.0
     */
    public static String getBinaryNameImpl(Element element, String className) {
        Element enclosingElement = element.getEnclosingElement();

        if (enclosingElement instanceof PackageElement) {
            PackageElement pkg = (PackageElement) enclosingElement;
            if (pkg.isUnnamed()) {
                return className;
            }
            return pkg.getQualifiedName() + "." + className;
        }

        TypeElement typeElement = (TypeElement) enclosingElement;
        return getBinaryNameImpl(typeElement, typeElement.getSimpleName() + "$" + className);
    }

    private static final class TypeElementVisitor extends CastingElementVisitor<TypeElement> {

        private static final TypeElementVisitor INSTANCE = new TypeElementVisitor();

        TypeElementVisitor() {
            super("type element");
        }

        @Override
        public TypeElement visitType(TypeElement e, Void ignore) {
            return e;
        }

    }

    private abstract static class CastingElementVisitor<T> extends SimpleElementVisitor8<T, Void> {

        private final String label;

        CastingElementVisitor(String label) {
            this.label = label;
        }

        @Override
        protected final T defaultAction(Element e, Void ignore) {
            throw new IllegalArgumentException(e + " does not represent a " + this.label);
        }

    }

}
