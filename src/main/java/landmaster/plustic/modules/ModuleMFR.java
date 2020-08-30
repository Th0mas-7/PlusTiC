package landmaster.plustic.modules;

import landmaster.plustic.ModInfo;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.util.concurrent.CompletableFuture;

import static slimeknights.tconstruct.library.utils.HarvestLevels.STONE;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleMFR implements IModule {
    private static final CompletableFuture<?> regFut = new CompletableFuture<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemReg(RegistryEvent.Register<Item> event) {
        regFut.complete(null);
    }

    @Override
    public void init() {
        if (Config.mfr && (Loader.isModLoaded("industrialforegoing"))) {
            Material pink_slime_mat = new Material("pink_slime", 0xFF84AD);

            PlusTiC.materials.put("pink_slime", pink_slime_mat);

            final CompletableFuture<?> mfrFut = regFut.thenRun(() -> {
                final Item pink_slime = Item.REGISTRY.getObject(new ResourceLocation("industrialforegoing:pink_slime"));

                pink_slime_mat.addTrait(ModuleMFRStuff.slimey_pink);
                pink_slime_mat.addItem(pink_slime, 1, Material.VALUE_Ingot);
                pink_slime_mat.setCraftable(true);
                pink_slime_mat.setRepresentativeItem(pink_slime);
                PlusTiC.proxy.setRenderInfo(pink_slime_mat, 0xFF84AD);

                TinkerRegistry.addMaterialStats(pink_slime_mat,
                        new HeadMaterialStats(1800, 3.77f, 1.80f, STONE),
                        new HandleMaterialStats(2.7f, -729),
                        new ExtraMaterialStats(243),
                        new BowMaterialStats(1.2f, 0.8f, 0));
            });

            PlusTiC.materialIntegrationStages.put("pink_slime", mfrFut);
        }
    }
}
