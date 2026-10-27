package studentgroups.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "groups")
public class StudentGroupListWrapper {

    private List<StudentGroup> groups;

    @XmlElement(name = "group")
    public List<StudentGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<StudentGroup> groups) {
        this.groups = groups;
    }
}