package invtweaks;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import invtweaks.forge.InvTweaksMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Minecraft 1.3 Obfuscation layer
 *
 * @author Jimeo Wan
 *
 */
public class InvTweaksObfuscation {

    private static final Logger log = InvTweaks.log;

    public Minecraft mc;

    public InvTweaksModCompatibility mods;

    private static Map<String, Field> fieldsMap = new HashMap<String, Field>();

    private static Class slotCreativeInventory = ReflectionHelper.getClass(InvTweaksObfuscation.class.getClassLoader(), "ayu", "net.minecraft.client.gui.inventory.SlotCreativeInventory");

    private static Class containerCreative = ReflectionHelper.getClass(InvTweaksObfuscation.class.getClassLoader(), "ays", "net.minecraft.client.gui.inventory.ContainerCreative");
	public InvTweaksObfuscation(Minecraft mc) {
		this.mc = mc;
		this.mods = new InvTweaksModCompatibility(this);
	}

	// Minecraft members

	public void addChatMessage(String message) {
	    if (mc.ingameGUI != null) {
	        mc.ingameGUI.getChatGUI().printChatMessage(message);
	    }
	}
    public EntityPlayer getThePlayer() {
        return mc.thePlayer;
    }
    public World getTheWorld() {
        return mc.theWorld;
    }
	public PlayerControllerMP getPlayerController() {
		return mc.playerController;
	}
	public GuiScreen getCurrentScreen() {
		return mc.currentScreen;
	}
	public FontRenderer getFontRenderer() {
        return mc.fontRenderer;
    }
    public void displayGuiScreen(GuiScreen parentScreen) {
        mc.displayGuiScreen(parentScreen);
    }
    public int getDisplayWidth() {
        return mc.displayWidth;
    }
    public int getDisplayHeight() {
        return mc.displayHeight;
    }
    public GameSettings getGameSettings() {
        return mc.gameSettings;
    }
    public KeyBinding[] getRegisteredBindings() {
        return getGameSettings().keyBindings;
    }
    public void setRegisteredBindings(KeyBinding[] bindings) {
        getGameSettings().keyBindings = bindings;
    }
    public int getKeyBindingForwardKeyCode() {
        return getKeyCode(getGameSettings().keyBindForward);
    }
    public int getKeyBindingBackKeyCode() {
        return getKeyCode(getGameSettings().keyBindBack);
    }

	// EntityPlayer members

	public InventoryPlayer getInventoryPlayer() { // InventoryPlayer
		return getThePlayer().inventory;
	}
	public ItemStack getCurrentEquippedItem() { // ItemStack
		return getThePlayer().getCurrentEquippedItem();
	}
    public ContainerPlayer getPlayerContainer() {
        return (ContainerPlayer) getThePlayer().inventoryContainer;
    }

	// InventoryPlayer members

	public ItemStack[] getMainInventory() {
		return getInventoryPlayer().mainInventory;
	}
	public void setMainInventory(ItemStack[] value) {
		getInventoryPlayer().mainInventory = value;
	}
	public void setHasInventoryChanged(boolean value) {
		getInventoryPlayer().inventoryChanged = value;
	}
	public void setHeldStack(ItemStack stack) {
		getInventoryPlayer().setItemStack(stack); // setItemStack
	}
	public boolean hasInventoryChanged() {
		return getInventoryPlayer().inventoryChanged;
	}
	public ItemStack getHeldStack() {
		return getInventoryPlayer().getItemStack(); // getItemStack
	}
	public ItemStack getFocusedStack() {
		return getInventoryPlayer().getCurrentItem(); // getCurrentItem
	}
	public int getFocusedSlot() {
		return getInventoryPlayer().currentItem; // currentItem
	}

    // GuiScreen members

	public int getWindowWidth(GuiScreen guiScreen) {
	    return guiScreen.width;
	}
    public int getWindowHeight(GuiScreen guiScreen) {
        return guiScreen.height;
    }
    public int getGuiX(GuiContainer guiContainer) {
        return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, guiContainer, "n", "field_74198_m", "guiLeft");
    }
    public int getGuiY(GuiContainer guiContainer) {
        return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, guiContainer, "o", "field_74197_n", "guiTop");
    }
    public int getGuiWidth(GuiContainer guiContainer) {
        return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, guiContainer, "b", "field_74194_b", "xSize");
    }
    public int getGuiHeight(GuiContainer guiContainer) {
        return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, guiContainer, "c", "field_74195_c", "ySize");
    }
    @SuppressWarnings("unchecked")
    public List<Object> getControlList(GuiScreen guiScreen) {
        return ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, guiScreen, "i", "field_73887_h", "buttonList");
    }
    public void setControlList(GuiScreen guiScreen, List<?> controlList) {
        ObfuscationReflectionHelper.setPrivateValue(GuiScreen.class, guiScreen, controlList, "i", "field_73887_h", "buttonList");
    }
    public GuiContainer asGuiContainer(GuiScreen guiScreen) {
        return (GuiContainer) guiScreen;
    }

    // FontRenderer members

	public int getStringWidth(FontRenderer fontRenderer, String line) {
	    return fontRenderer.getStringWidth(line);
	}
	public void drawStringWithShadow(FontRenderer fontRenderer,
            String s, int i, int j, int k) {
        fontRenderer.drawStringWithShadow(s, i, j, k);
    }

	// ItemStack members

	public ItemStack createItemStack(int id, int size, int damage) {
		return new ItemStack(id, size, damage);
	}
	public ItemStack copy(ItemStack itemStack) {
		return itemStack.copy();
	}
	public int getItemDamage(ItemStack itemStack) {
		return itemStack.getItemDamage();
	}
	public int getMaxStackSize(ItemStack itemStack) {
		return itemStack.getMaxStackSize();
	}
	public boolean hasDataTags(ItemStack itemStack) {
	  return itemStack.hasTagCompound();
	}
	public int getStackSize(ItemStack itemStack) {
		return itemStack.stackSize;
	}
	public int getItemID(ItemStack itemStack) {
		return itemStack.itemID;
	}
	public boolean areItemStacksEqual(ItemStack itemStack1, ItemStack itemStack2) {
		return itemStack1.isItemEqual(itemStack2) && getStackSize(itemStack1) == getStackSize(itemStack2);
	}
    public boolean isItemStackDamageable(ItemStack itemStack) {
        return itemStack.isItemStackDamageable();
    }
    public boolean areSameItemType(ItemStack itemStack1, ItemStack itemStack2) {
        return areItemsEqual(itemStack1, itemStack2) ||
                (isItemStackDamageable(itemStack1)
                        && getItemID(itemStack1) == getItemID(itemStack2));
    }
    public boolean areItemsEqual(ItemStack itemStack1, ItemStack itemStack2) {
        return itemStack1.isItemEqual(itemStack2);
    }
    public int getAnimationsToGo(ItemStack itemStack) {
        return itemStack.animationsToGo;
    }
    public Item getItem(ItemStack itemStack) { // Item
        return itemStack.getItem();
    }

    // Item & ItemArmor

    public boolean isDamageable(Item item) {
        return item.isDamageable();
    }
    public int getMaxDamage(Item item) {
        return item.getMaxDamage();
    }
    public int getArmorLevel(ItemArmor itemArmor) { // ItemArmor
        return itemArmor.damageReduceAmount;
    }
    public ItemArmor asItemArmor(Item item) { // ItemArmor
        return (ItemArmor) item;
    }

	// PlayerController members

	public ItemStack clickInventory(PlayerControllerMP playerController,
			int windowId, int slot, int clickButton,
			boolean shiftHold, EntityPlayer entityPlayer) {
        InvTweaksMod.proxy.slotClick(playerController, windowId, slot, clickButton, shiftHold, entityPlayer);
        return null;
    }

	// Container members

	public int getWindowId(Container container) {
		return container.windowId;
	}
	public List<?> getSlots(Container container) {
		return container.inventorySlots;
	}
    public Slot getSlot(Container container, int i) { // Slot
        return (Slot) (getSlots(container).get(i));
    }

    public ItemStack getSlotStack(Container container, int i) {
    	Slot slot = getSlot(container, i);
        return (slot == null) ? null : getStack(slot); // getStack
    }

    public void setSlotStack(Container container, int i, ItemStack stack) {
        container.putStackInSlot(i, stack); // putStackInSlot
    }

    // Slot members

    public boolean hasStack(Slot slot) {
        return slot.getHasStack();
    }
    public int getSlotNumber(Slot slot) {
        try {
            // Creative slots don't set the "slotNumber" property, serve as a proxy for true slots
            if (slotCreativeInventory.isInstance(slot)) {
            	//Slot underlyingSlot = SlotCreativeInventory.func_75240_a((SlotCreativeInventory) slot);
            	Slot underlyingSlot = (Slot)ObfuscationReflectionHelper.getPrivateValue(slotCreativeInventory, slot, "b", "field_75241_b", "theSlot");
                if (underlyingSlot != null) {
                    return underlyingSlot.slotNumber;
                } else {
                    log.warning("Creative inventory: Failed to get real slot");
                }
            }
        } catch (Exception e) {
            log.warning("Failed to access creative slot number");
        }
        return slot.slotNumber;
    }
    public ItemStack getStack(Slot slot) {
        return slot.getStack();
    }
    public int getXDisplayPosition(Slot slot) {
        return slot.xDisplayPosition;
    }
    public int getYDisplayPosition(Slot slot) {
        return slot.yDisplayPosition;
    }
    public boolean areSlotAndStackCompatible(Slot slot, ItemStack itemStack) {
        return slot.isItemValid(itemStack); // isItemValid
    }

    // GuiContainer members

    public Container getContainer(GuiContainer guiContainer) {
        return guiContainer.inventorySlots;
    }

    // GuiButton

    public GuiButton asGuiButton(Object o) {
        return (GuiButton) o;
    }
    public void setEnabled(GuiButton guiButton, boolean enabled) { // GuiButton
        guiButton.enabled = enabled;
    }
    public int getId(GuiButton guiButton) { // GuiButton
        return guiButton.id;
    }
    public void setDisplayString(GuiButton guiButton, String string) {
        guiButton.displayString = string;
    }
    public String getDisplayString(GuiButton guiButton) {
        return guiButton.displayString;
    }

    // Other

    public void playSound(String string, float f, float g) {
        mc.sndManager.playSoundFX(string, f, g);
    }
    public long getCurrentTime() {
        return getTheWorld().getTotalWorldTime();
    }
    public int getKeyCode(KeyBinding b) {
        return b.keyCode;
    }
    public int getSpecialChestRowSize(GuiContainer guiContainer, int defaultValue) {
    	return mods.getSpecialChestRowSize(guiContainer, defaultValue);
    }
    public boolean hasTexture(String texture) {
    	InputStream resourceAsStream = null;
    	try {
	        resourceAsStream = mc.renderEngine.texturePack.getSelectedTexturePack().getResourceAsStream(texture);
			return resourceAsStream != null;
    	} catch (IOException e) {
            return false;
        } finally {
    		if (resourceAsStream != null) {
    			try {
					resourceAsStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }

    // Static access

    public static StringTranslate getLocalizationService() { // StringTranslate
        return StringTranslate.getInstance(); // StringTranslate.getInstance()
    }
    public static String getCurrentLanguage() {
        return getLocalizationService().getCurrentLanguage();
    }
    public static String getLocalizedString(String key) {
        return getLocalizationService().translateKey(key);
    }
    public static ItemStack getHoldStackStatic(Minecraft mc) {
        return new InvTweaksObfuscation(mc).getHeldStack();
    }
    public static GuiScreen getCurrentScreenStatic(Minecraft mc) {
        return new InvTweaksObfuscation(mc).getCurrentScreen();
    }

	// Classes

    public boolean isValidChest(GuiScreen guiScreen) {
        return guiScreen != null && (isGuiChest(guiScreen)
        		|| isGuiDispenser(guiScreen)
        		|| mods.isSpecialChest(guiScreen))
                && (guiScreen instanceof GuiContainer);
    }
	public boolean isValidInventory(GuiScreen guiScreen) {
        return isStandardInventory(guiScreen)
        		|| mods.isSpecialInventory(guiScreen);
    }
	public boolean isStandardInventory(GuiScreen guiScreen) {
        return isGuiInventory(guiScreen)
        		|| isGuiWorkbench(guiScreen)
        		|| isGuiFurnace(guiScreen)
                || isGuiBrewingStand(guiScreen)
                || isGuiEnchantmentTable(guiScreen)
                || isGuiTrading(guiScreen)
                || isGuiAnvil(guiScreen)
                || isGuiBeacon(guiScreen)
                || isGuiHopper(guiScreen)
                || (isGuiInventoryCreative(guiScreen)
                        && getSlots(getContainer(asGuiContainer(guiScreen))).size() == 46);
    }

    public boolean isGuiContainer(Object o) { // GuiContainer (abstract class)
        return o != null && o instanceof GuiContainer;
    }

    public boolean isGuiBeacon(Object o) { // GuiBeacon
        return o != null && o.getClass().equals(GuiBeacon.class);
    }
    public boolean isGuiBrewingStand(Object o) { // GuiBrewingStand
        return o != null && o.getClass().equals(GuiBrewingStand.class);
    }
    public boolean isGuiChest(Object o) { // GuiChest
        return o != null && o.getClass().equals(GuiChest.class);
    }
    public boolean isGuiWorkbench(Object o) { // GuiWorkbench
        return o != null && o.getClass().equals(GuiCrafting.class);
    }
    public boolean isGuiInventoryCreative(Object o) { // GuiInventoryCreative
        return o != null && o.getClass().equals(GuiContainerCreative.class);
    }
    public boolean isGuiEnchantmentTable(Object o) { // GuiEnchantmentTable
        return o != null && o.getClass().equals(GuiEnchantment.class);
    }
    public boolean isGuiFurnace(Object o) { // GuiFurnace
        return o != null && o.getClass().equals(GuiFurnace.class);
    }
    public boolean isGuiInventory(Object o) { // GuiInventory
        return o != null && o.getClass().equals(GuiInventory.class);
    }
    public boolean isGuiTrading(Object o) { // GuiTrading
        return o != null && o.getClass().equals(GuiMerchant.class);
    }
    public boolean isGuiAnvil(Object o) { // GuiAnvil
        return o != null && o.getClass().equals(GuiRepair.class);
    }
    public boolean isGuiDispenser(Object o) { // GuiDispenser
        return o != null && o.getClass().equals(GuiDispenser.class);
    }
    public boolean isGuiHopper(Object o) { // GuiHopper
        return o != null && o.getClass().equals(GuiHopper.class);
    }

    public boolean isGuiButton(Object o) { // GuiButton
        return o != null && o instanceof GuiButton;
    }

    public boolean isGuiEditSign(Object o) {
        return o != null && o.getClass().equals(GuiEditSign.class);
    }
    public boolean isContainerBeacon(Object o) {
        return o != null && o.getClass().equals(ContainerBeacon.class);
    }
    public boolean isContainerBrewingStand(Object o) {
        return o != null && o.getClass().equals(ContainerBrewingStand.class);
    }
    public boolean isContainerChest(Object o) {
        return o != null && o.getClass().equals(ContainerChest.class);
    }
    public boolean isContainerWorkbench(Object o) {
        return o != null && o.getClass().equals(ContainerWorkbench.class);
    }
    public boolean isContainerEnchantmentTable(Object o) {
        return o != null && o.getClass().equals(ContainerEnchantment.class);
    }
    public boolean isContainerFurnace(Object o) {
        return o != null && o.getClass().equals(ContainerFurnace.class);
    }
	public boolean isContainerPlayer(Object o) {
	    return o != null && o.getClass().equals(ContainerPlayer.class);
	}
    public boolean isContainerTrading(Object o) {
        return o != null && o.getClass().equals(ContainerMerchant.class);
    }
    public boolean isContainerAnvil(Object o) {
        return o != null && o.getClass().equals(ContainerRepair.class);
    }
    public boolean isContainerDispenser(Object o) {
        return o != null && o.getClass().equals(ContainerDispenser.class);
    }
    public boolean isContainerCreative(Object o) { // ContainerCreative
        return o != null && o.getClass().equals(containerCreative);
    }

    public boolean isItemArmor(Object o) { // ItemArmor
        return o != null && o instanceof ItemArmor;
    }

    public boolean isBasicSlot(Object o) { // Slot
        return o != null && (o.getClass().equals(Slot.class) || o.getClass().equals(slotCreativeInventory));
    }

}
