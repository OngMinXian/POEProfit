package com.trombae.poeprofitapi.constants;

import lombok.Getter;

public final class POENinjaConstants {
    public static final class APIEndpoints {
        public static final String CURRENCY_ENDPOINT = "currencyoverview";
        public static final String ITEM_ENDPOINT = "itemoverview";

        public static final String LINES_JSON_FIELD = "lines";
    }

    @Getter
    public enum CurrencyTypes {
        CURRENCY("Currency"),
        FRAGMENT("Fragment");

        private final String typeName;

        CurrencyTypes(String typeName) {
            this.typeName = typeName;
        }
    }

    @Getter
    public enum ItemTypes {
        RUNEGRAFT("Runegraft"),
        ALLFLAME_EMBER("AllflameEmber"),
        TATTOO("Tattoo"),
        OMEN("Omen"),
        DIVINATION_CARD("DivinationCard"),
        ARTIFACT("Artifact"),
        OIL("Oil"),
        INCUBATOR("Incubator"),
        UNIQUE_WEAPON("UniqueWeapon"),
        UNIQUE_ARMOUR("UniqueArmour"),
        UNIQUE_ACCESSORY("UniqueAccessory"),
        UNIQUE_FLASK("UniqueFlask"),
        UNIQUE_JEWEL("UniqueJewel"),
        UNIQUE_RELIC("UniqueRelic"),
        SKILL_GEM("SkillGem"),
        CLUSTER_JEWEL("ClusterJewel"),
        MAP("Map"),
        BLIGHTED_MAP("BlightedMap"),
        BLIGHT_RAVAGED_MAP("BlightRavagedMap"),
        UNIQUE_MAP("UniqueMap"),
        DELIRIUM_ORB("DeliriumOrb"),
        INVITATION("Invitation"),
        SCARAB("Scarab"),
        BASE_TYPE("BaseType"),
        FOSSIL("Fossil"),
        RESONATOR("Resonator"),
        BEAST("Beast"),
        ESSENCE("Essence"),
        VAIL("Vial");

        private final String typeName;

        ItemTypes(String typeName) {
            this.typeName = typeName;
        }
    }
}
