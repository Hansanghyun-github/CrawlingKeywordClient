package com.example.crawlingkeywordclient;

import java.util.List;

public class NewTitlesResponse {
    private List<String> inflearnNewTitles;
    private List<String> okkyNewTitles;

    public NewTitlesResponse(List<String> inflearnNewTitles, List<String> okkyNewTitles) {
        this.inflearnNewTitles = inflearnNewTitles;
        this.okkyNewTitles = okkyNewTitles;
    }

    public List<String> getInflearnNewTitles() {
        return inflearnNewTitles;
    }

    public List<String> getOkkyNewTitles() {
        return okkyNewTitles;
    }

    @Override
    public String toString() {
        return "inflearnNewTitles=" + inflearnNewTitles +
                "\nokkyNewTitles=" + okkyNewTitles +
                "\n";
    }
}
