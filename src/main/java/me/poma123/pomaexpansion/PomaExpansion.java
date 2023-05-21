package me.poma123.pomaexpansion;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.LockedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.FarmerAndroid;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.MinerAndroid;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.WoodcutterAndroid;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;

public class PomaExpansion extends JavaPlugin implements SlimefunAddon {

    private static PomaExpansion instance;
    public static SlimefunItemStack ADVANCED_MASS_FABRICATOR_MACHINE;
    public static LockedItemGroup itemgroup;
    
    @Override
    public void onEnable() {
        instance = this;

        // Registering category
        ItemStack categoryItem = new SlimefunItemStack("PE_ITEMGROUP", HeadTexture.PROGRAMMABLE_ANDROID, "&c高级安卓机器人");
        itemgroup = new LockedItemGroup(new NamespacedKey(this, "poma_expansion"), categoryItem, 4, new NamespacedKey(Slimefun.instance(), "basic_machines"));

        // Registering items
        SlimefunItemStack advancedWoodcutterAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_2_WOODCUTTER", HeadTexture.PROGRAMMABLE_ANDROID_WOODCUTTER, "&c高级安卓机器人 &7(伐木工)", "", "&8\u21E8 &7功能: 伐木", "&8\u21E8 &7燃料效率: 1.5x");
        SlimefunItemStack advancedMinerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_2_MINER", HeadTexture.PROGRAMMABLE_ANDROID_MINER, "&c高级安卓机器人 &7(矿工)", "", "&8\u21E8 &7功能: 挖矿", "&8\u21E8 &7燃料效率: 1.5x");

        SlimefunItemStack empoweredFarmerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_FARMER", HeadTexture.PROGRAMMABLE_ANDROID_FARMER, "&e超级安卓机器人 &7(农民)", "", "&8\u21E8 &7功能: 种植", "&8\u21E8 &7燃料效率: 8x", "&8\u21E8 &7可以收获和种植异域花园作物");
        SlimefunItemStack empoweredWoodcutterAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_WOODCUTTER", HeadTexture.PROGRAMMABLE_ANDROID_WOODCUTTER, "&e超级安卓机器人 &7(伐木工)", "", "&8\u21E8 &7功能: 伐木", "&8\u21E8 &7燃料效率: 8x");
        SlimefunItemStack empoweredMinerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_MINER", HeadTexture.PROGRAMMABLE_ANDROID_MINER, "&e超级安卓机器人 &7(矿工)", "", "&8\u21E8 &7功能: 挖矿", "&8\u21E8 &7燃料效率: 8x");

        new MinerAndroid(itemgroup, 2, advancedMinerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.PROGRAMMABLE_ANDROID_2, new ItemStack(Material.DIAMOND_PICKAXE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        new WoodcutterAndroid(itemgroup, 2, advancedWoodcutterAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_AXE), SlimefunItems.PROGRAMMABLE_ANDROID_2, new ItemStack(Material.DIAMOND_AXE), null, SlimefunItems.ELECTRIC_MOTOR, null}) {
            @Override
            public void postRegister() {
                fuelTypes.clear();
                registerFuelType(new MachineFuel(300, new ItemStack(Material.LAVA_BUCKET)));
                registerFuelType(new MachineFuel(200, SlimefunItems.OIL_BUCKET));
                registerFuelType(new MachineFuel(500, SlimefunItems.FUEL_BUCKET));
            }
        }
        .register(this);

        new MinerAndroid(itemgroup, 3, empoweredMinerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.PROGRAMMABLE_ANDROID_3, new ItemStack(Material.DIAMOND_PICKAXE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        new WoodcutterAndroid(itemgroup, 3, empoweredWoodcutterAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_AXE), SlimefunItems.PROGRAMMABLE_ANDROID_3, new ItemStack(Material.DIAMOND_AXE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        new FarmerAndroid(itemgroup, 3, empoweredFarmerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, SlimefunItems.GPS_TRANSMITTER_3, null, new ItemStack(Material.DIAMOND_HOE), SlimefunItems.PROGRAMMABLE_ANDROID_3, new ItemStack(Material.DIAMOND_HOE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        registerResearch("advanced_miner_android", 55550, "高级安卓机器人 -矿工", 30, advancedMinerAndroid);
        registerResearch("advanced_woodcutter_android", 55551, "高级安卓机器人 - 伐木工", 30, advancedWoodcutterAndroid);
        registerResearch("empowered_miner_android", 55552, "超级安卓机器人 - 矿工", 30, empoweredMinerAndroid);
        registerResearch("empowered_woodcutter_android", 55553, "超级安卓机器人 - 伐木工", 30, empoweredWoodcutterAndroid);
        registerResearch("empowered_farmer_android", 55554, "超级安卓机器人 - 农民", 30, empoweredFarmerAndroid);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return "https://github.com/TheOld-Crafters/PomaExpansion/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    private void registerResearch(String key, int id, String name, int defaultCost, ItemStack... items) {
        Research research = new Research(new NamespacedKey(this, key), id, name, defaultCost);

        for (ItemStack item : items) {
            SlimefunItem sfItem = SlimefunItem.getByItem(item);

            if (sfItem != null) {
                research.addItems(sfItem);
            }
        }

        research.register();
    }

    public void registerRecipe(@Nonnull SlimefunItemStack result, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType) {
        for (int i = 0; i < 9; i++) {
            final ItemStack[] recipe = new ItemStack[9];
            recipe[i] = item;
            recipeType.register(recipe, result);
        }
    }

    @Deprecated
    private ItemGroup getItemGroup(NamespacedKey id) {
        List<ItemGroup> list = Slimefun.getRegistry().getAllItemGroups().stream().filter(c -> c.getKey().equals(id)).collect(Collectors.toList());

        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public static PomaExpansion getInstance() {
        return instance;
    }
}
