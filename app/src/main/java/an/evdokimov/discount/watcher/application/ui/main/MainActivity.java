package an.evdokimov.discount.watcher.application.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.configuration.ExceptionService;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.databinding.ActivityMainBinding;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import an.evdokimov.discount.watcher.application.ui.login.LoginActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Inject
    public UserService userService;
    @Inject
    public ErrorMessageService errorMessageService;
    @Inject
    public ProductListAdapter productListAdapter;
    @Inject
    public ShopSpinnerAdapter shopSpinnerAdapter;

    private ActivityMainBinding binding;
    private Spinner shopSpinner;
    private Spinner modeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

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

    private void configureShopSpinner() {
        shopSpinner = binding.shopSpinner;
        shopSpinner.setAdapter(shopSpinnerAdapter);
        shopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productListAdapter.updateProducts();
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
        //modeList.add(getResources().getString(R.string.main_mode_spinner_price_changed));
        modeSpinner.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modeList)
        );
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productListAdapter.updateProducts();
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
}