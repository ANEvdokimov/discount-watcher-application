package an.evdokimov.discount.watcher.application.ui.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.mapper.product.ProductListItemMapper;
import an.evdokimov.discount.watcher.application.databinding.LayoutProductListItemBinding;
import an.evdokimov.discount.watcher.application.service.product.ProductService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private final ProductService productService;
    private final Application application;
    private final ProductListItemMapper productListItemMapper;
    private Context context;
    private List<Product> products;

    @Inject
    public ProductListAdapter(ProductService productService, Application application,
                              ProductListItemMapper productListItemMapper) {
        this.productService = productService;
        this.application = application;
        this.productListItemMapper = productListItemMapper;
        products = new ArrayList<>();
    }

    public void updateProducts() {
        productService.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        products -> {
                            this.products = products;
                            notifyDataSetChanged();
                        },
                        throwable -> {
                            Log.e(getClass().getName(), throwable.getMessage(), throwable);
                            Toast.makeText(
                                    application,
                                    throwable.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                );
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ProductViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.layout_product_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productListItemMapper.map(products.get(position)));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView shopName;
        private final TextView availability;
        private final TextView date;
        private final TextView price;
        private final TextView discount;
        private final TextView priceWithDiscount;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            LayoutProductListItemBinding binding = LayoutProductListItemBinding.bind(itemView);

            name = binding.name;
            shopName = binding.shopName;
            availability = binding.availability;
            date = binding.date;
            price = binding.price;
            discount = binding.discount;
            priceWithDiscount = binding.priceWithDiscount;
        }

        public void bind(ProductListItem productListItem) {
            name.setText(productListItem.getProductName());
            shopName.setText(productListItem.getShopName());
            availability.setText(productListItem.getAvailabilityInformation());
            date.setText(productListItem.getDate());
            price.setText(productListItem.getPrice());
            discount.setText(productListItem.getDiscount());
            priceWithDiscount.setText(productListItem.getPriceWithDiscount());
        }
    }
}
