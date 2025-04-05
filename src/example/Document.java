package example;

import db.*;
import java.util.*;

public class Document extends Entity implements Trackable {
    public static final int DOCUMENT_ENTITY_CODE = 15;
    public String content;
    private Date creationDate;
    private Date lastModificationDate;

    public Document(String content) {
        this.content = content;
    }

    @Override
    public Entity copy() {
        Document copy = new Document(content);
        copy.id = this.id;

        if (creationDate != null)
            copy.creationDate = new Date(this.creationDate.getTime());
        if (lastModificationDate != null)
            copy.lastModificationDate = new Date(this.lastModificationDate.getTime());

        return copy;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
