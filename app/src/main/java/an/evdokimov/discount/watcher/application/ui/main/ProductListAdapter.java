package an.evdokimov.discount.watcher.application.ui.main;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.databinding.LayoutProductListItemBinding;
import an.evdokimov.discount.watcher.application.service.product.ProductService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import an.evdokimov.discount.watcher.application.ui.product.details.ProductDetailsActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private final ProductService productService;
    private final ErrorMessageService errorMessageService;
    private List<Product> products;

    @Inject
    public ProductListAdapter(ProductService productService,
                              ErrorMessageService errorMessageService) {
        this.productService = productService;

        this.errorMessageService = errorMessageService;
        products = new ArrayList<>();
    }

    public void updateProducts(@NonNull Boolean onlyActive,
                               @Nullable Shop shop,
                               @Nullable Boolean monitorAvailability,
                               @Nullable Boolean monitorDiscount,
                               @Nullable Boolean monitorPriceChanges) {
        productService.getAll(
                        false,
                        onlyActive,
                        shop,
                        monitorAvailability,
                        monitorDiscount,
                        monitorPriceChanges
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        products -> {
                            this.products = products;
                            notifyDataSetChanged();
                        },
                        throwable -> {
                            Log.e(getClass().getName(), throwable.getMessage(), throwable);
                            errorMessageService.showErrorMessage(throwable.getMessage());
                        }
                );
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_product_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView shopName;
        private final TextView availability;
        private final TextView date;
        private final TextView oldPrice;
        private final TextView discount;
        private final TextView actualPrice;

        private Product currentProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            LayoutProductListItemBinding binding = LayoutProductListItemBinding.bind(itemView);

            name = binding.name;
            shopName = binding.shopName;
            availability = binding.availability;
            date = binding.date;
            oldPrice = binding.oldPrice;
            discount = binding.discount;
            actualPrice = binding.actualPrice;

            itemView.setOnClickListener(this);
        }

        public void bind(Product product) {
            currentProduct = product;

            ProductPrice productPrice = product.getPrices().get(0);

            name.setText(product.getProductInformation().getName());
            shopName.setText(product.getShop().getName());

            if (productPrice == null) {
                availability.setText("N/A");
                date.setText("N/A");
                oldPrice.setText("N/A");
                discount.setText("N/A");
                actualPrice.setText("N/A");
            } else {
                availability.setText(productPrice.getAvailabilityInformation());
                date.setText(productPrice.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm")));

                if (productPrice.getDiscount() != null && productPrice.getPriceWithDiscount() != null) {
                    actualPrice.setText(productPrice.getPriceWithDiscount().toString());
                    oldPrice.setText(productPrice.getPrice().toString());
                    discount.setText(productPrice.getDiscount().toString());
                } else {
                    actualPrice.setText(productPrice.getPrice().toString());
                    oldPrice.setText("");
                    discount.setText("");
                }
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
            intent.putExtra("product", currentProduct);
            itemView.getContext().startActivity(intent);
        }
    }
}
