
public class YhteenvetokyselytOsa2 {

    public static void main(String[] args) {
        // Tässä tehtävässä ei tarvitse ohjelmoida Java-kielellä. 
        // Seuraa tehtävänannon ohjeita materiaalista.

    }

    public static String kysely1() {
        return "SELECT Genre.Name AS genre, COUNT(DISTINCT TrackId) AS kappaleita FROM Track LEFT JOIN Genre ON Track.GenreId=Genre.GenreId GROUP BY Genre.Name;";
    }

    public static String kysely2() {
        return "SELECT Genre.Name AS genre, COUNT(Invoice.InvoiceId) AS ostettuja FROM Invoice LEFT JOIN InvoiceLine ON Invoice.InvoiceId=InvoiceLine.InvoiceId LEFT JOIN Track ON InvoiceLine.TrackId=Track.TrackId LEFT JOIN Genre ON Track.GenreId=Genre.GenreId GROUP BY Genre.Name;";
    }

    public static String kysely3() {
        return "SELECT Artist.Name AS artisti, COUNT(DISTINCT Album.AlbumId) AS levyt FROM Artist LEFT JOIN Album ON Album.ArtistId=Artist.ArtistId GROUP BY Artist.Name;";
    }

}
