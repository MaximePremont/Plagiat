package net.samagames.plagiat.game;

import net.samagames.api.gui.AbstractGui;
import net.samagames.api.shops.IPlayerShop;
import net.samagames.plagiat.Plagiat;
import net.samagames.tools.GlowEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Plagiat Kit Selector Gui Class
 */
public class PlagiatKitSelectorGui extends AbstractGui
{
    private Plagiat plugin;

    public PlagiatKitSelectorGui(Plagiat plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Display GUI to user
     * Just call update method
     *
     * @param player Bukkit Player instance
     */
    @Override
    public void display(Player player)
    {
        this.update(player);
    }

    /**
     * Update GUI to user
     *
     * @param player Bukkit Player instance
     */
    @Override
    public void update(Player player)
    {
        this.inventory = this.plugin.getServer().createInventory(null, 27);

        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setDisplayName("Retour");
        arrow.setItemMeta(arrowMeta);
        this.setSlotData(arrow, 26, "back");

        IPlayerShop playerShop = this.plugin.getSamaGamesAPI().getShopsManager().getPlayer(player.getUniqueId());
        if (playerShop == null)
            return ;

        final int[] i = {0};
        PlagiatKit selectedKit = this.plugin.getGame().getKitForPlayer(player.getUniqueId());
        PlagiatKit.getKits().forEach(kit ->
        {
            boolean unlocked = kit.getDbId() == -1;
            if (!unlocked)
                unlocked = playerShop.getTransactionsByID(kit.getDbId()) != null;

            ItemStack itemStack;
            if (unlocked)
                itemStack = kit.getIcon();
            else
                itemStack = kit.getLockedIcon();

            if (selectedKit == kit)
                GlowEffect.addGlow(itemStack);

            this.setSlotData(itemStack, i[0], "kit:" + kit.getName() + ":" + kit.getDbId());

            ++i[0];
        });
    }

    /**
     * Handle click on GUI
     *
     * @param player Bukkit Player instance
     * @param stack Bukkit ItemStack instance
     * @param action Action performed
     */
    @Override
    public void onClick(Player player, ItemStack stack, String action)
    {
        if (action.equals("back"))
        {
            player.closeInventory();
            //return ;
        }

        // TODO Select kit
    }
}