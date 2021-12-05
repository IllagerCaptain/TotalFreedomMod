package me.totalfreedom.totalfreedommod.command.input;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters {

    String name();

    String description();

    String usage();

    String aliases() default ""; // "alias1,alias2,alias3" - no spaces
}