package amagen.magen.mediastacknews.models

// all categories type from API documentation
enum class Categories(val type:String, val description:String) {
    GENERAL("general","Uncategorized News"),
    BUSINESS("business","Business News"),
    ENTERTAINMENT("entertainment", "Entertainment News"),
    HEALTH("health","Health News"),
    SCIENCE("science","Science News"),
    SPORTS("sports","Sports News"),
    TECHNOLOGY("technology","Technology News")
}
/*from documentation
*Categories type and description from the API documentation
*   general - Uncategorized News
    business - Business News
    entertainment - Entertainment News
    health - Health News
    science - Science News
    sports - Sports News
    technology - Technology News
* */