package com.gildedrose;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private Item updateAndGet(Item item) {
        GildedRose app = new GildedRose(new Item[] { item });
        app.updateQuality();
        return app.items[0];
    }

    @Test
    void normalItemDegradesByOne() {
        Item item = updateAndGet(new Item("+5 Dexterity Vest", 10, 20));
        assertEquals(9, item.sellIn);
        assertEquals(19, item.quality);
    }

    @Test
    void normalItemDegradesByTwoAfterSellDate() {
        Item item = updateAndGet(new Item("+5 Dexterity Vest", 0, 20));
        assertEquals(-1, item.sellIn);
        assertEquals(18, item.quality);
    }

    @Test
    void normalItemQualityNeverNegative() {
        Item item = updateAndGet(new Item("+5 Dexterity Vest", 10, 0));
        assertEquals(0, item.quality);
    }

    @Test
    void agedBrieIncreasesInQuality() {
        Item item = updateAndGet(new Item("Aged Brie", 10, 20));
        assertEquals(9, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void agedBrieIncreasesByTwoAfterSellDate() {
        Item item = updateAndGet(new Item("Aged Brie", 0, 20));
        assertEquals(-1, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void agedBrieQualityCappedAtFifty() {
        Item item = updateAndGet(new Item("Aged Brie", 10, 50));
        assertEquals(50, item.quality);
    }

    @Test
    void sulfurasNeverChanges() {
        Item item = updateAndGet(new Item("Sulfuras, Hand of Ragnaros", 10, 80));
        assertEquals(10, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void sulfurasWithNegativeSellInNeverChanges() {
        Item item = updateAndGet(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
        assertEquals(-1, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void backstagePassesIncreaseByOneWhenMoreThanTenDays() {
        Item item = updateAndGet(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        assertEquals(14, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePassesIncreaseByTwoWhenTenDaysOrLess() {
        Item item = updateAndGet(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20));
        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePassesIncreaseByThreeWhenFiveDaysOrLess() {
        Item item = updateAndGet(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20));
        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePassesQualityDropsToZeroAfterConcert() {
        Item item = updateAndGet(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void backstagePassesQualityCappedAtFifty() {
        Item item = updateAndGet(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49));
        assertEquals(50, item.quality);
    }
}
