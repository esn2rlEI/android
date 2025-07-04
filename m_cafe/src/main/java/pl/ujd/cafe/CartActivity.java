package pl.ujd.cafe;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import pl.ujd.cafe.models.Item;

public final class CartActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_cart);

        ((TextView) this.findViewById(R.id.cart_worth)).setText(
                String.format(Locale.getDefault(), "Total: %.2f PLN", CartManager.getInstance().getItemsWorth()));

        final RecyclerView recycler = this.findViewById(R.id.cart_list);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        final CartAdapter adapter = new CartAdapter(CartManager.getInstance().getItems());
        recycler.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override public boolean onMove(final RecyclerView rv, final RecyclerView.ViewHolder vh,final RecyclerView.ViewHolder target) {return false;}
            @Override public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
                final int position = viewHolder.getAdapterPosition();
                final Item item = adapter.getItem(position);
                CartManager.getInstance().removeItem(item.getName());
                adapter.notifyItemRemoved(position);
                ((TextView) findViewById(R.id.cart_worth)).setText(
                    String.format(Locale.getDefault(), "Total: %.2f PLN", CartManager.getInstance().getItemsWorth()));
            }
        }).attachToRecyclerView(recycler);

//        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CartManager.getInstance().getItems()) {
//            @NotNull @Override public View getView(final int position, final View convertView, final ViewGroup parent) {
//                final TextView view = (TextView) super.getView(position, convertView, parent);
//                final Item item = this.getItem(position);
//                view.setText(String.format(Locale.getDefault(), "%s (x%d) (%.2f PLN)", item.getName(), item.getQuantity(), item.getPrice()));
//                return view;
//            }
//        };
//        ((ListView) this.findViewById(R.id.cart_list)).setAdapter(adapter);
//        ((ListView) this.findViewById(R.id.cart_list)).setOnItemClickListener((parent, view, position, id) -> {
//            final Item item = CartManager.getInstance().getItems().get(position);
//            CartManager.getInstance().removeItem(item.getName());
//            adapter.notifyDataSetChanged();
//            ((TextView) this.findViewById(R.id.cart_worth)).setText(
//                    String.format(Locale.getDefault(), "Total: %.2f PLN", CartManager.getInstance().getItemsWorth()));
//        });

        this.findViewById(R.id.checkout_button).setOnClickListener(view -> {
            if (!CartManager.getInstance().getItems().isEmpty()) {
                Toast.makeText(this, "Checkout", Toast.LENGTH_SHORT).show();
                CartManager.getInstance().clear();
//                adapter.clear();
                adapter.notifyDataSetChanged();
                ((TextView) this.findViewById(R.id.cart_worth)).setText(
                        String.format(Locale.getDefault(), "Total: %.2f PLN", CartManager.getInstance().getItemsWorth()));
            } else Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        });
    }

}