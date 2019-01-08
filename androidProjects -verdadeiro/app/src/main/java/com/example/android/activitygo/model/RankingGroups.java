package com.example.android.activitygo.model;

import java.util.HashMap;
import java.util.Map;

public class RankingGroups {

    private Map<String,Integer> gruposRanking;

    public RankingGroups(){

    }

    public RankingGroups(Map<String, Integer> gruposRanking) {
        this.gruposRanking = new HashMap<>();
    }

    public Map<String, Integer> getGruposRanking() {
        return gruposRanking;
    }




}
