package com.aslamep.similary;

import java.util.ArrayList;
import java.util.Comparator;

public class OmdbResponse {

     String Title;
     String Year;
     String Rated;
     String Released;
     String Runtime;
     String Genre;
     String Director;
     String Writer;
     String Actors;
     String Plot;
     String Language;
     String Country;
     String Awards;
     String Poster;
     ArrayList<Ratings1> Ratings = new ArrayList<Ratings1>();
     String Metascore;
     String imdbRating;
     String imdbVotes;
     String imdbID;
     String Type;
     String DVD;
     String BoxOffice;
     String Production;
     String Website;
     String Response;
     String Trailer;

    public OmdbResponse() {
    }

    public OmdbResponse(String title, String year, String rated, String released, String runtime, String genre, String director, String writer, String actors, String plot, String language, String country, String awards, String poster, ArrayList<Ratings1> ratings, String metascore, String imdbRating, String imdbVotes, String imdbID, String type, String DVD, String boxOffice, String production, String website, String response) {
        Title = title;
        Year = year;
        Rated = rated;
        Released = released;
        Runtime = runtime;
        Genre = genre;
        Director = director;
        Writer = writer;
        Actors = actors;
        Plot = plot;
        Language = language;
        Country = country;
        Awards = awards;
        Poster = poster;
        Ratings = ratings;
        Metascore = metascore;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        Type = type;
        this.DVD = DVD;
        BoxOffice = boxOffice;
        Production = production;
        Website = website;
        Response = response;
    }

    public static Comparator<OmdbResponse> RatingComparator = new Comparator<OmdbResponse>() {
        @Override
        public int compare(OmdbResponse o1, OmdbResponse o2) {
            float r1, r2;
            try {
                r1 = Float.parseFloat(o1.imdbRating);
            }catch (NumberFormatException e){
                r1 = 0;
            }
            try {
                r2 = Float.parseFloat(o2.imdbRating);
            }catch (NumberFormatException e){
                r2 = 0;
            }

            return Float.compare(r2, r1 );
        }
    };
}

class Ratings1{
    String Source, Value;

    public Ratings1(String source, String value) {
        Source = source;
        Value = value;
    }

    public Ratings1() {
    }
}