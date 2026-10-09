package cv.pn.processmanagement.commons;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CommonsFlatTableAttributes extends CommonsAttributes{

    @Column(name = "dm_relationship_table", nullable = false, length = 50)
    private String dmRelationshipTable;

    @Column(name = "relationship_table_fk", nullable = false, length = 40)
    private String relationshipTable;

    public String getDmRelationshipTable() {
        return dmRelationshipTable;
    }

    public void setDmRelationshipTable(String dmRelationshipTable) {
        this.dmRelationshipTable = dmRelationshipTable;
    }

    public String getRelationshipTable() {
        return relationshipTable;
    }

    public void setRelationshipTable(String relationshipTable) {
        this.relationshipTable = relationshipTable;
    }
}
