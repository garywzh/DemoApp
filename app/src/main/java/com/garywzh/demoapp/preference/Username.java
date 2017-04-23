package com.garywzh.demoapp.preference;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

/**
 * Created by garywzh on 2017/4/17.
 */

@Qualifier
@Retention(RUNTIME)
public @interface Username {

}
