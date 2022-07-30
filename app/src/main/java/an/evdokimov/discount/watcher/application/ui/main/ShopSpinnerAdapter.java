package an.evdokimov.discount.watcher.application.ui.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.configuration.ResourceService;
import an.evdokimov.discount.watcher.application.data.database.shop.model.Shop;
import an.evdokimov.discount.watcher.application.service.shop.ShopService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShopSpinnerAdapter extends BaseAdapter {
    private final ShopService shopService;
    private final ErrorMessageService errorMessageService;
    private final ResourceService resourceService;

    private final Map<Integer, Shop> userShops;

    @Inject
    public ShopSpinnerAdapter(ShopService shopService,
                              ErrorMessageService errorMessageService,
                              ResourceService resourceService) {
        this.shopService = shopService;
        this.errorMessageService = errorMessageService;
        this.resourceService = resourceService;
        userShops = new HashMap<>();
    }

    public void updateShopSpinner() {
        shopService.getAllUserShops()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateDataSet,
                        throwable -> {
                            Log.e(getClass().getName(), throwable.getMessage(), throwable);
                            errorMessageService.showErrorMessage(throwable.getMessage());
                        }
                );
    }

    private void updateDataSet(List<Shop> shops) {
        userShops.clear();
        if (shops.size() == 1) {
            userShops.put(0, shops.get(0));
        } else {
            userShops.put(0, null);
            int position = 1;
            for (Shop shop : shops) {
                userShops.put(position++, shop);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userShops.size();
    }

    @Override
    public Shop getItem(int position) {
        return userShops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_item, parent, false);
        } else {
            textView = (TextView) convertView;
        }

        if (userShops.get(position) == null) {
            textView.setText(
                    resourceService.getStringResource(R.string.main_shops_spinner_all_shops)
            );
        } else {
            textView.setText(userShops.get(position).getName());
        }

        return textView;
    }
}
