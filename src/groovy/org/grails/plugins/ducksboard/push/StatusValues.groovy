package org.grails.plugins.ducksboard.push

public enum StatusValues {
    OK(0),
    ERROR(1),
    WARNING(2),
    UNKNOWN(3)

    Integer value
    StatusValues(value) {
        this.value = value
    }
}