package com.programmingtechniques.uas.Menu;

public class HomeHelper {
    String commands, description;

    HomeHelper() {
    }

    public HomeHelper(String commands, String description) {
        this.commands = commands;
        this.description = description;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
