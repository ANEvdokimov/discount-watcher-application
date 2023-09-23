package an.evdokimov.discount.watcher.application.ui.product.details;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.databinding.LayoutPriceHistoryListItemBinding;
import an.evdokimov.discount.watcher.application.service.product.ProductService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class PriceHystoryListAdapter extends RecyclerView.Adapter<PriceHystoryListAdapter.PriceHistoryViewHolder> {
    private final ProductService productService;
    private final ErrorMessageService errorMessageService;
    private List<ProductPrice> prices;

    @Inject
    public PriceHystoryListAdapter(ProductService productService,
                                   ErrorMessageService errorMessageService) {
        this.productService = productService;

        this.errorMessageService = errorMessageService;
        prices = new ArrayList<>();
    }

    public void loadPrices(@NonNull Long productId) {
        productService.getAllPrices(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        prices -> {
                            this.prices = prices;
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
    public PriceHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PriceHistoryViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_price_history_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PriceHistoryViewHolder holder, int position) {
        holder.bind(prices.get(position));
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    static class PriceHistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView availability;
        private final TextView date;
        private final TextView oldPrice;
        private final TextView discount;
        private final TextView actualPrice;

        public PriceHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            LayoutPriceHistoryListItemBinding binding = LayoutPriceHistoryListItemBinding.bind(itemView);

            availability = binding.availability;
            date = binding.date;
            oldPrice = binding.oldPrice;
            discount = binding.discount;
            actualPrice = binding.actualPrice;
        }

        public void bind(ProductPrice price) {
            availability.setText(price.isInStock() ?
                    itemView.getResources().getString(R.string.in_stock)
                    : itemView.getResources().getString(R.string.not_in_stock));
            date.setText(price.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm")));

            if (price.getDiscount() != null && price.getPriceWithDiscount() != null) {
                oldPrice.setText(price.getPrice().toString());
                discount.setText(price.getDiscount() + "%");
                actualPrice.setText(price.getPriceWithDiscount().toString());
            } else {
                oldPrice.setText("");
                discount.setText("");
                actualPrice.setText(price.getPrice().toString());
            }


        }
    }
}
