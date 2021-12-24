package hrbeu.courseDesign.yxd.infrastructure.annotation;


import org.springframework.http.HttpRequest;

import java.lang.annotation.*;
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogRecord {
    String body() default "";
    String type() ;
}