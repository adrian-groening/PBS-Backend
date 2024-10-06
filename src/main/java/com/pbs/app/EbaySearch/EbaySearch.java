package com.pbs.app.EbaySearch;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import com.google.gson.Gson;
import com.pbs.Entities.Product;
import com.pbs.app.EbaySearch.Root.itemSummary;
import com.pbs.app.ImpactSearch.ProductResults.Result;
import com.pbs.app.Services.Data;

public class EbaySearch {

    private HttpClient client = HttpClient.newHttpClient();
    private final String url = "https://api.ebay.com/buy/browse/v1/item_summary/search";
    private Root Root;
    String gtin = "";

    public EbaySearch(String code, String type) throws IOException, InterruptedException {
    
        if (type.equals("barcode")) {
            Root = fetchProductDetailsUsingGtin(code);
        } else if (type.equals("name")) {
            Root = fetchProductDetailsUsingName(code);
        }

    }

    private Root fetchProductDetailsUsingGtin(String gtin) throws IOException, InterruptedException {
        String fullUrl = String.format("%s?gtin=%s&limit=15", url, gtin);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + "v^1.1#i^1#p^1#I^3#r^0#f^0#t^H4sIAAAAAAAAAOVYa2wUVRTu9gGUlxHxAQGpA1SgzuydmX1OuptuH0hj6RZ2qbQB4c7MnXbo7sww967djUmpJYIxiBJTJUhIIQSjiaKRoIaUEBAVjKTR+EcTTaohGgIRgg8Src5MH2wrgYZuTBP3z2bOPffc7/vuOfeeGdA5pXjFjlU7fp/lmprf0wk6810udgYonlJUNrsgf35RHshycPV0Luks7Cr4qRzDZMIQ1iJs6BpGJelkQsOCYwxRKVMTdIhVLGgwibBAJCEWWV0ncAwQDFMnuqQnqJLa6hAV9HI8hJD3sDILAzBgWbXhmHE9RHk9nJcXZcnH8ggFWGSNY5xCtRomUCMhigOchwZBmvPHQVBggQB4xsN5mqmSRmRiVdcsFwZQYQeu4Mw1s7DeHirEGJnECkKFayMrY9FIbXVNfbzcnRUrPKRDjECSwqOfqnQZlTTCRArdfhnseAuxlCQhjCl3eHCF0UGFyDCYu4DvSC17/cAvKn6IpCBEopITKVfqZhKS2+OwLapMK46rgDSiksydFLXUELcgiQw91VshaqtL7L81KZhQFRWZIaqmMtIUaWigwhHZVKH2uEk3VMbohrXVNIQS6xORJNGKT+FZUQ4MrTEYaEjhMYtU6Zqs2nrhknqdVCILMBorC8iSxXKKalEzohAbTJYfyw7Lx3qb7f0c3MAUadXsLUVJS4MS5/HO4o/MJsRUxRRBIxHGDjjqhChoGKpMjR100nAoc9I4RLUSYghud3t7O9POM7rZ4uYAYN3rV9fFpFaUhNSwr13rWL3zBFp1qEhWhaaxKpCMYWFJW2lqAdBaqLAn6OUD7JDuo2GFx1r/Zcji7B5dDLkqDhSEXiUoiVbOcH5JzklxhIfy023jQCLM0ElotiFiJKCEaMnKs1QSmaos8F6F4wMKomVfUKE9QUWhRa/so1kFIYCQKErBwP+kRsab5TEkmYjkLs1zkeJNbWWqoqzfYj7p9+GUIkpP+PwwuLYx2rIlDstAulryV+v+ypp0fF1ovIVwS/JVCdVSJm6tn1sB7FqfqAirdEyQPCF6MUk3UIOeUKXM5Npg3pQboEkyMZRIWIYJkYwYRm0Oj+lc0Bv/CXF3lHN8M/33t9ItWWE7WycXK3s+tgJAQ2Xse4eR9KRbh1bDYZs2OYidHn4ivFWrVZ1UrC2Sg2xVebDHZBzKDH5aYkyE9ZRptddM1O674nob0qyrjJh6IoHMxollgF3KyWSKQDGBJltN5yDBVTjJ7lnWz/k9XIAD/IR4Sc4tummyHUk5PIWHDYX8OPpo9+gX+nCe82O7XKdBl+tkvssFysFSdjF4ZErBusKCmfOxShCjQoXBaotmvaeaiGlDGQOqZv59edcOda+qml8TfXXFM/FM3+uf5s3M+p7QsxE8NPJFobiAnZH1eQEsuDlSxN7z4CzOA4KcHwRZAPhmsPjmaCH7QOHcQxe9tDxt86negQ3Nz52o+Lt4//nLYNaIk8tVlFfY5crbduG38w8nL50lwNO/pOP4nn07L2v7X/jxxpV3mi/NSR/c5Ll+xffRga8+n/5ufB9VeuiHt3anNx8WSi+WdlU+tT7dhL7s2CayG68+37t1wbdn+jrwdP+5+sbT4s6ys9Fe8t2u5bu0FX/tdR3Yai7affjNvu6+NUd//jpd8eIJT+trKxd6Xpqa/nD2Kx0X6/Lle9/+870z7ZnZBz4494f/1wu9H+9ZsOzZ/mmhRwc+eax/A1V5vP5kov/swIzv3Ve3T++p23B98eVTvL68be4vpOzovCOlL1cMbEVF3XD2/d9s6208dsPY130sZtQefH/enDfK69Xmui8ye7cvO7qsqX/RaaarYuE112fRI0vR4F7+AwLIgBfpEQAA")
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

        private Root fetchProductDetailsUsingName(String name) throws IOException, InterruptedException {
        String fullUrl = String.format("%s?q=%s&limit=15", url, name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + "v^1.1#i^1#p^1#I^3#r^0#f^0#t^H4sIAAAAAAAAAOVYa2wUVRTu9gGUlxHxAQGpA1SgzuydmX1OuptuH0hj6RZ2qbQB4c7MnXbo7sww967djUmpJYIxiBJTJUhIIQSjiaKRoIaUEBAVjKTR+EcTTaohGgIRgg8Src5MH2wrgYZuTBP3z2bOPffc7/vuOfeeGdA5pXjFjlU7fp/lmprf0wk6810udgYonlJUNrsgf35RHshycPV0Luks7Cr4qRzDZMIQ1iJs6BpGJelkQsOCYwxRKVMTdIhVLGgwibBAJCEWWV0ncAwQDFMnuqQnqJLa6hAV9HI8hJD3sDILAzBgWbXhmHE9RHk9nJcXZcnH8ggFWGSNY5xCtRomUCMhigOchwZBmvPHQVBggQB4xsN5mqmSRmRiVdcsFwZQYQeu4Mw1s7DeHirEGJnECkKFayMrY9FIbXVNfbzcnRUrPKRDjECSwqOfqnQZlTTCRArdfhnseAuxlCQhjCl3eHCF0UGFyDCYu4DvSC17/cAvKn6IpCBEopITKVfqZhKS2+OwLapMK46rgDSiksydFLXUELcgiQw91VshaqtL7L81KZhQFRWZIaqmMtIUaWigwhHZVKH2uEk3VMbohrXVNIQS6xORJNGKT+FZUQ4MrTEYaEjhMYtU6Zqs2nrhknqdVCILMBorC8iSxXKKalEzohAbTJYfyw7Lx3qb7f0c3MAUadXsLUVJS4MS5/HO4o/MJsRUxRRBIxHGDjjqhChoGKpMjR100nAoc9I4RLUSYghud3t7O9POM7rZ4uYAYN3rV9fFpFaUhNSwr13rWL3zBFp1qEhWhaaxKpCMYWFJW2lqAdBaqLAn6OUD7JDuo2GFx1r/Zcji7B5dDLkqDhSEXiUoiVbOcH5JzklxhIfy023jQCLM0ElotiFiJKCEaMnKs1QSmaos8F6F4wMKomVfUKE9QUWhRa/so1kFIYCQKErBwP+kRsab5TEkmYjkLs1zkeJNbWWqoqzfYj7p9+GUIkpP+PwwuLYx2rIlDstAulryV+v+ypp0fF1ovIVwS/JVCdVSJm6tn1sB7FqfqAirdEyQPCF6MUk3UIOeUKXM5Npg3pQboEkyMZRIWIYJkYwYRm0Oj+lc0Bv/CXF3lHN8M/33t9ItWWE7WycXK3s+tgJAQ2Xse4eR9KRbh1bDYZs2OYidHn4ivFWrVZ1UrC2Sg2xVebDHZBzKDH5aYkyE9ZRptddM1O674nob0qyrjJh6IoHMxollgF3KyWSKQDGBJltN5yDBVTjJ7lnWz/k9XIAD/IR4Sc4tummyHUk5PIWHDYX8OPpo9+gX+nCe82O7XKdBl+tkvssFysFSdjF4ZErBusKCmfOxShCjQoXBaotmvaeaiGlDGQOqZv59edcOda+qml8TfXXFM/FM3+uf5s3M+p7QsxE8NPJFobiAnZH1eQEsuDlSxN7z4CzOA4KcHwRZAPhmsPjmaCH7QOHcQxe9tDxt86negQ3Nz52o+Lt4//nLYNaIk8tVlFfY5crbduG38w8nL50lwNO/pOP4nn07L2v7X/jxxpV3mi/NSR/c5Ll+xffRga8+n/5ufB9VeuiHt3anNx8WSi+WdlU+tT7dhL7s2CayG68+37t1wbdn+jrwdP+5+sbT4s6ys9Fe8t2u5bu0FX/tdR3Yai7affjNvu6+NUd//jpd8eIJT+trKxd6Xpqa/nD2Kx0X6/Lle9/+870z7ZnZBz4494f/1wu9H+9ZsOzZ/mmhRwc+eax/A1V5vP5kov/swIzv3Ve3T++p23B98eVTvL68be4vpOzovCOlL1cMbEVF3XD2/d9s6208dsPY130sZtQefH/enDfK69Xmui8ye7cvO7qsqX/RaaarYuE112fRI0vR4F7+AwLIgBfpEQAA")
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
