package com.example.bolek.testy.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("ch_title")
    @Expose
    private String chTitle;
    @SerializedName("novel_id")
    @Expose
    private String novelId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("tom_number")
    @Expose
    private int tomNumber;
    @SerializedName("chapter_number")
    @Expose
    private int chapterNumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;

    public Chapter(int id, String chTitle) {
        this.id = id;
        this.chTitle = chTitle;
    }

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return chTitle;
    }

    /**
     * @param chTitle The title
     */
    public void setTitle(String chTitle) {
        this.chTitle = chTitle;
    }

    /**
     * @return The novelId
     */
    public String getNovelId() {
        return novelId;
    }

    /**
     * @param novelId The novel_id
     */
    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The tomNumber
     */
    public int getTomNumber() {
        return tomNumber;
    }

    /**
     * @param tomNumber The tom_number
     */
    public void setTomNumber(int tomNumber) {
        this.tomNumber = tomNumber;
    }

    /**
     * @return The chapterNumber
     */
    public int getChapterNumber() {
        return chapterNumber;
    }

    /**
     * @param chapterNumber The chapter_number
     */
    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }
}