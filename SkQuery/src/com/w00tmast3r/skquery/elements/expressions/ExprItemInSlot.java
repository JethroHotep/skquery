package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Slot;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@UsePropertyPatterns
@PropertyFrom("inventoryholder")
@PropertyTo("slot %number%")
public class ExprItemInSlot extends SimpleExpression<Slot> {

    private Expression<InventoryHolder> holder;
    private Expression<Number> slot;

    @Override
    protected Slot[] get(Event event) {
        InventoryHolder h = holder.getSingle(event);
        Number n = slot.getSingle(event);
        if (h == null || n == null) return null;
        final Inventory i = h.getInventory();
        final int s = n.intValue();
        return Collect.asArray(new Slot() {
            @Override
            public ItemStack getItem() {
                return i.getItem(s);
            }

            @Override
            public void setItem(ItemStack itemStack) {
                i.setItem(s, itemStack);
            }

            @Override
            protected String toString_i() {
                return "slot " + s + "of " + i.getHolder();
            }

			public boolean isSameSlot(Slot s) {
				return false;
			}
        });
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Slot> getReturnType() {
        return Slot.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "slot";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (i == 0) {
            slot = (Expression<Number>) expressions[0];
            holder = (Expression<InventoryHolder>) expressions[1];
        } else {
            slot = (Expression<Number>) expressions[1];
            holder = (Expression<InventoryHolder>) expressions[0];
        }
        return true;
    }
}
