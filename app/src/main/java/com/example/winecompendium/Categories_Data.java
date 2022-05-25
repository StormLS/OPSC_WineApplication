package com.example.winecompendium;

import java.util.HashMap;
import java.util.Map;

public class Categories_Data {

    /*
    This data may not be touched.
     */

    public Map<String, Object> WineTypesMap() {

        Map<String, Object> map = new HashMap<>();

        map = new HashMap<>();
        map.put("1","White");
        map.put("2","Red");
        map.put("3","Rosè");
        map.put("4","Sparkling");
        map.put("5","Dessert");
        map.put("6","Fortified");

        return map;
    }

    public Map<String, Object> SubTypeDessertMap() {

        Map<String, Object> DessertMap = new HashMap<>();
        DessertMap.put("1", "Port");
        DessertMap.put("2", "Madeira");
        DessertMap.put("3", "Sauternes");
        DessertMap.put("4", "Sherry");
        DessertMap.put("5", "Riesling");
        DessertMap.put("6", "Gewürztraminer");
        DessertMap.put("7", "Moscato");
        DessertMap.put("8", "Ice");

        return DessertMap;
    }

    public Map<String, Object> SubTypeFortifiedMap() {

        Map<String, Object> FortifiedMap = new HashMap<>();
        FortifiedMap.put("1","Madeira");
        FortifiedMap.put("2","Marsala");
        FortifiedMap.put("3","Port");
        FortifiedMap.put("4","Sherry");
        FortifiedMap.put("5","Vermouth");

        return FortifiedMap;
    }

    public Map<String, Object> SubTypeRedMap() {

        Map<String, Object> RedMap = new HashMap<>();
        RedMap.put("1","Cabernet Sauvignon");
        RedMap.put("2","Merlot");
        RedMap.put("3","Pinot Noir");
        RedMap.put("4","Pinotage");
        RedMap.put("5","Shiraz");
        RedMap.put("6","Zinfandel");
        RedMap.put("7","Sangiovese");
        RedMap.put("8","Nebbiolo");
        RedMap.put("9","Grenache");
        RedMap.put("10","Bordeaux");
        RedMap.put("11","Burgundy");
        RedMap.put("12","Cabernet France");

        return RedMap;
    }

    public Map<String, Object> SubTypeRoseMap() {

        Map<String, Object> RoseMap = new HashMap<>();
        RoseMap.put("1","Sangiovese");
        RoseMap.put("2","Tempranillo");
        RoseMap.put("3","Syrah");
        RoseMap.put("4","Cabernet Sauvignon");
        RoseMap.put("5","Zinfandel");
        RoseMap.put("6","Tavel");
        RoseMap.put("7","Provence");
        RoseMap.put("8","Mourvèdre");
        RoseMap.put("9","Pinot Noir");

        return RoseMap;
    }

    public Map<String, Object> SubTypeSparklingMap() {

        Map<String, Object> SparklingMap = new HashMap<>();
        SparklingMap.put("1","Champagne");
        SparklingMap.put("2","Prosecco");
        SparklingMap.put("3","Cava");
        SparklingMap.put("4","Sparkling Rosè");
        SparklingMap.put("5","Sekt");
        SparklingMap.put("6","Crèmant");
        SparklingMap.put("7","Trentodoc");

        return SparklingMap;
    }

    public Map<String, Object> SubTypeWhite() {

        Map<String, Object> WhiteMap = new HashMap<>();
        WhiteMap.put("1","Chardonnay");
        WhiteMap.put("2","Chenin Blanc");
        WhiteMap.put("3","Sauvignon Blan");
        WhiteMap.put("4","Moscato");
        WhiteMap.put("5","Pinot Grigio");
        WhiteMap.put("6","Gewürztraminer");
        WhiteMap.put("7","Riesling");
        WhiteMap.put("8","Viognier");

        return WhiteMap;
    }

    public Map<String, Object> OriginMap() {

        Map<String, Object> OriginMap = new HashMap<>();
        OriginMap.put("1","Italy");
        OriginMap.put("2","France");
        OriginMap.put("3","United States");
        OriginMap.put("4","Spain");
        OriginMap.put("5","Australia");
        OriginMap.put("6","China");
        OriginMap.put("7","South Africa");
        OriginMap.put("8","Chile");
        OriginMap.put("9","Germany");
        OriginMap.put("10","Portugal");
        OriginMap.put("11","Russia");
        OriginMap.put("12","Romania");
        OriginMap.put("13","Israel");
        OriginMap.put("14","Brazil");

        return OriginMap;
    }

    public Map<String, Object> BottleTypeMap() {

        Map<String, Object> BottleTypeMap = new HashMap<>();
        BottleTypeMap.put("1","Rhone");
        BottleTypeMap.put("2","Burgundy");
        BottleTypeMap.put("3","Alsace");
        BottleTypeMap.put("4","Bordeaux");
        BottleTypeMap.put("5","Sparkling");
        BottleTypeMap.put("6","Port");
        BottleTypeMap.put("7","Dessert");
        BottleTypeMap.put("8","Rhine");

        return BottleTypeMap;
    }

}
