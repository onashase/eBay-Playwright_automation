package page;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import common.Constants;
import data.SimilarProductsData;


import java.util.List;

public class ProductPage {

    private final Page page;
    private final BrowserContext context;

    //Product Page Common
    private final String lnkEbayLogo = "//a[@class='gh-logo']";

    //Main Product
    private final String lblMainProductPrice = "//div[contains(@id,'mainContent')]//div[contains(@class,'price-primary')]/span";
    private final String lblMainProductName = "//div[@id='mainContent']//h1/span";
    private final String lblProductCategorieName = "//h2[text()='breadcrumb']//following-sibling::ul/li/a/span";
    //private final String lblProductCategoryName = "(//h2[text()='breadcrumb']//following-sibling::ul/li)[#]/a/span";

    //Similar Products
    private final String lblSimilarProducts = "//div[contains(@class,'bottom-river')]//h2[text()='Similar items']";
    private final String lnkSimilarProduct = "//div[contains(@class,'bottom-river')]//div/section/a";
    private final String lblSimilarProductPrice = "(//div[contains(@class,'bottom-river')]//h3)[#]//following-sibling::div[1]//span[contains(text(),'$')]";
    private final String lblSimilarProductName = "(//div[contains(@class,'bottom-river')]//h3)[#]";
    private final String lnkForSimilarProduct = "(//div[contains(@class,'bottom-river')]//a)[#]";

    public ProductPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }


    //Navigate to the Product Page
    public void navigateToProductPage(String productURL) {
        page.navigate(productURL);
        page.setViewportSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    //Wait till product page load
    public boolean waitTillProductPageLoaded() {
        Locator eBayLogo = page.locator(lnkEbayLogo);
        eBayLogo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
        return eBayLogo.isVisible();
    }

    public boolean isSimilarProductsSectionDisplayed() {
        return page.locator(lblSimilarProducts).isVisible();
    }

    public boolean areSimilarProductsAvailable() {
        int productCount = page.locator(lnkSimilarProduct).count();
        return productCount > 0;
    }

    public int getSimilarProductCount() {
        return page.locator(lnkSimilarProduct).count();
    }

    public boolean isSimilarProductInSamePriceRange(int productNumber) {
        double mainProductPrice = Double.parseDouble(page.locator(lblMainProductPrice).textContent().replace("US $", ""));
        String similarProductPriceTag = page.locator(lblSimilarProductPrice.replace("#", String.valueOf(productNumber))).textContent();
        double similarProductPrice = Double.parseDouble(similarProductPriceTag.replace("$", ""));
        return (similarProductPrice >= (mainProductPrice - SimilarProductsData.PRICE_DIFFERENCE) && similarProductPrice <= (mainProductPrice + SimilarProductsData.PRICE_DIFFERENCE));
    }

    public String getSimilarProductName(int productNumber) {
        return page.locator(lblSimilarProductName.replace("#", String.valueOf(productNumber))).textContent();
    }

    public String clickAndGetSimilarProductTitle(int productNumber) {
        Page newPage = context.waitForPage(() -> {
            page.locator(lnkForSimilarProduct.replace("#", String.valueOf(productNumber + 1))).click();
        });
        newPage.setViewportSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        newPage.waitForLoadState();
        newPage.bringToFront();
        String similarProductName = newPage.locator(lblMainProductName).textContent();
        newPage.close();
        return similarProductName;
    }

    public String getProductCategory() {
        Locator breadcrumbTexts = page.locator(lblProductCategorieName);
        List<String> breadcrumbList = breadcrumbTexts.allTextContents();
        return String.join(">", breadcrumbList);
    }

    public String getSimilarProductCategory(int productNumber){
        Page newPage = context.waitForPage(() -> {
            page.locator(lnkForSimilarProduct.replace("#", String.valueOf(productNumber + 1))).click();
        });
        newPage.setViewportSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        newPage.waitForLoadState();
        newPage.bringToFront();
        Locator breadcrumbTexts = page.locator(lblProductCategorieName);
        List<String> breadcrumbList = breadcrumbTexts.allTextContents();
        newPage.close();
        return String.join(">", breadcrumbList);


    }


}
