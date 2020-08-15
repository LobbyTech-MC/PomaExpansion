package me.poma123.pomaexpansion;

import io.github.thebusybiscuit.slimefun4.core.categories.LockedCategory;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.MinerAndroid;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.WoodcutterAndroid;
import io.github.thebusybiscuit.slimefun4.implementation.setup.SlimefunItemSetup;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public class PomaExpansion extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {
        // Read something from your config.yml
        //Config cfg = new Config(this);

        ItemStack categoryItem = new CustomItem(SlimefunItems.PROGRAMMABLE_ANDROID, "&cPomaExpansion");
        LockedCategory category = new LockedCategory(new NamespacedKey(this, "poma_expansion"), categoryItem, 4, new NamespacedKey(SlimefunPlugin.instance(), "basic_machines"));



        // Registering items

        SlimefunItemStack advancedWoodcutterAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_2_WOODCUTTER", HeadTexture.PROGRAMMABLE_ANDROID_WOODCUTTER, "&cAdvanced Programmable Android &7(Woodcutter)", "", "&8\u21E8 &7Function: Woodcutting", "&8\u21E8 &7Fuel Efficiency: 1.5x");
        SlimefunItemStack advancedMinerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_2_MINER", HeadTexture.PROGRAMMABLE_ANDROID_MINER, "&cAdvanced Programmable Android &7(Miner)", "", "&8\u21E8 &7Function: Mining", "&8\u21E8 &7Fuel Efficiency: 1.5x");

        SlimefunItemStack empoweredFarmerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_FARMER", HeadTexture.PROGRAMMABLE_ANDROID_FARMER, "&eEmpowered Programmable Android &7(Farmer)", "", "&8\u21E8 &7Function: Farming", "&8\u21E8 &7Fuel Efficiency: 8x", "&8\u21E8 &7Can also harvest Plants from ExoticGarden");
        SlimefunItemStack empoweredWoodcutterAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_WOODCUTTER", HeadTexture.PROGRAMMABLE_ANDROID_WOODCUTTER, "&eEmpowered Programmable Android &7(Woodcutter)", "", "&8\u21E8 &7Function: Woodcutting", "&8\u21E8 &7Fuel Efficiency: 8x");
        SlimefunItemStack empoweredMinerAndroid = new SlimefunItemStack("PROGRAMMABLE_ANDROID_3_MINER", HeadTexture.PROGRAMMABLE_ANDROID_MINER, "&eEmpowered Programmable Android &7(Miner)", "", "&8\u21E8 &7Function: Mining", "&8\u21E8 &7Fuel Efficiency: 8x");


        new MinerAndroid(category, advancedMinerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.DIAMOND_PICKAXE), null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public float getFuelEfficiency() {
                return 1.5F;
            }

            @Override
            public int getTier() {
                return 2;
            }

        }
        .register(this);

        new WoodcutterAndroid(category, advancedWoodcutterAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_AXE), SlimefunItems.PROGRAMMABLE_ANDROID, new ItemStack(Material.DIAMOND_AXE), null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public float getFuelEfficiency() {
                return 1.5F;
            }

            @Override
            public int getTier() {
                return 2;
            }

        }
        .register(this);

        new MinerAndroid(category, empoweredMinerAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_PICKAXE), advancedMinerAndroid, new ItemStack(Material.DIAMOND_PICKAXE), null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public float getFuelEfficiency() {
                return 8F;
            }

            @Override
            public int getTier() {
                return 3;
            }

        }
        .register(this);

        new WoodcutterAndroid(category, empoweredWoodcutterAndroid, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, null, null, new ItemStack(Material.DIAMOND_AXE), advancedWoodcutterAndroid, new ItemStack(Material.DIAMOND_AXE), null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public float getFuelEfficiency() {
                return 8F;
            }

            @Override
            public int getTier() {
                return 3;
            }

        }
        .register(this);
        
    }




    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return "https://github.com/poma123/PomaExpansion/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
