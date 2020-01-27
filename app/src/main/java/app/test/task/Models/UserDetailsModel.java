package app.test.task.Models;

import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {
    @SerializedName("login")
    private String login;
    @SerializedName("id")
    private int id;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("name")
    private String name;
    @SerializedName("public_repos")
    private int repos;
    @SerializedName("public_gists")
    private int gists;
    @SerializedName("followers")
    private int followers;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepos() {
        return repos;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

    public int getGists() {
        return gists;
    }

    public void setGists(int gists) {
        this.gists = gists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
