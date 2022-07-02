package com.mobulous.billingClient

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*

class MyBillingClient(val context: Context) : PurchasesUpdatedListener {
    private lateinit var billingClient: BillingClient
    private val skuList =
        listOf("com.fitscope.fitscope.67208.130", "com.fitscope.fitscope.67207.26")

    init {
        if (this::billingClient.isInitialized) {
            if (!billingClient.isReady) {
                setupBillingClient()
            } else {
                loadAllSKUs()
            }
        } else {
            setupBillingClient()
        }


    }

    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener(this)
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    loadAllSKUs()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.

            }
        })

    }

    private fun loadAllSKUs() = if (billingClient.isReady) {
        val params = SkuDetailsParams
            .newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.SUBS)
            .build()
        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            // Process the result.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList?.isNotEmpty() == true) {
                for (skuDetails in skuDetailsList) {
                    if (skuDetails.sku == "com.fitscope.fitscope.67207.26") {
                        val billingFlowParams = BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(skuDetails)
                            .build()
                        billingClient.launchBillingFlow((context as Activity), billingFlowParams)
                    }

                }
            }
            //  logger(skuDetailsList.get(0).description)

        }

    } else {
        println("Billing Client not ready")
    }


    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                acknowledgePurchase(purchase.purchaseToken)

            }
        } else if (billingResult?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            //logger("User Cancelled")
            println("~~~${billingResult?.debugMessage.toString()}~~~")


        } else {
            println("~~~${billingResult?.debugMessage.toString()}~~~")
            // Handle any other error codes.
        }
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { billingResult ->
            val responseCode = billingResult.responseCode
            val debugMessage = billingResult.debugMessage
            println("~~~~~${responseCode}~~${debugMessage}~~")
        }
    }


}