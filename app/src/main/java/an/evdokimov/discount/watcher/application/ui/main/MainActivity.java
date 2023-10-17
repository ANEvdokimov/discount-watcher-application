package an.evdokimov.discount.watcher.application.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.configuration.ExceptionService;
import an.evdokimov.discount.watcher.application.data.database.product.model.UserProduct;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.databinding.ActivityMainBinding;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import an.evdokimov.discount.watcher.application.ui.login.LoginActivity;
import an.evdokimov.discount.watcher.application.ui.product.add.NewProductsActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;

public class MainActivity extends AppCompatActivity {
    @Inject
    public UserService userService;
    @Inject
    public ErrorMessageService errorMessageService;
    @Inject
    public ProductListAdapter productListAdapter;
    @Inject
    public ShopSpinnerAdapter shopSpinnerAdapter;

    @Getter
    private ActivityResultLauncher<Intent> productDetailsActivityLauncher;
    private ActivityMainBinding binding;
    private Spinner shopSpinner;
    private Spinner modeSpinner;
    private CheckBox isActiveCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        configureAddProductButton();
        configureOnlyActiveCheckbox();
        configureModeSpinner();
        configureShopSpinner();
        configureProductList();

        userService.getActive()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateContent,
                        ExceptionService::throwException,
                        this::openLoginActivityAndShowContent
                );

        initProductDetailActivityLauncher();

        setContentView(binding.getRoot());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateContent();
    }

    protected void openLoginActivityAndShowContent() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void updateContent(User activeUser) {
        updateContent();
    }

    protected void updateContent() {
        shopSpinnerAdapter.updateShopSpinner();
    }

    protected void updateProductList() {
        Boolean monitorAvailability = modeSpinner.getSelectedItemPosition() == 2 ? true : null;
        Boolean monitorDiscount = modeSpinner.getSelectedItemPosition() == 1 ? true : null;
        Boolean monitorPriceChanges = modeSpinner.getSelectedItemPosition() == 3 ? true : null;

        productListAdapter.updateProducts(
                isActiveCheckbox.isChecked(),
                (Shop) shopSpinner.getSelectedItem(),
                monitorAvailability,
                monitorDiscount,
                monitorPriceChanges
        );
    }

    private void configureOnlyActiveCheckbox() {
        isActiveCheckbox = binding.onlyActive;
        isActiveCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> updateProductList());
    }

    private void configureShopSpinner() {
        shopSpinner = binding.shopSpinner;
        shopSpinner.setAdapter(shopSpinnerAdapter);
        shopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateProductList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shopSpinner.setSelection(0);
            }
        });
    }

    private void configureModeSpinner() {
        modeSpinner = binding.modeSpinner;
        ArrayList<String> modeList = new ArrayList<>();
        modeList.add(getResources().getString(R.string.main_mode_spinner_all));
        modeList.add(getResources().getString(R.string.main_mode_spinner_discount));
        modeList.add(getResources().getString(R.string.main_mode_spinner_available));
        modeList.add(getResources().getString(R.string.main_mode_spinner_price_changed));
        modeSpinner.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modeList)
        );
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateProductList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                modeSpinner.setSelection(0);
            }
        });
    }

    private void configureProductList() {
        RecyclerView productList = binding.productList;
        productList.setLayoutManager(new LinearLayoutManager(this));
        productList.setAdapter(productListAdapter);
    }

    public void configureAddProductButton() {
        FloatingActionButton newProduct = binding.addNewProduct;
        newProduct.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewProductsActivity.class);
            startActivity(intent);
        });
    }

    private void initProductDetailActivityLauncher() {
        productDetailsActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        int position = result.getData().getIntExtra("position", -1);
                        UserProduct userProduct = (UserProduct) result.getData()
                                .getSerializableExtra("userProduct");
                        productListAdapter.updateProduct(position, userProduct);
                    }
                });
    }
}