package com.SD.notepad;

//TODO:add an attribute for the last modified field

public class NotesBuilder {
    private String title,
    content;

    public NotesBuilder() {

    }

    public NotesBuilder(String title, String content) {

            this.title = title;
            this.content = content;

        }

        public String getTitle () {
            return title;
        }

        public String getContent () {
            return content;
        }


}
