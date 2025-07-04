package pl.ujd.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import pl.ujd.cafe.models.Item;

public final class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Item> items;

    public CartAdapter(final List<Item> items) {
        this.items = items;
    }

    public static final class CartViewHolder extends RecyclerView.ViewHolder {

        final TextView view;

        public CartViewHolder(final View view) {
            super(view);
            this.view = view.findViewById(android.R.id.text1);
        }

    }

    @Override public CartViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CartViewHolder(view);
    }

    @Override public void onBindViewHolder(final CartViewHolder holder, int position) {
        final Item item = this.items.get(position);
        holder.view.setText(String.format(Locale.getDefault(), "%s (x%d) (%.2f PLN)", item.getName(), item.getQuantity(), item.getPrice()));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public Item getItem(final int position) {
        return items.get(position);
    }

}