package com.norcane.zen.io;

public interface Resource {

    boolean exists();

    String getPath();

    String readAsString();
}
