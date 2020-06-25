package com.redblueflame.herbocraft.blocks;

import com.redblueflame.herbocraft.HerboCraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import spinnery.common.container.BaseContainer;
import spinnery.common.inventory.BaseInventory;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;

import java.util.Collection;

public class UpgraderBlockContainer extends BaseContainer {
    public UpgraderBlockEntity entity;
    public static final int GROWTH_CONTROLLER_INVENTORY = 1;
    public Text name;
    public PlayerEntity player;

    public UpgraderBlockContainer(int synchronizationID, Text name, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(synchronizationID, playerInventory);
        this.name = name;
        this.player = playerInventory.player;
        this.entity = (UpgraderBlockEntity) world.getBlockEntity(pos);
        initInventory();
    }

    private void initInventory() {
        WInterface mainInterface = getInterface();
        if (entity.inventory == null) {
            // Contains the 1 input and 9 outputs.
            entity.inventory = new BaseInventory(20);
        }
        getInventories().put(GROWTH_CONTROLLER_INVENTORY, entity.inventory);
        mainInterface.createChild(WSlot::new);
        Collection<WSlot> input = WSlot.addHeadlessArray(mainInterface, 0, GROWTH_CONTROLLER_INVENTORY, 3, 3);
        for (WSlot item: input) {
            //noinspection unchecked
            item.accept(HerboCraft.SEEDS);

            item.setWhitelist();
        }
        Collection<WSlot> traitment = WSlot.addHeadlessArray(mainInterface, 10, GROWTH_CONTROLLER_INVENTORY, 1, 1);
        for (WSlot item: traitment) {
            item.accept(HerboCraft.SEEDS);
            item.setWhitelist();
        }
        Collection<WSlot> output = WSlot.addHeadlessArray(mainInterface, 11, GROWTH_CONTROLLER_INVENTORY, 3, 3);
        for (WSlot item: output) {
            item.accept(Items.AIR);
            item.setWhitelist();
        }
        WSlot.addHeadlessPlayerInventory(mainInterface);
    }
}

