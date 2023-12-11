package app.main;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class Main { //TODO Prolly delete and make only GUI no console app
    public static void main(String[] args) {
        Orianna.setRiotAPIKey("RGAPI-9edeac1e-1a58-4e3a-8be3-d96bbd011bec");
        Orianna.setDefaultRegion(Region.EUROPE_WEST);
        final Summoner summoner = Summoner.named("ElXavioMK09").get();
        System.out.println(summoner.getChampionMasteries().toString());
    }
}