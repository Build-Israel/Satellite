package online.bteisrael.satellite.util.event;

import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    boolean ignoreCancelled() default false;
    int maxExecutions() default 0;
}
