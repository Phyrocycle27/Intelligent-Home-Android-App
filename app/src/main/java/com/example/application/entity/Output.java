package com.example.application.entity;

import java.util.Date;

public class Output {

    private Integer outputId;
    private String name;
    private Integer gpio;
    private Boolean reverse;
    private Date creationDate;
    private String type;

    public Output(Integer outputId, String name, Integer gpio, Boolean reverse, Date creationDate, String type) {
        this.outputId = outputId;
        this.name = name;
        this.gpio = gpio;
        this.reverse = reverse;
        this.creationDate = creationDate;
        this.type = type;
    }

    public Integer getOutputId() {
        return outputId;
    }

    public String getName() {
        return name;
    }

    public Integer getGpio() {
        return gpio;
    }

    public Boolean getReverse() {
        return reverse;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "\nOutput{" +
                "\n\toutputId=" + outputId + ',' +
                "\n\tname='" + name + '\'' + ',' +
                "\n\tgpio=" + gpio + ',' +
                "\n\treverse=" + reverse + ',' +
                "\n\tcreationDate=" + creationDate + ',' +
                "\n\ttype='" + type + '\'' + '\n' +
                '}';
    }
}