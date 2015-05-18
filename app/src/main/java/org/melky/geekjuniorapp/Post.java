package org.melky.geekjuniorapp;

/**
 * Created by Administrador on 30/04/2015.
 */

public class Post {
    String ID;
    String title;
    Featured_image featured_image;
    String content;
    String excerpt;
    String date;
    String link;

    @Override
    public boolean equals(Object o) {
        return ((Post)o).ID.equals(this.ID);
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
class Featured_image{
    String ID;
    String source;
    Attachment_meta attachment_meta;

}

class Attachment_meta {
    Sizes sizes;

}

class Sizes{
    Thumbnail thumbnail;
    Thumbnail medium;

}

class Thumbnail{
    String url;
}

