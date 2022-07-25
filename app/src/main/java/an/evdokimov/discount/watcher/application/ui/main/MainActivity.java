package an.evdokimov.discount.watcher.application.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.configuration.ExceptionService;
import an.evdokimov.discount.watcher.application.data.database.user.model.User;
import an.evdokimov.discount.watcher.application.databinding.ActivityMainBinding;
import an.evdokimov.discount.watcher.application.service.user.UserService;
import an.evdokimov.discount.watcher.application.ui.login.LoginActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Inject
    public UserService userService;

    @Inject
    public ProductListAdapter productListAdapter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        RecyclerView productList = binding.productList;
        productList.setLayoutManager(new LinearLayoutManager(this));
        productList.setAdapter(productListAdapter);

        userService.getActive()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateProductList,
                        ExceptionService::throwException,
                        this::openLoginActivityAndShowContent
                );

        setContentView(binding.getRoot());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateProductList();
    }

    protected void openLoginActivityAndShowContent() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void updateProductList(User activeUser) {
        updateProductList();
    }

    protected void updateProductList(){
        productListAdapter.updateProducts();
    }
}