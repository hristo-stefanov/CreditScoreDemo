package hristostefanov.creditscoredemo.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditReportInfo(
    val changeInLongTermDebt: Int = 0,
    val changeInShortTermDebt: Int = 0,
    val changedScore: Int = 0,
    val clientRef: String = "",
    val currentLongTermCreditLimit: Any? = Any(),
    val currentLongTermCreditUtilisation: Any? = Any(),
    val currentLongTermDebt: Int = 0,
    val currentLongTermNonPromotionalDebt: Int = 0,
    val currentShortTermCreditLimit: Int = 0,
    val currentShortTermCreditUtilisation: Int = 0,
    val currentShortTermDebt: Int = 0,
    val currentShortTermNonPromotionalDebt: Int = 0,
    val daysUntilNextReport: Int = 0,
    val equifaxScoreBand: Int = 0,
    val equifaxScoreBandDescription: String = "",
    val hasEverBeenDelinquent: Boolean = false,
    val hasEverDefaulted: Boolean = false,
    val maxScoreValue: Int = 0,
    val minScoreValue: Int = 0,
    val monthsSinceLastDefaulted: Int = 0,
    val monthsSinceLastDelinquent: Int = 0,
    val numNegativeScoreFactors: Int = 0,
    val numPositiveScoreFactors: Int = 0,
    val percentageCreditUsed: Int = 0,
    val percentageCreditUsedDirectionFlag: Int = 0,
    val score: Int = 0,
    val scoreBand: Int = 0,
    val status: String = ""
)