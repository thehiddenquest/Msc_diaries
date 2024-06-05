package ricart_agarwala;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Node {
    public List<String> requestList = new ArrayList<String>();
    private List<Node> deferredList = new ArrayList<Node>();
    private int UID;
    private String name;
    private int status = -1; // -1 : not requesting, 1 : requested, 0 : in CS
    private int timestamp = 99999;

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void requestFrom(Node otherNode) {
        if (this.status == -1 || this.status == 0) {
            System.out.println(this.name + " has replied to " + otherNode.getName());
            otherNode.replyFrom(this.name);
        } else if (this.timestamp > otherNode.timestamp) {
            System.out.println(this.name + " has replied to " + otherNode.getName());
            otherNode.replyFrom(this.name);
        } else if (this.timestamp == otherNode.timestamp) {
            if (this.UID > otherNode.UID) {
                System.out.println(this.name + " has replied to " + otherNode.getName());
                otherNode.replyFrom(this.name);
            }
        } else {
            deferredList.add(otherNode);
            System.out.println(this.name + " has defrred " + otherNode.getName());
        }
    }

    public void replyFrom(String name) {
        requestList.remove(name);
        if (requestList.isEmpty() && this.status == 1) {
            this.status = 0;
            System.out.println(this.name + " is Going to critical section ");
            System.out.println(this.name + " exits Critical Section");
            Iterator<Node> iterator = deferredList.iterator();
            while (iterator.hasNext()) {
                Node item = iterator.next();
                System.out.println(this.name + " has replied to " + item.getName());
                item.replyFrom(this.name);
                iterator.remove();

            }
//            Iterator<Node> iterator1 = deferredList.iterator();
//            while (iterator1.hasNext()) {
//                Node item = iterator1.next();
//                
//            }
        }
    }
}