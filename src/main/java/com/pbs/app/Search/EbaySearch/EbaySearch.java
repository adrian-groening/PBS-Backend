package com.pbs.app.Search.EbaySearch;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pbs.app.Entities.APIKeys;
import com.pbs.app.Entities.Creator;
import com.pbs.app.Entities.Product;
import com.pbs.app.Search.EbaySearch.Root.itemSummary;
import com.pbs.app.Services.Data;

@Service
public class EbaySearch {

    @Autowired
    private Data data;

    private HttpClient client = HttpClient.newHttpClient();
    private final String url = "https://api.ebay.com/buy/browse/v1/item_summary/search";
    private Root Root;
    private String gtin = null;
    private String token = "v^1.1#i^1#r^0#f^0#p^1#I^3#t^H4sIAAAAAAAAAOVYb2wTZRhfu24y/gwTBSYjW7kNIsxr37u21/ZYG7t1Y41A69pNmCHw9u69ctv1rrn3Slfww5gJISwhSnRi0DFFQ4IS/IBE1JhANBAlzH8x+EFiIglIiCTEoKAB77pudJMwwhqzxH5p7nmf93l/v9/7PO8/0FdesXJn284/5pkeMQ/3gT6zyUTNARXlZQ2VpebFZSWgwME03FffZ+kvvdyIYVJKse0IpxQZI2tvUpIxmzP6iLQqswrEImZlmESY1Tg2Gli7hqVtgE2piqZwikRYQ0Ef4YAOl5sCAsO7PQ6Od+pWeSxmTPERXi/npL0uJwROnnFzRjvGaRSSsQZlzUfQgHaSFCBpOkYBFjCsC9gYF91FWDuRikVF1l1sgPDn4LK5vmoB1vtDhRgjVdODEP5QoDUaDoSCLetijfaCWP68DlENamk88atZ4ZG1E0ppdP9hcM6bjaY5DmFM2P2jI0wMygbGwDwE/JzU0Ml4HR7EOHjIO7xeVBQpWxU1CbX74zAsIk8KOVcWyZqoZadSVFcj3o04Lf+1Tg8RClqNv2fTUBIFEak+oqUpsCEQiRD+AK+KUF6tkpGmKBlpD5IQchQTRxxHCozgoOK8Jz/GaKC8wpMGaVZkXjT0wtZ1itaEdMBogizAy7oKZNGdwnJYDQiaAaZQPnpMPqejy5jP0QlMa1tkY0pRUtfAmvucWvzx3pqmivG0hsYjTG7IqaNPcyol8sTkxlwa5jOnF/uILZqWYu32TCZjyzhsipqw0wBQ9vVr10S5LSgJiTFfo9axOHUHUsxR4fS06sUiq2VTOpZePU11AHKC8Du9LoeHyus+EZZ/svVfhgLO9onFUKzi4N1einZRvIcWAOIdxagNfz497QYMFIdZMgnVHqSlJMghktPTLJ1EqsizDpdAOzwCInnGK5BOryCQcRfPkJSAEEAoHue8nv9JiTxokkcRpyKteFlejAzf0NMgCsL6bvU5N4PTQpx7hnFDb3tnONEdgw2gN8i5g4q7qaU31uF70Dq4J/lmSdSVienjF1kAo9anKUKbgjXET4telFNSKKJIIpedWRPsUPkIVLVsFEmSbpgWyUAqFSriKl0Meg++Qjwc5SJvTP/9pnRPVtjI1pnFyuiP9QAwJdqMfcfGKUm7AvXzhmHalEPcaNT6dHiL+kl1RrHWSY6yFfnRI6YtR9mGt3I2FWElreqna1vYOHbFlB4k61uZpiqShNTO6WWAUcrJZFqDcQnNtJouQoKLcIbts5RbvxB6KYYC0+LF5XbRTTNtSSriKjxusNBTH6PtE+/z/pLcj+o3nQL9ps/MJhNoBMuoOrC0vLTDUjp3MRY1ZBOhYMNiQtavqSqy9aBsCoqq+bGS62+/2ta8uCU8uHJ7LPv1/tMlcwueE4Y3gqrxB4WKUmpOwesCWHK3pYyav2ge7aQATVMAMC7QBerutlqohZbHXw9vvrOjLXptrWXPrV+u1qhHrh5OgHnjTiZTWYml31Qy9OMXr7157H373pvqsvdWDq2+crhmRB7a9eGGRfWugcT564ONtQdinxy6uPuHWcztgUtHPiJux7Z92/7rDd9BX43ZX3VmMHxs66xLcm2Vm9pzcN8HC7aVVx8KDV4jL594+huiS7511Pd5XX1ly9nd0pK/hlf7j+wqF5+ffSExcvrnd9a8kPm7qnFj98mLx5cFbl6pPr/7wLvbDy6vzr705AJw/KubI4lVmZN3Bj4+210nL9z/6cDvy5nMlXNPtNLV87mRDj84FantOrNpbzBc42Zv11a+svzEW7dS5/atiL08vGPFTx3uF4/2uMx9m+tnr3rjwp9fNjUMWRJLl1bveuq3ju8frdi/z1954+J3o3P5DxvvbCXoEQAA";

    public EbaySearch() {
    }

    public EbaySearch(Data data) {
        this.data = data;
    }

    public EbaySearch(String code, String type, String email, Data data) throws IOException, InterruptedException {
        try {
            this.data = data;
            Creator creator = data.getCreator(email);
            APIKeys keys = data.getAPIKeys(creator.getCreatorID());
            //token = keys.getEbayKey();

            if (type.equals("barcode")) {
                Root = fetchProductDetailsUsingGtin(code);
            } else if (type.equals("name")) {
                Root = fetchProductDetailsUsingName(code);
            }

        } catch (SQLException ex) {
            System.out.println("Error getting creator details");
        }
    }

    //fetches product details from ebay api using gtin
    private Root fetchProductDetailsUsingGtin(String gtin) throws IOException, InterruptedException {
        String fullUrl = String.format("%s?gtin=%s&limit=15", url, gtin);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + token)
                .header("X-EBAY-C-MARKETPLACE-ID", "EBAY_US")
                .header("X-EBAY-C-ENDUSERCTX", "affiliateCampaignId=<ePNCampaignId>,affiliateReferenceId=<referenceId>")
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Root = gson.fromJson(response.body(), Root.class);
            return gson.fromJson(response.body(), Root.class);
        } else {
            System.out.println("Failed to fetch product details: " + response.statusCode());
            return null;
        }
    }

    //fetches product details from ebay api using name
    private Root fetchProductDetailsUsingName(String name) throws IOException, InterruptedException {
        String fullUrl = String.format("%s?q=%s&limit=15", url, name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + token)
                .header("X-EBAY-C-MARKETPLACE-ID", "EBAY_US")
                .header("X-EBAY-C-ENDUSERCTX", "affiliateCampaignId=<ePNCampaignId>,affiliateReferenceId=<referenceId>")
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Root = gson.fromJson(response.body(), Root.class);
            return gson.fromJson(response.body(), Root.class);
        } else {
            System.out.println("Failed to fetch product details: " + response.statusCode());
            return null;
        }
    }

    //converts product details to json string format
    public String toAppProductList() {
        List<Product> EbayProducts = new ArrayList<Product>();
        if (Root != null) {
            for (int i = 0; i < Root.itemSummaries.size(); i++) {
                Product p;
                itemSummary item = Root.itemSummaries.get(i);
                item.calculateCommission();
                Random rand = new Random();
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay", "ebay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false, item.getItemCreationDate());
                EbayProducts.add(p);
            }
            Gson gson = new Gson();
            String json = gson.toJson(EbayProducts);
            System.out.println("JSON: "+json);
            return json;
        } else {
            System.out.println("No product details found");
            return null;
        }
    }

    //converts product details to list format
    public List<Product> toList() {
        List<Product> EbayProducts = new ArrayList<>();
        if ((Root != null)&& (Root.itemSummaries != null)) {
            for (int i = 0; i < Root.itemSummaries.size(); i++) {
                Product p;
                itemSummary item = Root.itemSummaries.get(i);
                item.calculateCommission();
                Random rand = new Random();
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay","ebay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false, item.getItemCreationDate());
                EbayProducts.add(p);
            }
            return EbayProducts;
        } else {
            System.out.println("No product details found");
            return null;
        }
    }

}
