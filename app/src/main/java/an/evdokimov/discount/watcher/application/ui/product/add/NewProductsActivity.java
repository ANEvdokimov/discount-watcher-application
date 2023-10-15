package an.evdokimov.discount.watcher.application.ui.product.add;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import an.evdokimov.discount.watcher.application.R;
import an.evdokimov.discount.watcher.application.configuration.DiscountWatcherApplication;
import an.evdokimov.discount.watcher.application.data.web.product.dto.request.NewProduct;
import an.evdokimov.discount.watcher.application.databinding.ActivityNewProductsBinding;
import an.evdokimov.discount.watcher.application.service.product.UserProductService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewProductsActivity extends AppCompatActivity {
    @Inject
    public UserProductService userProductService;

    private WebView webPage;
    private FloatingActionButton saveProductButton;
    private final List<Pattern> productPageUrlPatterns = new ArrayList<>() {{
        add(Pattern.compile("https://magnit.ru/catalog/\\d+/?"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DiscountWatcherApplication) getApplicationContext())
                .applicationComponent.inject(this);

        ActivityNewProductsBinding binding =
                ActivityNewProductsBinding.inflate(getLayoutInflater());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        webPage = binding.webPage;
        saveProductButton = binding.saveProduct;
        saveProductButton.setOnClickListener(view -> {
            saveProduct();
        });
        setUpWebView();

        setContentView(binding.getRoot());
    }

    private void setUpWebView() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://magnit.ru/");
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ru,en-US;q=0.9,en;q=0.8");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) " +
                "AppleWebKit/535.19 (KHTML, like Gecko) " +
                "Chrome/18.0.1025.133 Mobile Safari/535.19");

        CookieManager.getInstance().setAcceptThirdPartyCookies(webPage, true);
        webPage.getSettings().setJavaScriptEnabled(true);
        webPage.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webPage.getSettings().setDomStorageEnabled(true);
        webPage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                boolean isProductPage = validateUrl(url);
                saveProductButton.setEnabled(isProductPage);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
        webPage.loadUrl("https://magnit.ru/", headers);
    }

    private void saveProduct() {
        String productPage = webPage.getUrl();

        boolean urlIsCorrect = validateUrl(productPage);
        if (!urlIsCorrect) {
            Toast.makeText(this, getResources().getString(R.string.it_is_not_product_page),
                    Toast.LENGTH_SHORT).show();
            Log.e(this.getClass().getName(), String.format("Wrong product URL %s", productPage));
            return;
        }

        String[] cookies = CookieManager.getInstance().getCookie(productPage).split(";");
        String cookieShopCode = "";
        String cookieShopId = "";
        for (String cookie : cookies) {//todo получать требуемые куки для поиска в БД
            cookie = cookie.trim();
            if (cookie.startsWith("shopCode")) {
                cookieShopCode = cookie;
            }
            if (cookie.startsWith("shopId")) {
                cookieShopId = cookie;
            }
        }

        NewProduct newProduct = NewProduct.builder()
                .url(productPage)
                .cookies(cookieShopCode + "; " + cookieShopId)
                .monitorDiscount(true)//todo переделать захардкоженное
                .monitorPriceChanges(true)
                .monitorAvailability(true)
                .build();

        userProductService.addProduct(newProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Toast.makeText(
                                this,
                                getResources().getString(R.string.new_product_saved),
                                Toast.LENGTH_SHORT
                        ).show(),
                        throwable -> Toast.makeText(
                                this,
                                getResources().getString(R.string.new_product_saving_failure) +
                                        ": " + throwable.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show()
                );
    }

    private boolean validateUrl(@Nullable String productPage) {
        if (productPage == null) {
            Log.e(this.getClass().getName(), "Product URL is null");
            return false;
        }

        return productPageUrlPatterns.stream()
                .anyMatch(pattern -> pattern.matcher(productPage).find());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        webPage.goBack();
    }
}