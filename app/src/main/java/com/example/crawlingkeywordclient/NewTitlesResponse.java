package com.example.crawlingkeywordclient;

import java.util.List;

public class NewTitlesResponse {
    private List<String> inflearnNewTitles;
    private List<String> okkyNewTitles;

    public NewTitlesResponse(List<String> inflearnNewTitles, List<String> okkyNewTitles) {
        this.inflearnNewTitles = inflearnNewTitles;
        this.okkyNewTitles = okkyNewTitles;
    }

    @Override
    public String toString() {
        return "NewTitlesResponse{" +
                "\n\tinflearnNewTitles=" + inflearnNewTitles +
                ",\n\tokkyNewTitles=" + okkyNewTitles +
                "\n}";
    }
}
