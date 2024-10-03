package strelka.gizmos.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import strelka.gizmos.blocks.ModBlocks;
import strelka.gizmos.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipes extends RecipeProvider{

    public ModRecipes(DataGenerator generator, CompletableFuture<HolderLookup.Provider> provider){
        super(generator.getPackOutput(), provider);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBBER_DUCK.get(), 1)
            .pattern("FEF")
            .pattern(" H ")
            .define('F', Tags.Items.FEATHERS)
            .define('E', Tags.Items.EGGS)
            .define('H', Items.HAY_BLOCK)
            .unlockedBy("has_feahters", has(Tags.Items.FEATHERS))
            .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DESTRUCTION_CATALYST.get(), 1)
            .pattern("tft")
            .pattern("non")
            .pattern("t t")
            .define('t', Blocks.TNT)
            .define('n', Tags.Items.INGOTS_NETHERITE)
            .define('o', Tags.Items.OBSIDIANS)
            .define('f', Items.FLINT_AND_STEEL)
            .unlockedBy("has_tnt", has(Blocks.TNT))
            .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TUNING_WAND.get(), 1)
            .pattern(" i ")
            .pattern(" ai")
            .pattern("s  ")
            .define('i', Tags.Items.INGOTS_IRON)
            .define('a', Tags.Items.GEMS_AMETHYST)
            .define('s', Tags.Items.RODS_WOODEN)
            .unlockedBy("has_brain", has(ModItems.ROTTEN_BRAIN.get()))
            .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.BRAIN_JAR.get(), 1)
            .pattern("sss")
            .pattern("gbg")
            .pattern("gag")
            .define('s', ItemTags.WOODEN_SLABS)
            .define('g', Tags.Items.GLASS_BLOCKS)
            .define('b', ModItems.ROTTEN_BRAIN.get())
            // TODO: Use tag for this
            .define('a', Items.AMETHYST_BLOCK)
            .unlockedBy("has_brain", has(ModItems.ROTTEN_BRAIN.get()))
            .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.NODE.get(), 2)
            .pattern("www")
            .pattern("geg")
            .pattern("www")
            .define('w', Items.DARK_OAK_PLANKS)
            .define('g', Tags.Items.INGOTS_GOLD)
            .define('e', Tags.Items.ENDER_PEARLS)
            .unlockedBy("has_brain", has(ModItems.ROTTEN_BRAIN.get()))
            .save(pRecipeOutput);
    }
}



