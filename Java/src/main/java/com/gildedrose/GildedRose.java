package com.gildedrose;

class GildedRose {

    private static final String AGED_BRIE     = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS       = "Sulfuras, Hand of Ragnaros";

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }

    /** Обновляет один товар: качество до истечения срока, затем срок, затем качество после. */
    private void updateItem(Item item) {
        if (isSulfuras(item)) {
            return; // Sulfuras никогда не меняется
        }
        updateQualityBeforeExpiry(item);
        item.sellIn -= 1;
        if (item.sellIn < 0) {
            updateQualityAfterExpiry(item);
        }
    }

    private void updateQualityBeforeExpiry(Item item) {
        if (isAgedBrie(item)) {
            increaseQuality(item, 1);
        } else if (isBackstagePass(item)) {
            updateBackstagePassBeforeExpiry(item);
        } else {
            decreaseQuality(item, 1);
        }
    }

    private void updateBackstagePassBeforeExpiry(Item item) {
        increaseQuality(item, 1);
        if (item.sellIn < 11) {
            increaseQuality(item, 1);
        }
        if (item.sellIn < 6) {
            increaseQuality(item, 1);
        }
    }

    private void updateQualityAfterExpiry(Item item) {
        if (isAgedBrie(item)) {
            increaseQuality(item, 1);
        } else if (isBackstagePass(item)) {
            item.quality = MIN_QUALITY;
        } else {
            decreaseQuality(item, 1);
        }
    }

    private void increaseQuality(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }

    private boolean isAgedBrie(Item item)     { return AGED_BRIE.equals(item.name); }
    private boolean isBackstagePass(Item item) { return BACKSTAGE_PASS.equals(item.name); }
    private boolean isSulfuras(Item item)     { return SULFURAS.equals(item.name); }
}
