package kg.kyrgyzcoder.kassa01.data.network.order.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import org.jetbrains.annotations.NotNull;

public class ModelFbOrder implements Parcelable {

    private String clientName;
    private String clientPhone;
    private String clientAddress;
    private int clientId;
    @ServerTimestamp
    private Timestamp dateOrder;
    private double totalCost;
    private int totalCount;
    private String clientLogo;
    private int orderType;
    private boolean isPaid;

    private int status;

    private String declineReason;
    private boolean deliveryCalled;

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }

    public boolean deliveryCalled() {
        return deliveryCalled;
    }

    public void setDeliveryCalled(boolean deliveryCalled) {
        this.deliveryCalled = deliveryCalled;
    }

    protected ModelFbOrder(Parcel in) {
        clientName = in.readString();
        clientPhone = in.readString();
        clientAddress = in.readString();
        clientId = in.readInt();
        dateOrder = in.readParcelable(Timestamp.class.getClassLoader());
        totalCost = in.readDouble();
        totalCount = in.readInt();
        clientLogo = in.readString();
        orderType = in.readInt();
        isPaid = in.readByte() != 0;
        status = in.readInt();
        storeId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientName);
        dest.writeString(clientPhone);
        dest.writeString(clientAddress);
        dest.writeInt(clientId);
        dest.writeParcelable(dateOrder, flags);
        dest.writeDouble(totalCost);
        dest.writeInt(totalCount);
        dest.writeString(clientLogo);
        dest.writeInt(orderType);
        dest.writeByte((byte) (isPaid ? 1 : 0));
        dest.writeInt(status);
        dest.writeInt(storeId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelFbOrder> CREATOR = new Creator<ModelFbOrder>() {
        @Override
        public ModelFbOrder createFromParcel(Parcel in) {
            return new ModelFbOrder(in);
        }

        @Override
        public ModelFbOrder[] newArray(int size) {
            return new ModelFbOrder[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    private int storeId;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getClientLogo() {
        return clientLogo;
    }

    public void setClientLogo(String clientLogo) {
        this.clientLogo = clientLogo;
    }

    public ModelFbOrder(String clientName, String clientPhone, String clientAddress, int clientId, double totalCost, int totalCount) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.clientId = clientId;
        this.totalCost = totalCost;
        this.totalCount = totalCount;
    }

    public ModelFbOrder() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Timestamp getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Timestamp dateOrder) {
        this.dateOrder = dateOrder;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @NotNull
    @Override
    public String toString() {
        return "ModelFbOrder{" +
                "clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientId=" + clientId +
                ", dateOrder=" + dateOrder +
                ", totalCost=" + totalCost +
                ", totalCount=" + totalCount +
                ", clientLogo='" + clientLogo + '\'' +
                ", orderType=" + orderType +
                ", isPaid=" + isPaid +
                ", status=" + status +
                ", declineReason='" + declineReason + '\'' +
                ", deliveryCalled=" + deliveryCalled +
                ", storeId=" + storeId +
                '}';
    }
}
