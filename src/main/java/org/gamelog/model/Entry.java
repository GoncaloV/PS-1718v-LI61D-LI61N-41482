package org.gamelog.model;

import javax.persistence.*;

public class Entry {
    @ManyToOne
    @Id
    private Player player;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer entry_id;
    @Column(nullable = false)
    private boolean entry_privacy;
    @ManyToOne
    @Id
    private Game game;
    private Integer entry_rating;
    private String entry_review;
    @Column(nullable = false)
    private boolean entry_favorite;

    protected Entry(){}

    public Entry(Player player, Integer entry_id, boolean entry_privacy, Game game, Integer entry_rating, String entry_review, boolean entry_favorite) {
        this.player = player;
        this.entry_id = entry_id;
        this.entry_privacy = entry_privacy;
        this.game = game;
        this.entry_rating = entry_rating;
        this.entry_review = entry_review;
        this.entry_favorite = entry_favorite;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(Integer entry_id) {
        this.entry_id = entry_id;
    }

    public boolean isEntry_privacy() {
        return entry_privacy;
    }

    public void setEntry_privacy(boolean entry_privacy) {
        this.entry_privacy = entry_privacy;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getEntry_rating() {
        return entry_rating;
    }

    public void setEntry_rating(Integer entry_rating) {
        this.entry_rating = entry_rating;
    }

    public String getEntry_review() {
        return entry_review;
    }

    public void setEntry_review(String entry_review) {
        this.entry_review = entry_review;
    }

    public boolean isEntry_favorite() {
        return entry_favorite;
    }

    public void setEntry_favorite(boolean entry_favorite) {
        this.entry_favorite = entry_favorite;
    }
}
