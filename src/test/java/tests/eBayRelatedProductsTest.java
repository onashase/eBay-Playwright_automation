package tests;

import common.Constants;
import functions.Product;
import functions.TestBase;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class eBayRelatedProductsTest extends TestBase {

    @Test
    public void verifySimilarProductsAreDisplayed() {
        SoftAssert softAssert = new SoftAssert();
        Product.navigateToProductPage(page, context,Constants.PRODUCT_URL);
        softAssert.assertEquals(Product.isProductPageLoaded(page,context), "true","Product Page is not loaded");
        softAssert.assertEquals(Product.isSimilarProductsSectionDisplayed(page,context), "true","Similar Products section is not displayed");
        softAssert.assertEquals(Product.areSimilarProductsAvailable(page,context), "true","Similar Products are not displayed");

    }

    @Test
    public void verifyLessThanSixProductsDisplayed(){
        SoftAssert softAssert = new SoftAssert();
        Product.navigateToProductPage(page,context,Constants.PRODUCT_URL);
        softAssert.assertEquals(Product.isProductPageLoaded(page,context), "true","Product Page is not loaded");
        softAssert.assertEquals(Product.areSimilarProductsAvailable(page,context), "true","Similar Products are not displayed");
        softAssert.assertEquals(Product.getSimilarProductCount(page,context)<6,"true","Six Similar Products or More than six similar products are displayed ");
    }

    @Test
    public void verifySimilarProductsInSamePriceRange(){
        SoftAssert softAssert = new SoftAssert();
        Product.navigateToProductPage(page,context,Constants.PRODUCT_URL);
        softAssert.assertEquals(Product.isProductPageLoaded(page,context), "true","Product Page is not loaded");
        softAssert.assertEquals(Product.areSimilarProductsAvailable(page,context), "true","Similar Products are not displayed");
        int productCount = Product.getSimilarProductCount(page,context);
        for(int i=1; i<=productCount; i++){
            softAssert.assertEquals(Product.isSimilarProductInSamePriceRange(page,context,i),"true","Similar Product: "+i+" is not in the same price range as the main product");
        }

    }

    @Test
    public void verifySuccessfulNavigationToSimilarProduct(){
        SoftAssert softAssert = new SoftAssert();
        Product.navigateToProductPage(page,context,Constants.PRODUCT_URL);
        softAssert.assertEquals(Product.isProductPageLoaded(page,context), "true","Product Page is not loaded");
        softAssert.assertEquals(Product.areSimilarProductsAvailable(page,context), "true","Similar Products are not displayed");
        int productCount = Product.getSimilarProductCount(page,context);
        for(int i=1; i<=productCount; i++){
            String similarProductTitle = Product.getSimilarProductName(page,context,i);
            String clickedSimilarProductTitle = Product.getClickedSimilarProductName(page,context,i);
            softAssert.assertEquals(similarProductTitle,clickedSimilarProductTitle,"Clicked Similar Product Name and Opened Product Name does not match ");
        }

    }

    @Test
    public void verifyMainProductCategoryMatchesSimilarProductCategory(){
        SoftAssert softAssert = new SoftAssert();
        Product.navigateToProductPage(page,context,Constants.PRODUCT_URL);
        softAssert.assertEquals(Product.isProductPageLoaded(page,context), "true","Product Page is not loaded");
        softAssert.assertEquals(Product.areSimilarProductsAvailable(page,context), "true","Similar Products are not displayed");
        String mainProductCategory = Product.getMainProductCategoryName(page,context);
        int productCount = Product.getSimilarProductCount(page,context);
        for(int i=1; i<=productCount; i++){
            String similarProductCategory = Product.getSimilarProductCategoryName(page,context,i);
            softAssert.assertEquals(mainProductCategory,similarProductCategory,"Product"+i+": This Product's category doesn't match with Main Product's Category");
        }

    }





}
