package com.mobilesoft.bonways.backend;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lyonnel Dzotang on 07/02/2017.
 */

public class DummyServer {

    public static List<Product> getAvailableProduct() {
        ArrayList<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setDesignation("Jeu de Carte");
        product.setDescription("Jeu de carte qui vous permettra de vous de detendre en famille");
        product.setUnitQuantity((long) 78);
        product.setDateTimeOff("16/04/2017");
        product.setPrice(2000L);
        product.setDiscountPercentage(20L);
        for (int i = 1; i <= 32; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 11; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("http://magicien.ch/magasin-magie/images/jeux-cartes-bicycle-pack-bridge.jpg");
        product.setTrade(getTrade().get(0));
        product.setCategory(getCategory().get(5));
        products.add(product);

        product = new Product();
        product.setDesignation("BabyFoot");
        product.setDescription("Relaxez vous en famille ou entre collègue avec une partie de Babyfoot ");
        product.setUnitQuantity((long) 87);
        product.setDateTimeOff("11/04/2017");
        product.setPrice(8000L);
        product.setDiscountPercentage(32L);
        for (int i = 1; i <= 44; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 9; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("http://www.renaud-bray.com/ImagesEditeurs/PG/1446/1446905-gf.jpg");
        product.setTrade(getTrade().get(0));
        product.setCategory(getCategory().get(5));

        products.add(product);


        product = new Product();
        product.setDesignation("Table Basse");
        product.setDescription("Donnez vie à votre salon avec cette magnifique table basse");
        product.setUnitQuantity((long) 2);
        product.setDateTimeOff("12/05/2017");
        product.setPrice(25500L);
        product.setDiscountPercentage(45L);
        for (int i = 1; i <= 39; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 6; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("https://img1.dlmcdn.fr/a/1254/MSA1254399-0403-0300-p00-table-basse-rectangulaire-chene-massif-aboute-huile-l115xl100xh73cm-hawke.jpg");
        product.setTrade(getTrade().get(1));
        product.setCategory(getCategory().get(2));

        products.add(product);


        product = new Product();
        product.setDesignation("Television Samsung 52'");
        product.setDescription("La télévision en très haute dimension ");
        product.setUnitQuantity((long) 6);
        product.setDateTimeOff("15/06/2017");
        product.setPrice(322000l);
        product.setDiscountPercentage(12L);
        for (int i = 1; i <= 89; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 43; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("http://cdn.villatech.fr/media/catalog/product/cache/4/image/580x/9df78eab33525d08d6e5fb8d27136e95/1/0/1000040967_UE_55_F_6100-2.jpg");
        product.setTrade(getTrade().get(2));
        product.setCategory(getCategory().get(5));

        products.add(product);


        product = new Product();
        product.setDesignation("Rallonge Legrand 7 entrées");
        product.setDescription("Branchez plus d'equipement dans votre salon grace à cette equipement discret et resistant.");
        product.setUnitQuantity((long) 11);
        product.setDateTimeOff("12/04/2017");
        product.setPrice(2000l);
        product.setDiscountPercentage(33L);
        for (int i = 1; i <= 26; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 11; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("http://i2.cdscdn.com/pdt2/6/2/7/1/700x700/leg3245060500627/rw/legrand-rallonge-multiprise-electrique-std-6-prise.jpg");
        product.setTrade(getTrade().get(4));
        product.setCategory(getCategory().get(2));

        products.add(product);


        product = new Product();
        product.setDesignation("HP ProBook");
        product.setDescription("Core i5, 8GB Ram, 1To DD. \nLa puissance au bout des doigts");
        product.setUnitQuantity((long) 9);
        product.setDateTimeOff("25/04/2017");
        product.setPrice(212000l);
        product.setDiscountPercentage(5.5);
        for (int i = 1; i <= 78; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 34; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("http://www.notebookcheck.biz/uploads/tx_nbc2/978509.jpg");
        product.setTrade(getTrade().get(1));
        product.setCategory(getCategory().get(5));

        products.add(product);


        product = new Product();
        product.setDesignation("Rolex Revolution");
        product.setDescription("Montre de luxe, Pour un homme qui se respecte.");
        product.setUnitQuantity((long) 23);
        product.setDateTimeOff("30/08/2017");
        product.setPrice(22000L);
        product.setDiscountPercentage(21.0);
        for (int i = 1; i <= 98; i++) {
            String n = ""+i;
            product.getWatchers().add(n);

        }
        for (int i = 1; i <= 34; i++) {
            String n = ""+i;
            product.getLikers().add(n);

        }
        product.setImageUrl("https://content.rolex.com/is/image/Rolex/?src=is%7BRolex%2Fshadow_oyster_perpetual_39%3Flayer%3D1%26src%3D50683%26layer%3D2%26src%3D50684_g_39%26layer%3D3%26src%3D50682%7D&$rv55-watch-grid$");
        product.setTrade(getTrade().get(3));
        product.setCategory(getCategory().get(5));

        products.add(product);

        return products;


    }

    public static List<Trade> getTrade() {
        List<Trade> trades = new ArrayList<>();

        Trade trade = new Trade();
        trade.setName("Santa Lucia");
        trade.setRepresenterName("Moussa");
        trade.setAddress("33 Marie Salomon, Ange Raphael, Douala");
        trade.setNearestShopName("Le Bourget");
        trade.setEmail("info@santalucia.com");
        trade.setWebsite("santalucia.org");
        trade.setPhone("677998866");
        trade.setLogoUrl("http://hotel.complexesantalucia.com/wp-content/uploads/sites/16/2012/09/itl-300x179.png");
        trade.setLatitude(4.051782);
        trade.setLongitude(9.737898);
        trade.setMainCategory(getCategory().get(5).getTitle());

        trades.add(trade);


        trade = new Trade();
        trade.setName("Super U");
        trade.setRepresenterName("Jacob");
        trade.setAddress("1377 Rue Michel Brunet, Bali, Douala");
        trade.setNearestShopName("Total");
        trade.setEmail("info@superu.com");
        trade.setWebsite("superu.org");
        trade.setPhone("677998866");
        trade.setLogoUrl("https://www.labresse.net/medias/images/prestataires/super-u-la-bresse-795.jpg");
        trade.setLatitude(4.035187);
        trade.setLongitude(9.691819);
        trade.setMainCategory(getCategory().get(5).getTitle());

        trades.add(trade);


        trade = new Trade();
        trade.setName("Dovv");
        trade.setRepresenterName("Aboubakar");
        trade.setAddress("43 Rue Amidou, Bonapriso, Douala");
        trade.setNearestShopName("Oil Lybia");
        trade.setEmail("info@Dovv.com");
        trade.setWebsite("Dovv.org");
        trade.setPhone("677998866");
        trade.setLogoUrl("http://www.wasamundi.com/farm/access/var/watermark/wtmk_5460cc1a6d02614156298501397903_821082011257158_5658290838562498125_o.jpg");
        trade.setLatitude(4.028123);
        trade.setLongitude(9.698278);
        trade.setMainCategory(getCategory().get(5).getTitle());

        trades.add(trade);


        trade = new Trade();
        trade.setName("Zara");
        trade.setRepresenterName("Felix");
        trade.setAddress("34 Boulevard de la Reunification, Akwa, Douala");
        trade.setNearestShopName("Orange");
        trade.setEmail("info@Zara.com");
        trade.setWebsite("Zara.org");
        trade.setPhone("677998866");
        trade.setLogoUrl("https://lh4.ggpht.com/a-C0pps0yjA4gp7TeEg2mFaDAef13cPHDGJtd2Tigbln-EeFCA6KlMySqNXgEt2J38E=w300");
        trade.setLatitude(4.049218);
        trade.setLongitude(9.694730);
        trade.setMainCategory(getCategory().get(1).getTitle());

        trades.add(trade);


        trade = new Trade();
        trade.setName("Castorama");
        trade.setRepresenterName("Frederic");
        trade.setAddress("12 Rue Foch, Akwa, Douala");
        trade.setNearestShopName("MTN Shop");
        trade.setEmail("info@Castorama.com");
        trade.setWebsite("santalucia.org");
        trade.setPhone("677998866");
        trade.setLogoUrl("http://www.services-reclamation.com/wp-content/uploads/2016/04/reclamation-castorama.png");
        trade.setLatitude(4.052715);
        trade.setLongitude(9.697073);
        trade.setMainCategory(getCategory().get(2).getTitle());

        trades.add(trade);

        return trades;

    }

    public static List<Category> getCategory() {
        List<Category> categories = new ArrayList<>();

        Category category = new Category();
        category.setTitle("Tout");
        category.setIconIntUrl(R.drawable.cat_all);

        categories.add(category);


         category = new Category();
        category.setTitle("Beauté");
        category.setIconIntUrl(R.drawable.cat_beaute);

        categories.add(category);

        category = new Category();
        category.setTitle("Brico/Deco");
        category.setIconIntUrl(R.drawable.cat_bricolage);

        categories.add(category);


        category = new Category();
        category.setTitle("Kdo");
        category.setIconIntUrl(R.drawable.cat_cadeau);

        categories.add(category);

        category = new Category();
        category.setTitle("Transport");
        category.setIconIntUrl(R.drawable.cat_transport);

        categories.add(category);


        category = new Category();
        category.setTitle("Supermarché");
        category.setIconIntUrl(R.drawable.cat_epicerie);

        categories.add(category);

        category = new Category();
        category.setTitle("Banque/Service");
        category.setIconIntUrl(R.drawable.cat_service);

        categories.add(category);

        category = new Category();
        category.setTitle("Sortie");
        category.setIconIntUrl(R.drawable.cat_divertissement);

        categories.add(category);

        return categories;

    }
}
