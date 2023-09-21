package an.evdokimov.discount.watcher.application.ui.product.add;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import an.evdokimov.discount.watcher.application.data.database.city.model.City;
import an.evdokimov.discount.watcher.application.service.city.CityService;
import an.evdokimov.discount.watcher.application.ui.ErrorMessageService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class CitySpinnerAdapter extends BaseAdapter {
    private final CityService service;
    private final ErrorMessageService errorMessageService;

    private final List<City> cities;

    @Inject
    public CitySpinnerAdapter(CityService service, ErrorMessageService errorMessageService) {
        this.service = service;
        this.errorMessageService = errorMessageService;
        cities = new ArrayList<>();
    }

    public void updateCitySpinner() {
        service.getAll()
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

    private void updateDataSet(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public City getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cities.get(position).getId();
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

        textView.setText(cities.get(position).getCyrillicName());//TODO add english language support
        return textView;
    }
}
