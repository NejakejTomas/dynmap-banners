package cz.nejakejtomas.dynmapbanners.mixin;

import cz.nejakejtomas.dynmapbanners.DynmapBanners;
import cz.nejakejtomas.dynmapbanners.WorldPosition;
import cz.nejakejtomas.dynmapbanners.BannerApi;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dynmap.markers.MarkerIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractBannerBlock.class)
public abstract class AbstractBannerBlockMixin extends BlockWithEntity
{
	private final Logger LOGGER = LoggerFactory.getLogger(AbstractBannerBlockMixin.class);
	
	protected AbstractBannerBlockMixin(Settings settings)
	{
		super(settings);
	}
	
	@Inject(
					at = @At("HEAD"),
					method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
					require = 1)
	private void onPlaced(World world, BlockPos position, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo callbackInfo) {
		if (world.isClient) return;
		if (!itemStack.hasCustomName()) return;
		if (!(placer instanceof final PlayerEntity player)) return;
		
		final BannerApi bannerApi = DynmapBanners.getInstance().getBannerApi();
		final Optional<BannerBlockEntity> bannerEntity = world.getBlockEntity(position, BlockEntityType.BANNER);
		if (bannerEntity.isEmpty()) return;
		
		final String name = itemStack.getName().asString();
		final String dimension = player.getWorld().getRegistryKey().getValue().getPath();
		final MarkerIcon icon =  bannerApi.getIcon(bannerEntity.get().getColorForState().getName());
		final WorldPosition bannerPosition = new WorldPosition(position.getX(), position.getY(), position.getZ());
		
		bannerApi.addBanner(name, dimension, bannerPosition, icon);
		
		LOGGER.info("Banner marker created " + bannerPosition.x() + " " + bannerPosition.y() + " " + bannerPosition.z() + " by " + player.getName() + " " + placer.getUuidAsString());
	}
	
	@Override
	public void onBreak(World world, BlockPos position, BlockState state, PlayerEntity player)
	{
		super.onBreak(world, position, state, player);
		
		final BannerApi bannerApi = DynmapBanners.getInstance().getBannerApi();
		final WorldPosition bannerPosition = new WorldPosition(position.getX(), position.getY(), position.getZ());
		bannerApi.removeBanner(bannerPosition);
		
		LOGGER.info("Banner marker removed " + bannerPosition.x() + " " + bannerPosition.y() + " " + bannerPosition.z() + " by " + player.getName() + " " + player.getUuidAsString());
	}
}