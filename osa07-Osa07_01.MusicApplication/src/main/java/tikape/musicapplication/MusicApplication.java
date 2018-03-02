package tikape.musicapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.musicapplication.dao.AlbumDao;
import tikape.musicapplication.dao.ArtistDao;
import tikape.musicapplication.dao.TrackDao;
import tikape.musicapplication.database.Database;
import tikape.musicapplication.domain.ArtistData;

public class MusicApplication {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:Chinook_Sqlite.db");
        AlbumDao albumDao = new AlbumDao(database);
        TrackDao trackDao = new TrackDao(database);
        ArtistDao artistDao = new ArtistDao(database);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artists", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artists", artistDao.findAll());

            return new ModelAndView(map, "artists");
        }, new ThymeleafTemplateEngine());

        Spark.get("/artists/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artist", artistDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("albums", albumDao.findAllByArtistId(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "artist");
        }, new ThymeleafTemplateEngine());

        Spark.get("/albums", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("albums", albumDao.findAll());

            return new ModelAndView(map, "albums");
        }, new ThymeleafTemplateEngine());

        Spark.get("/albums/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("artist", artistDao.findOneByAlbumId(Integer.parseInt(req.params("id"))));
            map.put("album", albumDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("tracks", trackDao.findAllForAlbum(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "album");
        }, new ThymeleafTemplateEngine());

        Spark.get("/stats/artists", (req, res) -> {
            HashMap map = new HashMap<>();
            
            Connection conn = database.getConnection();
            
            List<ArtistData> artistdata = new ArrayList<>();
            
            PreparedStatement stmt = conn.prepareStatement("SELECT Artist.ArtistId, Artist.Name, COUNT(Album.AlbumId) AS AlbumCount "
                    + "FROM Artist LEFT JOIN Album "
                    + "ON Artist.ArtistId=Album.ArtistId "
                    + "GROUP BY Artist.ArtistId;");
             ResultSet rs = stmt.executeQuery();
             
             while (rs.next()) {
                 int id = rs.getInt("ArtistId");
                 String name = rs.getString("Name");
                 int count = rs.getInt("AlbumCount");
                 ArtistData ad = new ArtistData(id,name,count);
                 artistdata.add(ad);
             }
             
             map.put("artistdata", artistdata);


            return new ModelAndView(map, "stats-artists");
        }, new ThymeleafTemplateEngine());

    }
}
