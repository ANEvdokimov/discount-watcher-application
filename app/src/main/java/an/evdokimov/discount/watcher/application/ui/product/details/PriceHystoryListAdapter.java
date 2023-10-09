package an.evdokimov.discount.watcher.application.ui.product.details;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.databinding.LayoutPriceHistoryListItemBinding;
import an.evdokimov.discount.watcher.application.service.product.ProductPriceService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class PriceHystoryListAdapter extends RecyclerView.Adapter<PriceHystoryListAdapter.PriceHistoryViewHolder> {
    private final ProductPriceService priceService;
    private final ErrorMessageService errorMessageService;
    private List<ProductPrice> prices;

    @Inject
    public PriceHystoryListAdapter(ProductPriceService priceService,
                                   ErrorMessageService errorMessageService) {
        this.priceService = priceService;

        this.errorMessageService = errorMessageService;
        prices = new ArrayList<>();
    }

    public void loadPrices(@NonNull Long productId) {
        priceService.getByProductId(productId, true, LocalDate.now().minusMonths(3))
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
        private final Drawable iconEqual;
        private final Drawable iconUp;
        private final Drawable iconDown;
        private final Drawable iconDots;
        private final Drawable iconCross;

        private final TextView availability;
        private final TextView date;
        private final TextView oldPrice;
        private final TextView discount;
        private final TextView actualPrice;
        private final ImageView iwPriceChange;

        public PriceHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            LayoutPriceHistoryListItemBinding binding = LayoutPriceHistoryListItemBinding.bind(itemView);

            availability = binding.availability;
            date = binding.date;
            oldPrice = binding.oldPrice;
            discount = binding.discount;
            actualPrice = binding.actualPrice;
            iwPriceChange = binding.iwPriceChange;

            iconEqual = AppCompatResources.getDrawable(
                    itemView.getContext(),
                    R.drawable.equal_sign_svgrepo_com
            );
            iconUp = AppCompatResources.getDrawable(
                    itemView.getContext(),
                    R.drawable.arrow_up_svgrepo_com
            );
            iconDown = AppCompatResources.getDrawable(
                    itemView.getContext(),
                    R.drawable.arrow_down_svgrepo_com
            );
            iconDots = AppCompatResources.getDrawable(
                    itemView.getContext(),
                    R.drawable.dots_horizontal_svgrepo_com
            );
            iconCross = AppCompatResources.getDrawable(
                    itemView.getContext(),
                    R.drawable.close_svgrepo_com
            );
        }

        public void bind(ProductPrice price) {
            availability.setText(price.isInStock() ?
                    itemView.getResources().getString(R.string.in_stock)
                    : itemView.getResources().getString(R.string.not_in_stock));
            date.setText(price.getDate() != null
                    ? price.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm"))
                    : "date n/a");

            if (price.getDiscount() != null || price.getPriceWithDiscount() != null) {
                oldPrice.setText(price.getPrice().toString());
                discount.setText(price.getDiscount() + "%");
                actualPrice.setText(price.getPriceWithDiscount().toString());
            } else {
                oldPrice.setText("");
                discount.setText("");
                actualPrice.setText(price.getPrice().toString());
            }

            if (price.getPriceChange() != null) {
                switch (price.getPriceChange()) {
                    case UP:
                        iwPriceChange.setImageDrawable(iconUp);
                        break;
                    case DOWN:
                        iwPriceChange.setImageDrawable(iconDown);
                        break;
                    case EQUAL:
                        iwPriceChange.setImageDrawable(iconEqual);
                        break;
                    case UNDEFINED:
                        iwPriceChange.setImageDrawable(iconCross);
                        break;
                    case FIRST_PRICE:
                    default:
                        iwPriceChange.setImageDrawable(iconDots);
                }
            }
        }
    }
}
