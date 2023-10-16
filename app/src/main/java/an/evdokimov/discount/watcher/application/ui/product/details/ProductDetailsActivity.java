package an.evdokimov.discount.watcher.application.ui.product.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.data.database.product.model.UserProduct;
import an.evdokimov.discount.watcher.application.databinding.ActivityProductDetailsBinding;
import an.evdokimov.discount.watcher.application.service.product.UserProductService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailsActivity extends AppCompatActivity {
    private ActivityProductDetailsBinding binding;
    @Inject
    public PriceHystoryListAdapter listAdapter;
    @Inject
    public UserProductService userProductService;
    @Inject
    public ErrorMessageService errorMessageService;
    private UserProduct userProduct;
    private int userProductPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        userProduct = (UserProduct) getIntent().getExtras().get("product");
        userProductPosition = getIntent().getIntExtra("position", -1);

        binding.rwPriceHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rwPriceHistory.setAdapter(listAdapter);

        fillProductInfo();
        initCheckboxes();
        loadPriceHistory();

        setContentView(binding.getRoot());
    }

    private void fillProductInfo() {
        Product product = userProduct.getProduct();

        binding.productName.setText(product.getProductInformation().getName());
        binding.twShop.setText(product.getShop().getName());
        binding.twChainAndCity.setText(String.format("%s - %s",
                product.getShop().getShopChain().getCyrillicName(),
                product.getShop().getCity().getCyrillicName())
        );

        ProductPrice price = product.getLastPrice();
        if (price == null) {
            binding.twActualPrice.setText("N/A");
            binding.twOldPrice.setText("N/A");
            binding.twDiscount.setText("N/A");
            binding.twAvailability.setText("N/A");
            binding.twDate.setText("N/A");
        } else {
            binding.twAvailability.setText(price.getAvailabilityInformation());
            binding.twDate.setText(price.getDate() != null
                    ? price.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm"))
                    : "date n/a");

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

    private void initCheckboxes() {
        binding.cbDiscount.setChecked(userProduct.isMonitorDiscount());
        binding.cbAvailability.setChecked(userProduct.isMonitorAvailability());
        binding.cbPriceDecrease.setChecked(userProduct.isMonitorPriceChanges());
    }

    @Override
    public void onBackPressed() {
        boolean discountChecked = binding.cbDiscount.isChecked();
        boolean availabilityChecked = binding.cbAvailability.isChecked();
        boolean decreaseChecked = binding.cbPriceDecrease.isChecked();

        if (discountChecked != userProduct.isMonitorDiscount()
                || availabilityChecked != userProduct.isMonitorAvailability()
                || decreaseChecked != userProduct.isMonitorPriceChanges()) {
            userProduct.setMonitorDiscount(discountChecked);
            userProduct.setMonitorAvailability(availabilityChecked);
            userProduct.setMonitorPriceChanges(decreaseChecked);

            userProductService.update(userProduct)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> Toast.makeText(
                                    this,
                                    "Изменения сохранены",
                                    Toast.LENGTH_SHORT
                            ).show(), //todo translate
                            throwable -> {
                                Log.e(getClass().getName(), throwable.getMessage(), throwable);
                                errorMessageService.showErrorMessage(throwable.getMessage());
                            });

            Intent data = new Intent();
            data.putExtra("position", userProductPosition);
            data.putExtra("userProduct", userProduct);
            setResult(Activity.RESULT_OK, data);
        } else {
            setResult(Activity.RESULT_CANCELED, null);
        }

        super.onBackPressed();
    }

    private void loadPriceHistory() {
        listAdapter.loadPrices(userProduct.getProduct().getId());
    }
}