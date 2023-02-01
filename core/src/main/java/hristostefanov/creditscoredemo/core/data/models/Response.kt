package hristostefanov.creditscoredemo.core.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val accountIDVStatus: String = "",
    val augmentedCreditScore: Any? = Any(),
    val coachingSummary: CoachingSummary = CoachingSummary(),
    val creditReportInfo: CreditReportInfo = CreditReportInfo(),
    val dashboardStatus: String = "",
    val personaType: String = ""
)