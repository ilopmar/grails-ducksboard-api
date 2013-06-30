package org.grails.plugins.ducksboard.push

public enum LeaderboardStatusValues {
    GREEN("green"),
    YELLOW("yellow"),
    RED("red"),
    GRAY("gray")

    String value
    LeaderboardStatusValues(value) {
        this.value = value
    }
}