package com.example.fitnesspal;

public class ArticleEntity {

    private String ArticleId;
    private String Category;
    private String Title;
    private String Description;


    public ArticleEntity() {
    }



    public ArticleEntity(String articleId, String category, String title, String description) {
        ArticleId = articleId;
        Category = category;
        Title = title;
        Description = description;
    }

    public String getArticleId() {

        return ArticleId;
    }

    public void setArticleId(String articleId) {
        ArticleId = articleId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
