package mk.ukim.finki.rideshare.web.response;

public record RidePriceStatisticsResponse(
        Double min,
        Double max,
        Double average
) {
}
