/*
 * MIT License
 *
 * Copyright (c) 2023 Glare
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.glaremasters.guilds.guild;

import com.cryptomorin.xseries.XSkull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;
import java.util.Objects;

/**
 * Represents a guild skull, which is a player head in Minecraft that has a custom texture.
 */
public class GuildSkull {
    private final String serialized;
    private transient ItemStack itemStack;

    /**
     * Creates a guild skull from a player.
     *
     * @param player the player whose head will be used for the guild skull
     */
    public GuildSkull(Player player) {
        final XSkull.SkullInstruction<ItemStack> skullInstruction = XSkull.create();
        final XSkull.SkullAction<ItemStack> skullAction = skullInstruction.profile(player);

        itemStack = skullAction.apply();
        serialized = XSkull.getSkinValue(Objects.requireNonNull(itemStack.getItemMeta()));
    }

    /**
     * Creates a guild skull from a texture string.
     *
     * @param texture the texture string, which should be a Minecraft resource location string
     */
    public GuildSkull(String texture) {
        final String encoded = Base64.getEncoder().encodeToString(texture.getBytes());
        this.serialized = encoded;

        this.itemStack = createSkull();
    }

    /**
     * Creates a guild skull from a serialized string.
     *
     * @return the guild skull
     */
    public ItemStack createSkull() {
        final XSkull.SkullInstruction<ItemStack> skullInstruction = XSkull.create();
        final XSkull.SkullAction<ItemStack> skullAction = skullInstruction.profile(serialized);

        return skullAction.apply();
    }

    /**
     * Gets the guild skull as an item stack.
     *
     * @return the guild skull as an item stack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Get the serialized string
     *
     * @return serialized string
     */
    public String getSerialized() {
        return serialized;
    }
}