package com.yucatancorp.ecommercemarcosnarvaez.data

import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("responseStatus")
    val responseStatus: String?,
    @SerializedName("responseMessage")
    val responseMessage: String?,
    @SerializedName("item")
    val item: Item?
)

data class Item(
    @SerializedName("props")
    val props: Props?
)

data class Props(
    @SerializedName("pageProps")
    val pageProps: PageProps?
)

data class PageProps(
    @SerializedName("initialData")
    val initialData: InitialData?
)

data class InitialData(
    @SerializedName("searchResult")
    val searchResult: SearchResult?
)

data class SearchResult(
    @SerializedName("itemStacks")
    val itemStacks: List<ItemStack>?
)

data class ItemStack(
    @SerializedName("items")
    val items: List<ProductItem>
)

data class ProductItem(
    @SerializedName("name")
    val name: String?,
    @SerializedName("priceInfo")
    val priceInfo: PriceInfo?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("offerId")
    val offerId: String?
)

data class PriceInfo(
    @SerializedName("priceDetails")
    val priceDetails: PriceDetails?
)

data class PriceDetails(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("priceLines")
    val priceLines: List<PriceLine>?
)

data class PriceLine(
    @SerializedName("lineType")
    val lineType: String?,
    @SerializedName("values")
    val values: List<Value>?
)

data class Value(
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?,
)