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
                .header("Authorization", "Bearer " + "v^1.1#i^1#p^1#I^3#r^0#f^0#t^H4sIAAAAAAAAAOVYXWwUVRTutlugAoKAtAVs1imgtM7unZ3t7O6ku7rtFqlCW7tLLUgDd2bubIfOzgxzZ2kbTKyFkFB8QAjif+ofkmCMJAYkEtQSUBOkYAg+qCHyAjExEpV/Ep2Zbsu2EmjoxjRxXzZz7rnnft93z7n3zICuCQVlm5dsvjLVMTG3twt05Toc1GRQMCG//P683Dn5OSDDwdHbNb/L2Z13oRLDpKyxjQhrqoKRqyMpK5i1jSEipSusCrGEWQUmEWYNno1Fli1lvW7AarpqqLwqE67aaIjgOJESIB3kAlzQxwR506oMxoyrIYIRYIXXD/x0ANE0hMgcxziFahVsQMUIEV7g9ZHAT3pB3EuxVID1Mm4f8K8kXE1Ix5KqmC5uQIRtuKw9V8/AemeoEGOkG2YQIlwbWRyrj9RGa+rilZ6MWOG0DjEDGik8/KlaFZCrCcopdOdlsO3NxlI8jzAmPOGBFYYHZSODYO4Bvi21yAFK8EMABIgALfBZkXKxqiehcWcclkUSSNF2ZZFiSEbn3RQ11eDWIt5IP9WZIWqjLuvvmRSUJVFCeoioqYqsiDQ0EOGIoEtQeVInG6piZENjlISQpxgO8TwpMiJNcUIgvcZAoLTCIxapVhVBsvTCrjrVqEImYDRSFpAhi+lUr9TrEdGwwGT60Wn56CCz0trPgQ1MGa2KtaUoaWrgsh/vLv7QbMPQJS5loKEIIwdsdUIE1DRJIEYO2mmYzpwOHCJaDUNjPZ729nZ3O+1W9YTHCwDlaV62NMa3oiQkBn2tWsfS3SeQkk2FNyu0A0us0amZWDrMNDUBKAki7AtW0AEqrftwWOGR1n8ZMjh7hhdDtoqDZwJ+jmagWAEon4CYbBRHOJ2fHgsH4mAnmYR6GzI0GfKI5M08SyWRLgksXSF66YCISIEJiqQvKIokVyEwJCUiBBDiOD4Y+J/UyGizPIZ4HRnZS/NspPiKtnJJFJvX6s/6GZwSOf5pxg+DjU31ibVxWA46orw/qvqrajriy0OjLYTbkq+WJVOZuLl+dgWwan2sIixRsYGEMdGL8aqGGlRZ4jvH1wbTutAAdaMzhmTZNIyJZETTarN4TGeD3uhPiHujnOWb6b+/lW7LClvZOr5YWfOxGQBqktu6d9y8mvSo0Gw4LNNqG7Hdw4+Ft2S2quOKtUlygK0kDPSYbpuyG6/n3TrCako322t3vdV3xdU2pJhXmaGrsoz0prFlgFXKyWTKgJyMxltNZyHBJTjO7lnK76UqKEADZky8ePsWXT3ejqQsnsKDBic9ij7aM/yFPpxj/6huRx/odhzOdThAJVhAlYKHJ+Qtd+ZNmYMlA7klKLqxlFDM91QdudtQpwYlPXdmzh/v7lxSPaem/pWyDfHOk298nTMl43tCbwsoGvqiUJBHTc74vADm3RrJp6YVTvWafaTZNlJUwMusBKW3Rp3UbOesB9dETxTvb3rnm9PnZnlv7FAn9W/rB1OHnByO/BxntyNn+tZw8fnLP++4Ue06selg16W9ytY+38F9175f1X+oJL/0swu/vz3r46LDj334pbPvtfV12x83yvcUL7x+te/itdmP9n9xJpC4XDnjvZZc/fSePQschaHvLhdUlRYpD1y7oF5/PTFXXOQr2dre/ZBn09wrR75qjvJ/P7JqplR+blfZL4pxc27ZC3sjZdSxM8cu/rrzbMmf08XEuZrWwpcWn4rs3vLqoZ7G3Q76vqJT3vnPFZ5tXrRmI3/1wPye8x9sn3Fk3yTPjz/9QBZRT7x12vdRz7oXe45P+zzxZkvJvFTL8bNOedvLvQe0p6bsWudYeFguznn+E+23DfPe/4s86pn4adH+pqPfnmy9eaz20sb+gb38Bw/j6M3pEQAA")
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
                .header("Authorization", "Bearer " + "v^1.1#i^1#f^0#p^1#I^3#r^0#t^H4sIAAAAAAAAAOVYf2wTVRzvbd1ggYEDAzpRuwNCgNz1XX/3WCvtOlwVtkm7OTCG3I9349j1rrl3Za0QGAugxhmjhImghADqNGIkgAkIgxkjJqIIKokBifwhYFQMaILRAN7dutFNAgtrzBKbNM193/d93+fzed/ve98eaCsumbW+Zv3VUmxUwbY20FaAYdQYUFJcNHtcYUF5kQXkOGDb2qa1WdsLL1YiJiEl6YUQJRUZQVs6IcmINo0BPKXKtMIgEdEyk4CI1jg6Flown3aQgE6qiqZwioTbopEA7qdYyidASuA4yi1wQLfKfTHjSgCneI/HAwUf5/JQfg906OMIpWBURhojawHcARwuAngJhysOvLTbR1Nu0u1xL8ZtjVBFoiLrLiTAgyZc2pyr5mC9PVQGIahqehA8GA3Ni9WFopHq2nilPSdWMKtDTGO0FBr4VKXw0NbISCl4+2WQ6U3HUhwHEcLtwd4VBgalQ31g7gK+KbVDcDD6CowPugQvy+dHynmKmmC02+MwLCJPCKYrDWVN1DJ3UlRXg10GOS37VKuHiEZsxs8TKUYSBRGqAbw6HFoUqq/HgyFeFRn5UZWoD8eI+oURgmE4ysNCjiMEj+CkWN6XXaM3UFbhQYtUKTIvGnohW62ihaEOGA6WxZUji+5UJ9epIUEzwOT6+fvlA4uN/ezdwJS2VDa2FCZ0DWzm453F75+taarIpjTYH2HwgKlOAGeSSZHHBw+aaZjNnDQK4Es1LUnb7a2trWSrk1TUZrsDAMretGB+jFsKEwze52vUOhLvPIEQTSoc1GcikdYySR1LWk9THYDcjAddfrfTR2V1HwgrONj6L0MOZ/vAYshXcXA+4PSzDr08KC8LnXw+iiOYzU+7gQOyTIZIMGoL1JISw0GC0/MslYCqyNNOt+Bw6qcgwXv8AuHyCwLBunkPQQkQAghZlvP7/ic1MtQsj0FOhVr+0jwfKb6oZbYoCE3L1Ce9HpQSWO5xj5fxL2ysa14WZ2aDdITzRhRvuDodbwgMtRBuSb5KEnVl4vr6+RXAqPXhilCjIA3yw6IX45QkrFckkcuMrA12qnw9o2qZGJQk3TAskqFkMprHYzof9IZ+Qtwd5TzfTP/9rXRLVsjI1pHFypiP9ABMUiSNe4fklIRdYfSGwzAtMRGbPfxweIt6qzqiWOske9mKfG+PSZqUSbScI1WIlJSqt9dkndF3xZUWKOtXmaYqkgTVxuFlgFHKiURKY1gJjrSazkOCi8wIu2cpr4PyAa/P7x4WL868RZeMtCMpj6dwn8HqHEIfbR/4hz5oMT9UO/YxaMe6CzAMVILp1FRQUVzYYC0cW45EDZIiI5BIbJb1/6kqJFtgJsmIasFEy5XtG2uqyqvrOmetiGe+2nLUMjbnfcK2p8F9/W8USgqpMTmvF8CUmyNF1PjJpQ4X8Bpft49yLwZTb45aqUnWe7sOOMdXzDlYfu4b7/Udk7bYXv4g+gIo7XfCsCKLtR2zhE/gtUdXvXJu7+VThzqbZry2+jdn5c9TtpRYbkipmR0zha2X/7YdAms+mnxq3eEvPunZbLvQzV87UjNx9elVM7pXiuz3H26IHSt7/eTF77CVa09OOPGiZ86o5dPSu3dVzsXerNj3dunJDZ2RQ5fI4489/O35KTeuP1fxflnPvh2d99TChp4Oalzk09P7u0evuLz5bOD5g+H9o0N/PHL1l2vY+N97HC99uWNnzUObzh+RdhbOCx9/9p3w2nRx15m9P8yMTCy99tTZpo51zq/TZ+f/uLLk8PVjR/adaDuwynppz4y/aq1/zp0Qf/DKqQnv/nRmza+7Nj3wVkPZ1vbPul+9/72yzy88Aw5sn777fFfXxj2RN3r38h+sTTy36REAAA==")
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
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false);
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
        if (Root != null) {
            for (int i = 0; i < Root.itemSummaries.size(); i++) {
                Product p;
                itemSummary item = Root.itemSummaries.get(i);
                item.calculateCommission();
                Random rand = new Random();
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false);
                EbayProducts.add(p);
            }
            return EbayProducts;
        } else {
            System.out.println("No product details found");
            return null;
        }
    }

}
