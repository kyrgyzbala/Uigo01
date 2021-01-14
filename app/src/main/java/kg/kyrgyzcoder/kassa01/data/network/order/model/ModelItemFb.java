package kg.kyrgyzcoder.kassa01.data.network.order.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ModelItemFb implements Serializable {
    private int id;
    private String name;
    private Double cost;
    private int count;
    private Double total;
    private int storeId;


    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public ModelItemFb(int id, String name, Double cost, int count, Double total) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.count = count;
        this.total = total;
    }

    public ModelItemFb() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @NotNull
    @Override
    public String toString() {
        return "ModelItemFb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", count=" + count +
                ", total=" + total +
                '}';
    }
}
