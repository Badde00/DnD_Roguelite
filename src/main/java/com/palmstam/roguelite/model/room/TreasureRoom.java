package com.palmstam.roguelite.model.room;

import com.palmstam.roguelite.model.RollCurrency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreasureRoom extends Room {
    private String[] treasure;

    @Builder
    public TreasureRoom(String encounterType, String description, int level, int partySize) {
        super(encounterType, description, level);
        this.treasure = new String[5 + partySize];
        treasure[0] = RollCurrency.rollCurrencyRewardPerPerson(level, true, true) * 4 + " gold";
        treasure[1] = RollCurrency.rollCurrencyRewardPerPerson(level, false, false) + " electrum";
        treasure[2] = RollCurrency.rollCurrencyRewardPerPerson(level, false, false) + " gemstones";
        treasure[3] = RollCurrency.rollCurrencyRewardPerPerson(level, false, false) + " faith";
        treasure[4] = RollCurrency.rollCurrencyRewardPerPerson(level, false, false) + " artwork";
        if (partySize > 0) {
            for (int i = 0; i < partySize; i++) {
                treasure[5 + i] = "Nothing, for now"; //TODO: implement magic item table
            }
        }
    }
}
