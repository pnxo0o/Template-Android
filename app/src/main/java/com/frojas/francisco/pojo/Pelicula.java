package com.frojas.francisco.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Francisco on 30/03/2015.
 */
@DatabaseTable(tableName = "pelicula")
public class Pelicula implements Serializable{

    @DatabaseField(id = true,generatedId = false)
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private Date releaseDateTheater;

    @DatabaseField
    private int audienceScore;

    @DatabaseField
    private String synopsis;

    @DatabaseField
    private String urlThumbnail;

    @DatabaseField
    private String urlSelf;

    @DatabaseField
    private String urlCast;

    @DatabaseField
    private String urlReviews;

    @DatabaseField
    private String urlSimilar;

    public Pelicula(){
        super();
    }

    public Pelicula(long id,
                 String title,
                 Date releaseDateTheater,
                 int audienceScore,
                 String synopsis,
                 String urlThumbnail,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar){
        super();
        this.id=id;
        this.title=title;
        this.releaseDateTheater=releaseDateTheater;
        this.audienceScore=audienceScore;
        this.synopsis=synopsis;
        this.urlThumbnail=urlThumbnail;
        this.urlSelf=urlSelf;
        this.urlCast=urlCast;
        this.urlReviews=urlReviews;
        this.urlSimilar=urlSimilar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

}
