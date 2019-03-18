package com.xha.mangotv.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Series{
    private String name;
    private String type="line";
    public List data = new ArrayList<String>();

    public Series(String name, String type, List data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public Series() {
    }
}