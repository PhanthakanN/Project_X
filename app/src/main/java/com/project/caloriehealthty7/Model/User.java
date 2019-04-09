package com.project.caloriehealthty7.Model;

public class User {

        private String id;
        private String username;
        private String imageURL;
        private String gen;

        public User(String id, String username, String imageURL, String search) {
            this.id = id;
            this.username = username;
            this.imageURL = imageURL;
            this.gen = search;
        }

        public User() {}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getSearch() {
            return gen;
        }

        public void setSearch(String search) {
            this.gen = search;
        }
}
