package strelka.gizmos.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import strelka.gizmos.Gizmos;
import strelka.gizmos.datagen.loot.ModBlockLootTables;
import strelka.gizmos.datagen.loot.ModLootTableProvider;
import strelka.gizmos.datagen.models.ModItemModels;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = Gizmos.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators{
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event){
    ExistingFileHelper helper = event.getExistingFileHelper();
    DataGenerator generator = event.getGenerator();

    /*     server     */
    boolean isIncludeServer = event.includeServer();
    generator.addProvider(isIncludeServer, new ModRecipes(generator, event.getLookupProvider()));
    generator.addProvider(isIncludeServer, new ModLootTableProvider(generator.getPackOutput(), Collections.emptySet(), List.of(
            new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
    ), event.getLookupProvider()));

    /*     client     */
    boolean isIncludeClient = event.includeClient();
    generator.addProvider(isIncludeClient, new ModItemModels(generator, helper));
    generator.addProvider(isIncludeClient, new ModEnUsLangProvider(generator, "en_us"));
  }
}
