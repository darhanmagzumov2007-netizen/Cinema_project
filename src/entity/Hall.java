package entity;

public class Hall {
    private Integer id;
    private String name;
    private Integer capacity;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }





public Hall() {}
public Hall(Integer id, String name, Integer capacity) {
this.id = id;
this.name = name;
this.capacity = capacity;


}
@Override
public String toString() {




    return  "Hall{" +
            "id" + id +
            ", name=" + name + '\'' +
            ", capacity=" + capacity +
            '}';


}





}
