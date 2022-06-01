package com.example.winecompendium;

import java.util.HashMap;
import java.util.Map;

public class Categories_Data_Add_Goal {

    public Map<String, Object> Categories() {

        Map<String, Object> map = new HashMap<>();

        map = new HashMap<>();
        map.put("1","WineType");
        map.put("2","SubType");
        map.put("3","Origin");
        map.put("4","BottleType");

        return map;
    }

}
