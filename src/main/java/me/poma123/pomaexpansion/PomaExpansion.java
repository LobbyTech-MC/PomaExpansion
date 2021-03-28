package me.poma123.pomaexpansion;

import io.github.thebusybiscuit.slimefun4.core.categories.LockedCategory;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.FarmerAndroid;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.MinerAndroid;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.WoodcutterAndroid;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class PomaExpansion extends JavaPlugin implements SlimefunAddon {

    private static PomaExpansion instance;
    public static SlimefunItemStack ADVANCED_MASS_FABRICATOR_MACHINE;
    public static LockedCategory category;
    
    @Override
    public void onEnable() {
        instance = this;

        // Registering category
        ItemStack categoryItem = new CustomItem(SkullItem.fromHash("5545078a2f72f43ac629f5277eb7857d05d0041e5af77f24fec81f4bf465cb65"), "&c超级机器人");
        category = new LockedCategory(new NamespacedKey(this, "poma_expansion"), categoryItem, 4, new NamespacedKey(SlimefunPlugin.instance(), "basic_machines"));

        // Registering items
        SlimefunItemStack advancedWoodcutterAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_2_WOODCUTTER", HeadTexture.PROGRAMMABLE_ANDROID_WOODCUTTER, "&c高级安卓机器人 &7(伐木工)", "", "&8\u21E8 &7功能: 伐木", "&8\u21E8 &7燃料效率: 1.5x");
        SlimefunItemStack advancedMinerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_2_MINER", HeadTexture.PROGRAMMABLE_ANDROID_MINER, "&c高级安卓机器人 Android &7(矿工)", "", "&8\u21E8 &7功能: 挖矿", "&8\u21E8 &7燃料效率: 1.5x");

        SlimefunItemStack empoweredFarmerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_FARMER", HeadTexture.PROGRAMMABLE_ANDROID_FARMER, "&e超级安卓机器人 &7(农民)", "", "&8\u21E8 &7功能: 种植", "&8\u21E8 &7燃料效率: 8x", "&8\u21E8 &7可以收获和种植异域花园作物");
        SlimefunItemStack empoweredWoodcutterAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_WOODCUTTER", HeadTexture.PROGRAMMABLE_ANDROID_WOODCUTTER, "&e超级安卓机器人 &7(伐木工)", "", "&8\u21E8 &7功能: 伐木", "&8\u21E8 &7燃料效率: 8x");
        SlimefunItemStack empoweredMinerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_MINER", HeadTexture.PROGRAMMABLE_ANDROID_MINER, "&e超级安卓机器人 &7(矿工)", "", "&8\u21E8 &7功能: 挖矿", "&8\u21E8 &7燃料效率: 8x");

        new MinerAndroid(category, 2, advancedMinerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.PROGRAMMABLE_ANDROID_2, new ItemStack(Material.DIAMOND_PICKAXE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        new WoodcutterAndroid(category, 2, advancedWoodcutterAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
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

        new MinerAndroid(category, 3, empoweredMinerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.PROGRAMMABLE_ANDROID_3, new ItemStack(Material.DIAMOND_PICKAXE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        new WoodcutterAndroid(category, 3, empoweredWoodcutterAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_AXE), SlimefunItems.PROGRAMMABLE_ANDROID_3, new ItemStack(Material.DIAMOND_AXE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        new FarmerAndroid(category, 3, empoweredFarmerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, SlimefunItems.GPS_TRANSMITTER_3, null, new ItemStack(Material.DIAMOND_HOE), SlimefunItems.PROGRAMMABLE_ANDROID_3, new ItemStack(Material.DIAMOND_HOE), null, SlimefunItems.ELECTRIC_MOTOR, null})
        .register(this);

        registerResearch("advanced_miner_android", 55550, "高级安卓机器人 -矿工", 30, advancedMinerAndroid);
        registerResearch("advanced_woodcutter_android", 55551, "高级安卓机器人 - 伐木工", 30, advancedWoodcutterAndroid);
        registerResearch("empowered_miner_android", 55552, "超级安卓机器人 - 矿工", 30, empoweredMinerAndroid);
        registerResearch("empowered_woodcutter_android", 55553, "超级安卓机器人 - 伐木工", 30, empoweredWoodcutterAndroid);
        registerResearch("empowered_farmer_android", 55554, "超级安卓机器人 - 农民", 30, empoweredFarmerAndroid);


        if (getServer().getPluginManager().isPluginEnabled("LiteXpansion")) {
            ADVANCED_MASS_FABRICATOR_MACHINE = new SlimefunItemStack("ADVANCED_MASS_FABRICATOR_MACHINE", Material.RED_CONCRETE, "&c高级废料厂", "", "&f将 &8废料 &f转化成 &5UU物质", "", "&8⇨ &e⚡ &71024 J 可储存", "&8⇨ &e⚡ &7200 J/个废料");

            new AdvancedMassFabricator().register(this);
        }
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
    private Category getCategory(NamespacedKey id) {
        List<Category> list = SlimefunPlugin.getRegistry().getCategories().stream().filter(c -> c.getKey().equals(id)).collect(Collectors.toList());

        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public static PomaExpansion getInstance() {
        return instance;
    }
}
