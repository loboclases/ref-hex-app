package org.hexagonal.reference.infrastructure.adapter.driven.transactional;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.hexagonal.reference.domain.port.driven.UseCase;
import org.springframework.util.ClassUtils;

/**
 * The type Use case helper.
 *
 * @author joseluis.anton
 */
public class UseCaseHelper {

  /**
   * Determina si el objeto tiene la anotacion {@link UseCase} a nivel de Clase o a nivel de
   * Interfaz
   *
   * @param object the object
   * @return the boolean
   */
  public static boolean isUseCasePresentInType(Object object){
    boolean result=false;

    //Primero determinar si UseCase está a nivel de clase
    UseCase[] annotations = object.getClass().getAnnotationsByType(UseCase.class);
    if (null != annotations && 0 != annotations.length) {
      result = true;
    }else{
      //Segundo determinar si UseCase está a nivel de interfaz si no lo está a nivel de clase
      for(Class<?> objectClass:ClassUtils.getAllInterfaces(object) ){
        UseCase[] useCases = objectClass.getAnnotationsByType(UseCase.class);
        if (null != useCases && 0 != useCases.length) {
          result = true;
        }
      }
    }
    return result;
  }

  /**
   * Determina si el método está anotado en la implementación o en la declaración de la interfaz
   *
   * @param method the method
   * @return the boolean
   */
  public static boolean isUseCasePresentInMethod(Method method){
    boolean result=false;
    //Primero verificar si el método está anotado con UseCase
    UseCase annotation =  method.getDeclaredAnnotation(UseCase.class);
    if(null!=annotation){
      result=true;
    }else{
      //Si el método no está anotado con UseCase entonces buscar si el método está anotado a nivel de interfaz
      for(Class<?> interfaceClass:ClassUtils.getAllInterfaces(method.getDeclaringClass()) ){
        result=Arrays.asList(interfaceClass.getDeclaredMethods()).stream().filter(m->m.getName().equals(method.getName())).map(m->null!=m.getDeclaredAnnotation(UseCase.class)).findFirst().orElse(false);
      }
    }
    return result;

  }

  /**
   * Determina si el objeto tiene anotacion UseCase en algún método o a nivel de clase o en alguno
   * de los interfaces que implementa (nivel de método o interfaz)
   *
   * @param object the object
   * @return the boolean
   */
  public static boolean isUseCase(Object object) {
        return isUseCaseAnnotationPresent(object.getClass())||isUseCaseAnnotationPresent(ClassUtils.getAllInterfaces(object));
      }
    
      private static boolean isUseCaseAnnotationPresent(Class<?>[] classes){
        boolean result=false;
        for (Class<?> implementedInterface : classes) {
          if(isUseCaseAnnotationPresent(implementedInterface)){
            result=true;
            break;
          }
        }
        return result;
      }
      private static boolean isUseCaseAnnotationPresent(Class<?> beanClass){
        boolean result=false;
        // first: check whether the class is annotated with UseCase
        UseCase[] annotations = beanClass.getAnnotationsByType(UseCase.class);
        if (null != annotations && 0 != annotations.length) {
          result = true;
        }
    
        // second: if interfaces are not annotated with UseCase then check whether the
        // methods are annotated with UseCase
        if (!result) {
          for (Method method : beanClass.getDeclaredMethods()) {
            UseCase annotation = method.getDeclaredAnnotation(UseCase.class);
            if (null != annotation) {
              result = true;
              break;
            }
          }
        }
        return result;
      }
}