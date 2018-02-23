
public class YhteenvetokyselytOsa1 {

    public static void main(String[] args) {
        // Tässä tehtävässä ei tarvitse ohjelmoida Java-kielellä. 
        // Seuraa tehtävänannon ohjeita materiaalista.

    }

    public static String kysely1() {
        return "SELECT COUNT(DISTINCT AlbumId) FROM Album;";
    }

    public static String kysely2() {
        return "SELECT AVG(total) FROM INVOICE;";
    }

    public static String kysely3() {
        return "SELECT COUNT(DISTINCT TrackId) FROM Track,Genre WHERE Track.GenreId=Genre.GenreId AND (Genre.Name=\"Blues\" OR Genre.Name=\"Jazz\" OR Genre.Name=\"Metal\");";
    }

}
