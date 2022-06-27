package com.norcane.zen.io;

public interface Resource {

    boolean exists();

    String getLocation();

    String readAsString();
}
