package pl.lodz.uni.math.kuba.project33;

public class Pin {
    private String imageUrl;
    private String note;
    private String link;
    private String metadata;

    public Pin() {

    }

    public Pin(String imageUrl, String note, String link, String metadata) {
        this.imageUrl = imageUrl;
        this.note = note;
        this.link = link;
        this.metadata = metadata;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
