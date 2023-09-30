package an.evdokimov.discount.watcher.application.ui.product.details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.databinding.ActivityProductDetailsBinding;

public class ProductDetailsActivity extends AppCompatActivity {
    private ActivityProductDetailsBinding binding;
    @Inject
    public PriceHystoryListAdapter listAdapter;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        product = (Product) getIntent().getExtras().get("product");

        binding.rwPriceHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rwPriceHistory.setAdapter(listAdapter);

        fillProductInfo();
        loadPriceHistory();

        setContentView(binding.getRoot());
    }

    private void fillProductInfo() {
        binding.productName.setText(product.getProductInformation().getName());
        binding.twShop.setText(product.getShop().getName());
        binding.twChainAndCity.setText(String.format("%s - %s",
                product.getShop().getShopChain().getCyrillicName(),
                product.getShop().getCity().getCyrillicName())
        );

        ProductPrice price = product.getPrices().get(0);
        if (price == null) {
            binding.twActualPrice.setText("N/A");
            binding.twOldPrice.setText("N/A");
            binding.twDiscount.setText("N/A");
            binding.twAvailability.setText("N/A");
            binding.twDate.setText("N/A");
        } else {
            binding.twAvailability.setText(price.getAvailabilityInformation());
            binding.twDate.setText(price.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm")));

            if (price.getDiscount() != null || price.getPriceWithDiscount() != null) {
                binding.twDiscount.setText(price.getDiscount() + "%");
                binding.twActualPrice.setText(price.getPriceWithDiscount().toString());
                binding.twOldPrice.setText(price.getPrice().toString());
            } else {
                binding.twDiscount.setText("");
                binding.twActualPrice.setText(price.getPrice().toString());
                binding.twOldPrice.setText("");
            }
        }
    }

    private void loadPriceHistory() {
        listAdapter.loadPrices(product.getId());
    }
}