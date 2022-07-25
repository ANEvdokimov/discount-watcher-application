package an.evdokimov.discount.watcher.application.data.mapper.product;

import java.time.format.DateTimeFormatter;

import an.evdokimov.discount.watcher.application.data.database.product.model.LentaProductPrice;
import an.evdokimov.discount.watcher.application.data.database.product.model.Product;
import an.evdokimov.discount.watcher.application.data.database.product.model.ProductPrice;
import an.evdokimov.discount.watcher.application.ui.main.ProductListItem;

public class ProductListItemMapper {
    public ProductListItem map(Product product) {
        if (product == null) {
            return null;
        }

        ProductListItem productListItem = new ProductListItem();
        ProductPrice lastProductPrice = product.getPrices().get(0);

        productListItem.setProductName(product.getProductInformation().getName());
        productListItem.setShopName(product.getShop().getName());
        productListItem.setAvailabilityInformation(
                lastProductPrice.getAvailabilityInformation());
        productListItem.setDate(lastProductPrice.getDate()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm")));

        if (lastProductPrice.getDiscount() != null && lastProductPrice.getDiscount() != 0) {
            productListItem.setPrice(lastProductPrice.getPrice().toString());
            productListItem.setDiscount(lastProductPrice.getDiscount().toString() + "%");
            productListItem
                    .setPriceWithDiscount(lastProductPrice.getPriceWithDiscount().toString());
        } else {
            productListItem.setDiscount("");
            productListItem.setPriceWithDiscount("");
            if (lastProductPrice instanceof LentaProductPrice) {
                LentaProductPrice lastLentaProductPrice = (LentaProductPrice) lastProductPrice;
                productListItem.setPrice(lastLentaProductPrice.getPriceWithCard().toString());
            } else {
                productListItem.setPrice(lastProductPrice.getPrice().toString());
            }
        }
        return productListItem;
    }
}
