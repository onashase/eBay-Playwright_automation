package functions;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import page.ProductPage;

public class Product {
    private Product(){

    }

    public static void navigateToProductPage(Page page, BrowserContext context, String productURL){
        ProductPage productPage = new ProductPage(page,context);
        productPage.navigateToProductPage(productURL);
    }

    public static boolean isProductPageLoaded(Page page,BrowserContext context){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.waitTillProductPageLoaded();
    }

    public static boolean isSimilarProductsSectionDisplayed(Page page,BrowserContext context){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.isSimilarProductsSectionDisplayed();

    }
    public static boolean areSimilarProductsAvailable(Page page,BrowserContext context){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.areSimilarProductsAvailable();

    }
    public static int getSimilarProductCount(Page page,BrowserContext context){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.getSimilarProductCount();
    }

    public static boolean isSimilarProductInSamePriceRange(Page page,BrowserContext context,int productNumber){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.isSimilarProductInSamePriceRange(productNumber);
    }

    public static String getSimilarProductName(Page page,BrowserContext context,int productNumber){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.getSimilarProductName(productNumber);
    }

    public static String getClickedSimilarProductName(Page page,BrowserContext context,int productNumber){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.clickAndGetSimilarProductTitle(productNumber);
    }

    public static String getMainProductCategoryName(Page page,BrowserContext context){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.getProductCategory();
    }

    public static String getSimilarProductCategoryName(Page page,BrowserContext context,int productNumber){
        ProductPage productPage = new ProductPage(page,context);
        return productPage.getSimilarProductCategory(productNumber);
    }



}
